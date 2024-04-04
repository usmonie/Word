package com.usmonie.word.features.admob.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class LoginApi(private val baseUrl: String) {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun loginViaGoogle(googleIdToken: String) {
        val response = client.get {
            url {
                takeFrom(baseUrl)
                encodedPath = "auth/google"
                parameter("googleAuthId", googleIdToken)
            }
        }
        println(response.toString())
    }
}