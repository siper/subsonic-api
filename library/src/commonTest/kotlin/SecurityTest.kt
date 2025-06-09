import ru.stersh.subsonic.api.encodePassword
import ru.stersh.subsonic.api.generateToken
import kotlin.test.Test
import kotlin.test.assertTrue

class SecurityTest {

    @Test
    fun `Token generates correct`() {
        val salt = "c19b2d"
        val password = "sesame"
        val token = generateToken(salt, password)
        assertTrue { token == "26719a1196d2a940705a59634eb18eab" }
    }

    @Test
    fun `Password encoded correct`() {
        val password = "sesame"
        val encodedPassword = encodePassword(password)
        assertTrue { encodedPassword == "enc:736573616d65" }
    }
}