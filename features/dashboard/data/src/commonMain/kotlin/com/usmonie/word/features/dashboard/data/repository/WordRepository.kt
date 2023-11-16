package com.usmonie.word.features.dashboard.data.repository

import app.cash.sqldelight.db.SqlDriver
import com.usmonie.word.features.dashboard.data.db.Database
import com.usmonie.word.features.dashboard.data.db.Word
import com.usmonie.word.features.dashboard.domain.models.WordDomain
import com.usmonie.word.features.dashboard.domain.models.WordParcelable
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import org.lighthousegames.logging.logging

class WordRepositoryImpl(driver: SqlDriver) : WordRepository {
    private val database = Database(driver)

    private val logging = logging("WordRepositoryImpl")
    override val isDatabaseEmpty: Boolean
        get() = database.wordQueries.countOfRows().executeAsOne() == 0L

    override suspend fun search(query: String, offset: Long, limit: Long): List<WordDomain> {
        return database.wordQueries.searchWord(query, query, limit, offset).executeAsList()
            .map {
                if (it.word.equals(query, true)) {
                    database.wordQueries.insertSearchQuery(query, it.id)
                }
                mapWordDomain(it)
            }
    }

    override suspend fun searchExactly(query: String, offset: Long, limit: Long): List<WordDomain> {
        return database.wordQueries.searchExactWord(query, limit, offset).executeAsList()
            .map {
                logging.d { it }
                if (it.word == query) database.wordQueries.insertSearchQuery(query, it.id)
                mapWordDomain(it)
            }
    }

    override suspend fun searchInside(query: String, offset: Long, limit: Long): List<WordDomain> {
        return database.wordQueries.searchInsideWord(query, query, limit, offset).executeAsList()
            .map {
                logging.d { it }

                if (it.word.equals(query, true)) {
                    database.wordQueries.insertSearchQuery(query, it.id)
                }
                mapWordDomain(it)
            }
    }

    override suspend fun searchInDescription(
        query: String,
        offset: Long,
        limit: Long
    ): List<WordDomain> {
        return database.wordQueries.searchInDescription(query, limit, offset).executeAsList()
            .map {
                logging.d { it }

                if (it.word.equals(query, true)) {
                    database.wordQueries.insertSearchQuery(query, it.id)
                }
                mapWordDomain(it)
            }
    }

    override suspend fun searchSynonymsForWord(
        query: String,
        offset: Long,
        limit: Long
    ): List<WordDomain> {
        return database.wordQueries.searchBySynonym(query.lowercase(), limit, offset).executeAsList()
            .map {
                if (it.word.equals(query, true)) {
                    database.wordQueries.insertSearchQuery(query.lowercase(), it.id)
                }
                mapWordDomain(it)
            }
    }

    override suspend fun filter(char: Char, offset: Long, limit: Long): List<WordDomain> {
        return database.wordQueries
            .filterByChar(char.toString(), limit, offset)
            .executeAsList()
            .map(::mapWordDomain)
    }

    override suspend fun put(words: List<WordParcelable>) {
        database.transaction {
            afterRollback { logging.d { "No words were inserted." } }
            afterCommit { logging.d { "${words.size} words were inserted." } }

            with(database.wordQueries) {
                words.forEach { wordDomain ->
                    insertWord(wordDomain.word, wordDomain.pos)

                    val wordId = lastInsertRowId().executeAsOne()

                    wordDomain.synonyms?.split("; ")?.forEach { synonym ->
                        insertSynonym(synonym.lowercase())
                        val synonymId = lastInsertRowId().executeAsOne()
                        linkWordToSynonym(wordId, synonymId)
                    }
                    wordDomain.definitions.forEach {
                        insertDefinition(wordId, it)
                    }
                }
            }
        }
    }

    override suspend fun addToFavourites(word: WordDomain) {
        logging.d { word.isFavourite }

        database.wordQueries.addFavoriteWord(word.id)
    }

    override suspend fun deleteFromFavourites(word: WordDomain) {
        database.wordQueries.removeFavoriteWord(word.id)
    }

    override suspend fun getAllFavourites(): List<WordDomain> {
        return database.wordQueries.getAllFavorites(40, 0)
            .executeAsList()
            .map { mapWordDomain(it) }
    }

    override suspend fun getSearchHistory(offset: Long, limit: Long): List<WordDomain> {
        return database.transactionWithResult {
            database.wordQueries.getSearchHistory(limit, offset) { _, _, _, wordId ->
                mapWordDomain(database.wordQueries.getWord(wordId).executeAsOne())
            }.executeAsList()
        }
    }

    override suspend fun getWordOfTheDay(): WordDomain {
        return mapWordDomain(database.wordQueries.getRandomWordOfTheDay().executeAsOne())
    }

    override suspend fun clearSearchHistory() {
        database.wordQueries.clearSearchHistory()
    }

    private fun mapWordDomain(it: Word): WordDomain {
        val synonyms = database.wordQueries.getSynonymsForWord(it.word).executeAsList()
        val definitions = database.wordQueries.getDefinitionsForWord(it.id).executeAsList()
        val isFavourite = isWordFavourite(it.id)
        return WordDomain(it.id, it.word, it.pos, synonyms, definitions, isFavourite)
    }

    private fun isWordFavourite(wordId: Long): Boolean {
        return database.wordQueries.isWordFavorite(wordId).executeAsOne()
    }
}