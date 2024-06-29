package com.usmonie.word.features.games.domain.repositories

import com.usmonie.word.features.games.domain.models.UserProgress

interface UserProgressRepository {
	suspend fun saveProgress(progress: UserProgress)
	suspend fun getProgress(): UserProgress
}

internal class UserProgressRepositoryImpl : UserProgressRepository {
	override suspend fun saveProgress(progress: UserProgress) {
	
	}

	override suspend fun getProgress(): UserProgress {
		return UserProgress(0, 0L, 0, 0, 0)
	}
}
