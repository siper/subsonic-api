import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import kotlinx.coroutines.runBlocking
import ping.PingResponse
import ru.stersh.subsonic.api.SubsonicApi
import kotlin.test.Test

class SubsonicApiTest {
    private val api = SubsonicApi(
        baseUrl = "http://demo.subsonic.org",
        username = "guest",
        password = "guest",
        apiVersion = Api.VERSION,
        clientId = "SubsonicKotlinApi",
        useLegacyAuth = false
    )

    @Test
    fun `Test artist response`() {
        runBlocking {
            println(api.getArtist("13"))
        }
    }

    @Test
    fun `Test album response`() {
        runBlocking {
            println(api.getAlbum("37"))
        }
    }

    @Test
    fun `Test playlist response`() {
        runBlocking {
            println(api.getPlaylist("1"))
        }
    }

    @Test
    fun `Test playlists response`() {
        runBlocking {
            println(api.getPlaylists())
        }
    }
}