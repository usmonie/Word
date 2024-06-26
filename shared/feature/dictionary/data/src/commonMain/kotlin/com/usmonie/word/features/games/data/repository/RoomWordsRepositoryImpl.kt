package com.usmonie.word.features.games.data.repository

import com.usmonie.core.domain.tools.fastMap
import com.usmonie.word.features.games.data.api.WordApi
import com.usmonie.word.features.games.data.api.models.WordDto
import com.usmonie.word.features.games.data.db.room.DictionaryDatabase
import com.usmonie.word.features.games.data.db.room.models.SearchHistoryDb
import com.usmonie.word.features.games.data.db.room.models.WordDb
import com.usmonie.word.features.games.data.db.room.models.WordFavorite
import com.usmonie.word.features.games.data.db.room.models.mapper.toDb
import com.usmonie.word.features.games.data.db.room.models.mapper.toDomain
import com.usmonie.word.features.games.domain.models.WordCombined
import com.usmonie.word.features.games.domain.models.WordEtymology
import com.usmonie.word.features.games.domain.repository.WordsRepository

internal class RoomWordsRepositoryImpl(
    dictionaryDatabase: DictionaryDatabase,
    private val api: WordApi,
) : WordsRepository {
    private val wordDao by lazy { dictionaryDatabase.wordDao() }
    private val favoritesDao by lazy { dictionaryDatabase.favoritesDao() }

    override suspend fun searchWords(query: String, limit: Int, offset: Int): List<WordCombined> {
        try {
            val found = api.searchWords(query, limit, offset)

            wordDao.insert(found.fastMap { it.toDb() })
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return wordDao.query(query).asSequence().mapDbToCombined { checkFavorite(it) }
    }

    override suspend fun getRandomWord(maxSymbols: Int): WordCombined {
        val result = api.randomWord(maxSymbols).asSequence().map { it.toDb() }

        wordDao.insert(result.toList())

        return result.mapDbToCombined(::checkFavorite)[0]
    }

    override suspend fun getWordOfTheDay(): WordCombined {
        val result = api.getWordOfTheDay().asSequence().map { it.toDb() }

        wordDao.insert(result.toList())

        return result.mapDbToCombined(::checkFavorite)[0]
    }

    override suspend fun isFavorite(word: String): Boolean {
        return checkFavorite(word)
    }

    override suspend fun addFavorite(word: String) {
        favoritesDao.insert(WordFavorite(word))
    }

    override suspend fun deleteFavorite(word: String) {
        favoritesDao.unfavorite(word)
    }

    override suspend fun getAllFavorites(): List<WordCombined> {
        return favoritesDao.favorites()
            .asSequence()
            .flatMap { it.words }
            .mapDbToCombined(::checkFavorite)
    }

    override suspend fun addToSearchHistory(word: String): List<WordCombined> {
        wordDao.insertHistory(SearchHistoryDb(word))
        return getSearchHistory()
    }

    override suspend fun getSearchHistory(): List<WordCombined> {
        return wordDao.searchHistory()
            .asSequence()
            .flatMap { it.words }
            .mapDbToCombined(::checkFavorite)
    }

    override suspend fun clearSearchHistory() {
        wordDao.clearSearchHistory()
    }

    internal suspend fun addSearchHistory(history: List<SearchHistoryDb>) {
        wordDao.insertHistory(history)
    }

    internal suspend fun addFavorites(favorites: List<WordFavorite>) {
        favoritesDao.insert(favorites)
    }

    private suspend fun checkFavorite(word: String): Boolean {
        return favoritesDao.query(word) != null
    }
}

private suspend fun Sequence<WordDto>.mapDtoToCombined(checkFavorite: suspend (String) -> Boolean) =
    map { it.toDb() }.mapDbToCombined(checkFavorite)

private suspend fun Sequence<WordDb>.mapDbToCombined(checkFavorite: suspend (String) -> Boolean) =
    map { it.toDomain() }
        .groupBy { it.word }
        .mapValues { entry ->
            entry.value
                .groupBy { word ->
                    Triple(word.etymologyText, word.etymologyNumber, word.sounds)
                }
                .map { etymology ->
                    WordEtymology(
                        etymology.key.first,
                        etymology.key.second,
                        etymology.value,
                        etymology.key.third,
                    )
                }
        }
        .map { entry ->
            val isFavorite = checkFavorite(entry.key)

            WordCombined(entry.value, isFavorite, entry.key)
        }
