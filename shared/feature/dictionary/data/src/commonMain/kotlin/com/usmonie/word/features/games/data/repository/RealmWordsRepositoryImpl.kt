package com.usmonie.word.features.games.data.repository

import com.usmonie.word.features.games.data.api.WordApi
import com.usmonie.word.features.games.data.api.models.WordDto
import com.usmonie.word.features.games.data.db.models.WordDb
import com.usmonie.word.features.games.data.db.models.WordFavorite
import com.usmonie.word.features.games.data.db.models.WordSearchHistoryDb
import com.usmonie.word.features.games.data.db.models.toDatabase
import com.usmonie.word.features.games.data.db.models.toDomain
import com.usmonie.word.features.games.domain.models.WordCombined
import com.usmonie.word.features.games.domain.models.WordEtymology
import com.usmonie.word.features.games.domain.repository.WordsRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import io.realm.kotlin.query.find

@Suppress("TooManyFunctions")
internal class RealmWordsRepositoryImpl(
    private val realm: Realm,
    private val api: WordApi,
) : WordsRepository {

    override suspend fun searchWords(query: String, limit: Int, offset: Int): List<WordCombined> {
        return emptyList()
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
        return false
    }

    override suspend fun getWordOfTheDay(): WordCombined {
        val result = api.getWordOfTheDay().asSequence()

        if (!realm.isClosed()) {
            realm.write {
                result.forEach {
                    copyToRealm(it.toDatabase(), UpdatePolicy.ALL)
                }
            }
        }

        return result.mapDtoToCombined(::checkFavorite)[0]
    }

    override suspend fun addFavorite(word: String) = Unit

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

    override suspend fun addToSearchHistory(word: String): List<WordCombined> = emptyList()

    override suspend fun getSearchHistory(): List<WordCombined> = emptyList()

    override suspend fun clearSearchHistory() = Unit

    internal fun getOldSearchHistory(): List<WordSearchHistoryDb> {
        val words = realm.query<WordSearchHistoryDb>()
            .sort("date", Sort.DESCENDING)
            .find()
        return words
    }

    internal fun getOldFavorites(): List<WordFavorite> {
        val words = realm.query<WordFavorite>()
            .sort("date", Sort.DESCENDING)
            .find()
        return words
    }

    internal fun realmDeleteAndClose() {
        try {
            realm.writeBlocking {
                deleteAll()
            }
        } catch (_: Exception) {
        }

        realm.close()
    }

    private fun checkFavorite(word: String): Boolean {
        return realm.query<WordFavorite>(query = "word = $0", word)
            .find()
            .isNotEmpty()
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
