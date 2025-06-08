package ru.stersh.subsonic.api

import io.ktor.utils.io.core.toByteArray
import org.kotlincrypto.hash.md.MD5
import kotlin.random.Random

internal object Security {
    private val md5 = MD5()
    private val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')

    private const val SALT_LENGTH = 6

    fun generateSalt(): String {
        return buildString {
            repeat(SALT_LENGTH) {
                append(allowedChars.random())
            }
        }
    }

    fun getToken(salt: String, password: String): String {
        return md5(password + salt)
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun md5(input: String): String {
        return md5.digest(input.toByteArray()).toHexString()
    }
}
