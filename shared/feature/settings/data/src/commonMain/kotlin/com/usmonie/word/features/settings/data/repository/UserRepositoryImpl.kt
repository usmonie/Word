package com.usmonie.word.features.settings.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.usmonie.word.features.settings.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : UserRepository {

    override val hintsCount: Flow<Int>
        get() = dataStore.data.map {
            val currentHints = it[CURRENT_USER_HINTS_COUNT_KEY]
            if (currentHints == null) {
                addHints(5)
                return@map 5
            }
            currentHints
        }

    override val livesCount: Flow<Int>
        get() = dataStore.data.map {
            val currentLives = it[CURRENT_USER_LIVES_COUNT_KEY]
            if (currentLives == null) {
                addLives(5)
                return@map 5
            }
            currentLives
        }

    override suspend fun addHints(hintsCount: Int) {
        dataStore.edit {
            val currentHints = it[CURRENT_USER_HINTS_COUNT_KEY]
            if (currentHints != null && hintsCount > 0) {
                it[CURRENT_USER_HINTS_COUNT_KEY] = currentHints + hintsCount
            }
        }
    }

    override suspend fun useHints(hintsCount: Int) {
        dataStore.edit {
            val currentHints = it[CURRENT_USER_HINTS_COUNT_KEY]
            if (currentHints != null && currentHints >= hintsCount) {
                it[CURRENT_USER_HINTS_COUNT_KEY] = currentHints - hintsCount
            }
        }
    }

    override suspend fun addLives(livesCount: Int) {
        dataStore.edit {
            val livesHints = it[CURRENT_USER_LIVES_COUNT_KEY]
            if (livesHints != null && livesCount > 0) {
                it[CURRENT_USER_LIVES_COUNT_KEY] = livesHints + livesCount
            }
        }
    }

    override suspend fun useLives(livesCount: Int) {
        dataStore.edit {
            val livesHints = it[CURRENT_USER_LIVES_COUNT_KEY]
            if (livesHints != null && livesHints >= livesCount) {
                it[CURRENT_USER_LIVES_COUNT_KEY] = livesHints - livesCount
            }
        }
    }

    companion object {
        private val CURRENT_USER_HINTS_COUNT_KEY = intPreferencesKey("CURRENT_USER_HINTS_COUNT")
        private val CURRENT_USER_LIVES_COUNT_KEY = intPreferencesKey("CURRENT_USER_LIVES_COUNT")
    }
}
