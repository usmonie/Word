package com.usmonie.word.features.games.domain.models

data class UserPerformanceMetrics(
	val averageSolveTime: Long,
	val averageHintsUsed: Double,
	val successRate: Double,
	val totalPuzzlesSolved: Int
)
