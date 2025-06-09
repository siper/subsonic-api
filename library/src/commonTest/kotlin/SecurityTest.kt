import ru.stersh.subsonic.api.Security
import kotlin.test.Test
import kotlin.test.assertTrue

class SecurityTest {

    @Test
    fun `Token generates correct`() {
        val salt = "c19b2d"
        val password = "sesame"
        val token = Security.getToken(salt, password)
        assertTrue { token == "26719a1196d2a940705a59634eb18eab" }
    }
}