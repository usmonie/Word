package com.usmonie.word.features.games.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.games.domain.repositories.EnigmaRepository
import kotlinx.datetime.Clock

interface UpdateUserProgressUseCase : CoroutineUseCase<UpdateUserProgressUseCase.Param, Unit> {

	class Param(val won: Boolean, val startedTime: Long)
}

internal class UpdateUserProgressUseCaseImpl(
	private val enigmaRepository: EnigmaRepository
) : UpdateUserProgressUseCase {
	override suspend fun invoke(input: UpdateUserProgressUseCase.Param) {
		val currentUserProgress = enigmaRepository.getProgress()
		val solvedPuzzles = currentUserProgress.solvedPuzzles + if (input.won) 1 else 0
		val successfulAttempts = currentUserProgress.successfulAttempts + if (input.won) 1 else 0
		val totalAttempts = currentUserProgress.totalAttempts + 1
		val spentTime = if (input.won) Clock.System.now().epochSeconds - input.startedTime else 0

		val newAverageTime =
			((currentUserProgress.averageTime * successfulAttempts) + spentTime) / successfulAttempts.coerceAtLeast(1)

		enigmaRepository.saveProgress(
			currentUserProgress.copy(
				solvedPuzzles = solvedPuzzles,
				successfulAttempts = successfulAttempts,
				totalAttempts = totalAttempts,
				averageTime = newAverageTime
			),
		)
	}
}
