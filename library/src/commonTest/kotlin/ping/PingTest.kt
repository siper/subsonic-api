package ping

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import kotlinx.coroutines.runBlocking
import ru.stersh.subsonic.api.ApiException
import ru.stersh.subsonic.api.SubsonicApi
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertTrue

class PingTest {

    @Test
    fun `Ping response success`() {
        runBlocking {
            val api = SubsonicApi(
                baseUrl = "http://demo.subsonic.org",
                username = "guest",
                password = "guest",
                apiVersion = Api.VERSION,
                clientId = "SubsonicKotlinApi",
                useLegacyAuth = false,
                baseClient = HttpClient(
                    MockEngine {
                        respond(PingResponse.SUCCESS, headers = headersOf(HttpHeaders.ContentType, "application/json"))
                    }
                )
            )
            val response = api.ping()
            assertTrue { response.data.version == Api.VERSION }
            assertTrue { response.data.status == Api.STATUS_OK }
        }
    }

    @Test
    fun `Ping response failure`() {
        runBlocking {
            val api = SubsonicApi(
                baseUrl = "http://demo.subsonic.org",
                username = "guest",
                password = "guest",
                apiVersion = Api.VERSION,
                clientId = "SubsonicKotlinApi",
                useLegacyAuth = false,
                baseClient = HttpClient(
                    MockEngine { httpRequestData ->
                        respond(PingResponse.ERROR, headers = headersOf(HttpHeaders.ContentType, "application/json"))
                    }
                )
            )
            val response = runCatching { api.ping() }
            assertFails { response.getOrThrow() }
            assertTrue {
                val exception = response.exceptionOrNull()
                exception is ApiException && exception.code == 10 && exception.message == "Required parameter is missing."
            }
        }
    }
}