package ru.stersh.subsonic.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.appendPathSegments
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.stersh.subsonic.api.model.AlbumList2Response
import ru.stersh.subsonic.api.model.AlbumListResponse
import ru.stersh.subsonic.api.model.AlbumResponse
import ru.stersh.subsonic.api.model.ArtistResponse
import ru.stersh.subsonic.api.model.ArtistsResponse
import ru.stersh.subsonic.api.model.EmptyResponse
import ru.stersh.subsonic.api.model.ErrorResponse
import ru.stersh.subsonic.api.model.ListType
import ru.stersh.subsonic.api.model.PlayQueueResponse
import ru.stersh.subsonic.api.model.PlaylistResponse
import ru.stersh.subsonic.api.model.PlaylistsResponse
import ru.stersh.subsonic.api.model.RandomSongsResponse
import ru.stersh.subsonic.api.model.SearchResult3Response
import ru.stersh.subsonic.api.model.SongResponse
import ru.stersh.subsonic.api.model.Starred2Response
import ru.stersh.subsonic.api.model.StarredResponse
import ru.stersh.subsonic.api.model.SubsonicResponse


class SubsonicApi(
    val baseUrl: String,
    val username: String,
    val password: String,
    val apiVersion: String,
    val clientId: String,
    val authType: AuthType = AuthType.Token(),
    baseClient: HttpClient = HttpClient()
) {
    @OptIn(ExperimentalStdlibApi::class)
    private val client = baseClient.config {
        install(ContentNegotiation) {
            json(
                json = Json {
                    isLenient = true
                    explicitNulls = false
                    prettyPrint = true
                    ignoreUnknownKeys = true
                }
            )
        }
        install(DefaultRequest) {
            url(baseUrl)
            url {
                appendClientParameters()
                appendAuth(authType)
            }
        }
        HttpResponseValidator {
            validateResponse { response ->
                val error: SubsonicResponse<ErrorResponse>? = runCatching {
                    response.body<SubsonicResponse<ErrorResponse>>()
                }.getOrNull()

                if (error != null) {
                    throw ApiException(error.data.error.code, error.data.error.message)
                }
            }
        }
    }

    suspend fun ping(): SubsonicResponse<EmptyResponse> {
        return client
            .get("rest/ping")
            .body<SubsonicResponse<EmptyResponse>>()
    }

    suspend fun getSong(id: String): SubsonicResponse<SongResponse> {
        return client
            .get("rest/getSong") {
                parameter("id", id)
            }
            .body<SubsonicResponse<SongResponse>>()
    }

    suspend fun getRandomSongs(
        size: Int? = null,
        genre: String? = null,
        fromYear: Int? = null,
        toYear: Int? = null,
        musicFolderId: String? = null
    ): SubsonicResponse<RandomSongsResponse> {
        return client
            .get("rest/getRandomSongs") {
                parameter("size", size)
                parameter("genre", genre)
                parameter("fromYear", fromYear)
                parameter("toYear", toYear)
                parameter("musicFolderId", musicFolderId)
            }
            .body<SubsonicResponse<RandomSongsResponse>>()
    }

    suspend fun getArtist(id: String): SubsonicResponse<ArtistResponse> {
        return client
            .get("rest/getArtist") {
                parameter("id", id)
            }
            .body<SubsonicResponse<ArtistResponse>>()
    }

    suspend fun getArtists(): SubsonicResponse<ArtistsResponse> {
        return client
            .get("rest/getArtists")
            .body<SubsonicResponse<ArtistsResponse>>()
    }

    suspend fun getAlbumList(
        type: ListType,
        size: Int? = null,
        offset: Int? = null,
        fromYear: Int? = null,
        toYear: Int? = null,
        genre: String? = null,
        musicFolderId: String? = null,
    ): SubsonicResponse<AlbumListResponse> {
        return client
            .get("rest/getAlbumList") {
                parameter("type", type.value)
                parameter("size", size)
                parameter("offset", offset)
                parameter("fromYear", fromYear)
                parameter("toYear", toYear)
                parameter("genre", genre)
                parameter("musicFolderId", musicFolderId)
            }
            .body<SubsonicResponse<AlbumListResponse>>()
    }

    suspend fun getAlbumList2(
        type: ListType,
        size: Int? = null,
        offset: Int? = null,
        fromYear: Int? = null,
        toYear: Int? = null,
        genre: String? = null,
        musicFolderId: String? = null,
    ): SubsonicResponse<AlbumList2Response> {
        return client
            .get("rest/getAlbumList2") {
                parameter("type", type.value)
                parameter("size", size)
                parameter("offset", offset)
                parameter("fromYear", fromYear)
                parameter("toYear", toYear)
                parameter("genre", genre)
                parameter("musicFolderId", musicFolderId)
            }
            .body<SubsonicResponse<AlbumList2Response>>()
    }

    suspend fun getAlbum(id: String): SubsonicResponse<AlbumResponse> {
        return client
            .get("rest/getAlbum") {
                parameter("id", id)
            }
            .body<SubsonicResponse<AlbumResponse>>()
    }

    suspend fun getPlaylist(id: String): SubsonicResponse<PlaylistResponse> {
        return client
            .get("rest/getPlaylist") {
                parameter("id", id)
            }
            .body<SubsonicResponse<PlaylistResponse>>()
    }

    suspend fun getPlaylists(): SubsonicResponse<PlaylistsResponse> {
        return client
            .get("rest/getPlaylists")
            .body<SubsonicResponse<PlaylistsResponse>>()
    }

    suspend fun getPlayQueue(): SubsonicResponse<PlayQueueResponse> {
        return client
            .get("rest/getPlayQueue")
            .body<SubsonicResponse<PlayQueueResponse>>()
    }

    suspend fun savePlayQueue(
        id: List<String>,
        current: String? = null,
        position: Long? = null
    ): SubsonicResponse<EmptyResponse> {
        return client
            .get("rest/savePlayQueue") {
                id.forEach {
                    parameter("id", it)
                }
                parameter("current", current)
                parameter("position", position)
            }
            .body<SubsonicResponse<EmptyResponse>>()
    }

    suspend fun star(
        id: List<String>? = null,
        albumId: List<String>? = null,
        artistId: List<String>? = null
    ): SubsonicResponse<EmptyResponse> {
        return client
            .get("rest/star") {
                id?.forEach {
                    parameter("id", it)
                }
                albumId?.forEach {
                    parameter("albumId", it)
                }
                artistId?.forEach {
                    parameter("artistId", it)
                }
            }
            .body<SubsonicResponse<EmptyResponse>>()
    }

    suspend fun unstar(
        id: List<String>? = null,
        albumId: List<String>? = null,
        artistId: List<String>? = null
    ): SubsonicResponse<EmptyResponse> {
        return client
            .get("rest/unstar") {
                id?.forEach {
                    parameter("id", it)
                }
                albumId?.forEach {
                    parameter("albumId", it)
                }
                artistId?.forEach {
                    parameter("artistId", it)
                }
            }
            .body<SubsonicResponse<EmptyResponse>>()
    }

    suspend fun getStarred(
        musicFolderId: String? = null
    ): SubsonicResponse<StarredResponse> {
        return client
            .get("rest/getStarred") {
                parameter("musicFolderId", musicFolderId)
            }
            .body<SubsonicResponse<StarredResponse>>()
    }

    suspend fun getStarred2(
        musicFolderId: String? = null
    ): SubsonicResponse<Starred2Response> {
        return client
            .get("rest/getStarred2") {
                parameter("musicFolderId", musicFolderId)
            }
            .body<SubsonicResponse<Starred2Response>>()
    }

    suspend fun scrobble(
        id: String,
        time: Long? = null,
        submission: Boolean? = null,
    ): SubsonicResponse<EmptyResponse> {
        return client
            .get("rest/scrobble") {
                parameter("id", id)
                parameter("time", time)
                parameter("submission", submission)
            }
            .body<SubsonicResponse<EmptyResponse>>()
    }

    suspend fun search3(
        query: String,
        songCount: Int? = null,
        songOffset: Int? = null,
        albumCount: Int? = null,
        albumOffset: Int? = null,
        artistCount: Int? = null,
        artistOffset: Int? = null
    ): SubsonicResponse<SearchResult3Response> {
        return client
            .get("rest/search3") {
                parameter("query", query)
                parameter("songCount", songCount)
                parameter("songOffset", songOffset)
                parameter("albumCount", albumCount)
                parameter("albumOffset", albumOffset)
                parameter("artistCount", artistCount)
                parameter("artistOffset", artistOffset)
            }
            .body<SubsonicResponse<SearchResult3Response>>()
    }

    fun getCoverArtUrl(
        id: String?,
        size: Int? = null,
        auth: Boolean = false
    ): String? {
        return id?.let {
            buildUrl(
                "getCoverArt",
                mapOf("id" to it, "size" to size),
                auth
            ).toString()
        }
    }

    fun downloadUrl(id: String): String = buildUrl("download", mapOf("id" to id)).toString()

    fun streamUrl(id: String): String = buildUrl("stream", mapOf("id" to id)).toString()

    fun avatarUrl(
        username: String,
        auth: Boolean = false
    ): String = buildUrl("getAvatar", mapOf("username" to username), auth).toString()

    private fun buildUrl(
        path: String,
        queryMap: Map<String, Any?>,
        auth: Boolean = true
    ): Url {
        val uriBuilder = URLBuilder(baseUrl)
            .appendPathSegments("rest", path)

        uriBuilder.appendClientParameters()

        queryMap.forEach { entry ->
            if (entry.value != null) {
                uriBuilder.parameters.append(entry.key, entry.value.toString())
            }
        }

        if (auth) {
            uriBuilder.appendAuth(authType)
        }

        return uriBuilder
            .build()
    }

    fun URLBuilder.appendClientParameters() {
        parameters.append("c", clientId)
        parameters.append("v", apiVersion)
        parameters.append("f", "json")
    }

    fun URLBuilder.appendAuth(authType: AuthType) {
        parameters.append("u", this@SubsonicApi.username)
        val userPassword = this@SubsonicApi.password
        when (authType) {
            AuthType.EncodedPassword -> {
                parameters.append("p", encodePassword(userPassword))
            }

            is AuthType.Token -> {
                val salt = generateSalt(authType.saltLength)
                parameters.append("s", salt)
                parameters.append("t", generateToken(salt, userPassword))
            }

            AuthType.Unsecure -> {
                parameters.append("p", userPassword)
            }
        }
    }
}