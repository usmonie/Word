package com.usmonie.word.features.settings.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val hintsCount: Flow<Int>
    val livesCount: Flow<Int>

    suspend fun addHints(hintsCount: Int)
    suspend fun useHints(hintsCount: Int)

    suspend fun addLives(livesCount: Int)
    suspend fun useLives(livesCount: Int)
}
