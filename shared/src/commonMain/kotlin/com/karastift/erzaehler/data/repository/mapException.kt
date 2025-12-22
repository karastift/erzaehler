package com.karastift.erzaehler.data.repository

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

fun mapException(e: Throwable): Exception {
    println(e.message)
    return when (e) {
        is ClientRequestException ->
            Exception("Malformed request. Make sure your app is updated.")

        is ServerResponseException ->
            Exception("Server is currently unavailable.")

        is IOException ->
            Exception("Network error. Please check your connection.")

        is NoTransformationFoundException,
        is SerializationException ->
            Exception("The server returned malformed data.")

        else ->
            Exception("Sorry! Something went wrong.")
    }
}