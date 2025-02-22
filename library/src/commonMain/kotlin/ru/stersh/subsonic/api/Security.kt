package ru.stersh.subsonic.api

import io.ktor.utils.io.core.toByteArray
import org.kotlincrypto.hash.md.MD5
import kotlin.random.Random

internal object Security {
    private val md5 = MD5()
    private val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')

    fun generateSalt(): String {
        val length = Random.nextInt(10, 17)
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun getToken(salt: String, password: String): String {
        return md5(password + salt)
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun md5(input: String): String {
        return md5.digest(input.toByteArray()).toHexString()
    }
}
