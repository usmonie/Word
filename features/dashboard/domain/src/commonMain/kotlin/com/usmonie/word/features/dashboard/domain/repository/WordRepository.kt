package com.usmonie.word.features.dashboard.domain.repository

import com.usmonie.word.features.dashboard.domain.models.WordDomain
import com.usmonie.word.features.dashboard.domain.models.WordParcelable

interface WordRepository {
    val isDatabaseEmpty: Boolean

    suspend fun search(query: String, offset: Long, limit: Long): List<WordDomain>
    suspend fun searchInside(query: String, offset: Long, limit: Long): List<WordDomain>
    suspend fun searchExactly(query: String, offset: Long, limit: Long): List<WordDomain>
    suspend fun searchInDescription(query: String, offset: Long, limit: Long): List<WordDomain>
    suspend fun searchSynonymsForWord(query: String, offset: Long, limit: Long): List<WordDomain>
    suspend fun filter(char: Char, offset: Long, limit: Long): List<WordDomain>
    suspend fun put(words: List<WordParcelable>)
    suspend fun addToFavourites(word: WordDomain)
    suspend fun deleteFromFavourites(word: WordDomain)
    suspend fun getAllFavourites(): List<WordDomain>
    suspend fun getSearchHistory(offset: Long, limit: Long): List<WordDomain>
    suspend fun getWordOfTheDay(): WordDomain
    suspend fun getRandomWord(symbolsCount: Int): WordDomain
    suspend fun clearSearchHistory()
}