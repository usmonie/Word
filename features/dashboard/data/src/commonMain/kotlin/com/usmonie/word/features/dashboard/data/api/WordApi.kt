package com.usmonie.word.features.dashboard.data.api

import com.usmonie.word.features.dashboard.data.api.models.WordDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class WordApi(private val baseUrl: String) {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }

//        install(JsonFeature) {
//            serializer = GsonSerializer()
//        }
    }

    internal suspend fun searchWords(query: String, limit: Int, offset: Int): List<WordDto> {
        val response = client.get {
            url {
                takeFrom(baseUrl)
                encodedPath = "dictionary/search"
                parameter("query", query)
                parameter("limit", limit)
                parameter("offset", offset)
            }
        }
        return response.body()
    }


    internal suspend fun randomWord(maxSymbols: Int): List<WordDto> {
        return client.get {
            url {
                takeFrom(baseUrl)
                encodedPath = "/dictionary/random_word"
                parameter("max_symbols", maxSymbols)
            }
        }.body()
    }

    internal suspend fun getWordOfTheDay(): List<WordDto> {
        return client.get {
            url {
                takeFrom(baseUrl)
                encodedPath = "/dictionary/word_of_the_day"
            }
        }.body()
    }
}