package com.usmonie.word.features.dashboard.data.repository

import com.usmonie.word.features.dashboard.data.api.WordApi
import com.usmonie.word.features.dashboard.data.api.models.WordDto
import com.usmonie.word.features.dashboard.data.db.models.WordDb
import com.usmonie.word.features.dashboard.data.db.models.WordFavorite
import com.usmonie.word.features.dashboard.data.db.models.toDatabase
import com.usmonie.word.features.dashboard.data.db.models.toDomain
import com.usmonie.word.features.dashboard.domain.models.WordCombined
import com.usmonie.word.features.dashboard.domain.models.WordEtymology
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.find
import wtf.word.core.domain.tools.fastForEach
import wtf.word.core.domain.tools.fastMap

class WordsRepositoryImpl(private val realm: Realm, private val api: WordApi) : WordRepository {

    override suspend fun searchWords(query: String, limit: Int, offset: Int): List<WordCombined> {
        val found = api.searchWords(query, limit, offset)

        try {
            if (!realm.isClosed()) {
                found.fastForEach { wordDto ->

                    realm.write {
                        val wordDb = wordDto.toDatabase()
                        val findLatestQuery = realm.query<WordDb>(
                            "primaryKey == $0",
                            wordDb.word
                                    + wordDb.pos
                                    + wordDb.lang
                                    + wordDb.langCode
                                    + wordDb.etymologyNumber
                                    + wordDb.etymologyText
                        ).find().firstOrNull()

                        if (findLatestQuery == null) {
                            copyToRealm(wordDb, UpdatePolicy.ALL)
                        }
                    }
                }
            }
        } catch (e: Exception) {

        }

        return realm.query<WordDb>(query = "word = $0", query)
            .limit(limit)
            .find()
            .asSequence()
            .mapDbToCombined()
    }

    override suspend fun getRandomWord(maxSymbols: Int): WordCombined {
        val result = api.randomWord(maxSymbols).asSequence().mapDtoToCombined()
        return result[0]
    }

    override suspend fun getWordOfTheDay(): WordCombined {
        val result = api.randomWord(20).asSequence().mapDtoToCombined()
        return result[0]
    }

    override suspend fun addFavorite(word: String) {
        realm.write {
            copyToRealm(WordFavorite(word), UpdatePolicy.ALL)
        }
    }

    override suspend fun deleteFavorite(word: String) {
        realm.write { delete(WordFavorite(word)) }
    }

    override suspend fun getAllFavorites(): List<WordCombined> {
        realm.query<WordFavorite>()
            .find {
                it.fastMap {
                    println(it)
                }
            }

        return listOf()
    }

    override suspend fun getSearchHistory(): List<WordCombined> {
        return listOf()
    }

    override suspend fun clearSearchHistory(): List<WordCombined> {
        return listOf()
    }

    private fun Sequence<WordDto>.mapDtoToCombined() = map { it.toDatabase() }.mapDbToCombined()

    private fun Sequence<WordDb>.mapDbToCombined() = map { it.toDomain() }
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
            val isFavorite = realm.query<WordFavorite>(query = "word = $0", entry.key)
                .find()
                .isNotEmpty()

            WordCombined(entry.value, isFavorite, entry.key)
        }
}