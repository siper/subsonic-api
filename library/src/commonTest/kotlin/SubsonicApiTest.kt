import kotlinx.coroutines.runBlocking
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
    fun `Test random songs response`() {
        runBlocking {
            println(api.getRandomSongs(1))
        }
    }

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
            println(api.getPlaylist("2306"))
        }
    }

    @Test
    fun `Test playlists response`() {
        runBlocking {
            println(api.getPlaylists())
        }
    }

    @Test
    fun `Test starred response`() {
        runBlocking {
            println(api.getStarred2())
        }
    }
}