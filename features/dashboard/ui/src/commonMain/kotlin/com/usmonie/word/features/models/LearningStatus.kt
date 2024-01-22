package com.usmonie.word.features.models

data class LearningStatus(val title: String, val status: String, val description: String)

data class UserLearningStatusUi(
    val learned: Int,
    val pending: Int,
    val new: Int,
    val streak: Int
)

sealed class UserLearningStatus {
    data object NotStarted : UserLearningStatus()

    data class Started(
        val learned: Int,
        val pending: Int,
        val new: Int,
        val streak: Int
    ): UserLearningStatus()
}

data class PracticeStatus(val lastPracticeTime: Long)

sealed class StreakStatus {
    data class Continues(val lastPracticeTime: Long, )
}
