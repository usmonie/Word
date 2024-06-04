package com.usmonie.word.features.dictionary.domain.repository

import com.usmonie.word.features.dictionary.domain.models.WordCombined

interface WordsRepository {
    suspend fun searchWords(query: String, limit: Int, offset: Int): List<WordCombined>
    suspend fun getRandomWord(maxSymbols: Int): WordCombined
    suspend fun getWordOfTheDay(): WordCombined
    suspend fun isFavorite(word: String): Boolean
    suspend fun addFavorite(word: String)
    suspend fun deleteFavorite(word: String)
    suspend fun getAllFavorites(): List<WordCombined>
    suspend fun addToSearchHistory(word: String): List<WordCombined>
    suspend fun getSearchHistory(): List<WordCombined>
    suspend fun clearSearchHistory()
}
