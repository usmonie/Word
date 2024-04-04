package com.usmonie.word.features.dashboard.domain.repository

import com.usmonie.word.features.dashboard.domain.models.WordCombined

interface WordRepository {
    suspend fun searchWords(query: String, limit: Int, offset: Int): List<WordCombined>
    suspend fun getRandomWord(maxSymbols: Int): WordCombined
    suspend fun getWordOfTheDay(): WordCombined
    suspend fun isFavorite(word: String): Boolean
    suspend fun addFavorite(word: String)
    suspend fun deleteFavorite(word: String)
    suspend fun getAllFavorites(): List<WordCombined>
    suspend fun getSearchHistory(): List<WordCombined>
    suspend fun clearSearchHistory()
}