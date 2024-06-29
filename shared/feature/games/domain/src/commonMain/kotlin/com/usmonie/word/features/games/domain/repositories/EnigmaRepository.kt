package com.usmonie.word.features.games.domain.repositories

import com.usmonie.word.features.games.domain.models.UserProgress
import kotlinx.coroutines.flow.Flow

interface EnigmaRepository {
	fun getLevel(): Flow<Int>
	suspend fun setLevel(level: Int)
}
