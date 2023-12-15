package com.usmonie.word.features.dashboard.data.repository

import com.usmonie.word.features.dashboard.data.api.WordApi
import com.usmonie.word.features.dashboard.data.api.models.WordDto
import com.usmonie.word.features.dashboard.data.db.models.WordDb
import com.usmonie.word.features.dashboard.data.db.models.WordFavorite
import com.usmonie.word.features.dashboard.data.db.models.WordSearchHistoryDb
import com.usmonie.word.features.dashboard.data.db.models.toDatabase
import com.usmonie.word.features.dashboard.data.db.models.toDomain
import com.usmonie.word.features.dashboard.domain.models.WordCombined
import com.usmonie.word.features.dashboard.domain.models.WordEtymology
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import io.realm.kotlin.query.find
import wtf.word.core.domain.tools.fastForEach

class WordsRepositoryImpl(private val realm: Realm, private val api: WordApi) : WordRepository {

    override suspend fun searchWords(query: String, limit: Int, offset: Int): List<WordCombined> {
        val found = api.searchWords(query, limit, offset)

        try {
            if (!realm.isClosed()) {
                found.fastForEach { wordDto ->
                    realm.write {
                        val wordDb = wordDto.toDatabase()
                        val findLatestQuery = realm
                            .query<WordDb>(
                                "primaryKey == $0",
                                wordDb.word
                                        + wordDb.pos
                                        + wordDb.lang
                                        + wordDb.langCode
                                        + wordDb.etymologyNumber
                                        + wordDb.etymologyText
                            )
                            .find()
                            .firstOrNull()

                        if (findLatestQuery == null) {
                            copyToRealm(wordDb, UpdatePolicy.ALL)
                            copyToRealm(WordSearchHistoryDb(wordDb.word), UpdatePolicy.ALL)
                        }
                    }
                }
            }
        } catch (e: Exception) { e.printStackTrace() }

        return realm.query<WordDb>(query = "word = $0", query)
            .limit(limit)
            .find()
            .asSequence()
            .mapDbToCombined(::checkFavorite)
    }

    override suspend fun getRandomWord(maxSymbols: Int): WordCombined {
        val result = api.randomWord(maxSymbols).asSequence()

        if (!realm.isClosed()) {
            realm.write {
                result.forEach {
                    copyToRealm(it.toDatabase(), UpdatePolicy.ALL)
                }
            }
        }

        return result.mapDtoToCombined(::checkFavorite)[0]
    }

    override suspend fun isFavorite(word: String): Boolean {
        val findLatestQuery = realm
            .query<WordFavorite>(
                "word == $0",
                word
            )
            .first()
            .find()

        return findLatestQuery != null
    }

    override suspend fun getWordOfTheDay(): WordCombined {
        val result = api.randomWord(20).asSequence()

        if (!realm.isClosed()) {
            realm.write {
                result.forEach {
                    copyToRealm(it.toDatabase(), UpdatePolicy.ALL)
                }
            }
        }

        return result.mapDtoToCombined(::checkFavorite)[0]
    }

    override suspend fun addFavorite(word: String) {
        realm.write {
            copyToRealm(WordFavorite(word), UpdatePolicy.ALL)
        }
    }

    override suspend fun deleteFavorite(word: String) {
        val findLatestQuery = realm
            .query<WordFavorite>(
                "word == $0",
                word
            )
            .first()
            .find()
        realm.write {
            findLatestQuery?.let { word ->
                findLatest(word)?.let { delete(it) }

            }
        }
    }

    override suspend fun getAllFavorites(): List<WordCombined> {
        val words = realm.query<WordFavorite>()
            .sort("date", Sort.DESCENDING)
            .find { wordFavorites ->
                wordFavorites.flatMap { word ->
                    realm.query<WordDb>(query = "word = $0", word.word)
                        .find()
                        .asSequence()
                        .mapDbToCombined { true }
                }
            }

        return words
    }

    override suspend fun getSearchHistory(): List<WordCombined> {
        val words = realm.query<WordSearchHistoryDb>()
            .sort("date",Sort.DESCENDING)
            .find { wordsHistory ->
                wordsHistory.flatMap { word ->
                    realm.query<WordDb>(query = "word = $0", word.word)
                        .find()
                        .asSequence()
                        .mapDbToCombined(::checkFavorite)
                }
            }

        return words
    }

    override suspend fun clearSearchHistory() {
        realm.write {
            realm.query<WordSearchHistoryDb>()
                .find { wordSearchHistories ->
                    wordSearchHistories.fastForEach { wordSearchHistory -> delete(wordSearchHistory) }
                }
        }
    }

    private fun Sequence<WordDto>.mapDtoToCombined(checkFavorite: (String) -> Boolean) =
        map { it.toDatabase() }.mapDbToCombined(checkFavorite)

    private fun Sequence<WordDb>.mapDbToCombined(checkFavorite: (String) -> Boolean) =
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

    private fun checkFavorite(word: String): Boolean {
        return realm.query<WordFavorite>(query = "word = $0", word)
            .find()
            .isNotEmpty()
    }
}