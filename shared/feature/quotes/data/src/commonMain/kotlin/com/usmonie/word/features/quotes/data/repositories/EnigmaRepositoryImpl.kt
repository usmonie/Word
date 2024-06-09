package com.usmonie.word.features.quotes.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.usmonie.word.features.games.domain.repositories.EnigmaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class EnigmaRepositoryImpl(
    private val datastore: DataStore<Preferences>,
) : EnigmaRepository {
    override fun getLevel(): Flow<Int> {
        return datastore.data.map { it[intPreferencesKey(LEVEL_KEY)] ?: 1 }
    }

    override suspend fun setLevel(level: Int) {
        datastore.edit {
            it[intPreferencesKey(LEVEL_KEY)] = level
        }
    }

    companion object {
        private const val LEVEL_KEY = "level"
    }
}
