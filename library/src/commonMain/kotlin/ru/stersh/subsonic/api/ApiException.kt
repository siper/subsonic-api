package ru.stersh.subsonic.api

import kotlinx.io.IOException

data class ApiException(val code: Int, override val message: String) : IOException()
