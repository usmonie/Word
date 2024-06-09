package com.usmonie.word.features.games.domain.repositories

import kotlinx.coroutines.flow.Flow

interface EnigmaRepository {
    fun getLevel(): Flow<Int>
    suspend fun setLevel(level: Int)
}
