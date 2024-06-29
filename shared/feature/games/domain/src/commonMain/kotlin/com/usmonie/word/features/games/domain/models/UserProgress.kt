package com.usmonie.word.features.games.domain.models


data class UserProgress(
	val solvedPuzzles: Int,
	val averageTime: Long,
	val hintsUsed: Int,
	val successfulAttempts: Int,
	val totalAttempts: Int,
) {
	fun toUserPerformanceMetrics(): UserPerformanceMetrics {
		return UserPerformanceMetrics(
			averageSolveTime = averageTime,
			averageHintsUsed = hintsUsed.toDouble() / solvedPuzzles.coerceAtLeast(1),
			successRate = successfulAttempts.toDouble() / totalAttempts.coerceAtLeast(1),
			totalPuzzlesSolved = solvedPuzzles
		)
	}
}
