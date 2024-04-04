package com.usmonie.word.features.dashboard.ui.models

data class LearningStatus(val title: String, val status: Int, val description: String)

data class UserLearningStatusUi(
    val learned: Int,
    val pending: Int,
    val new: Int,
    val streak: Int
)


data class PracticeStatus(val lastPracticeTime: Long)

sealed class StreakStatus {
    data class Continues(val lastPracticeTime: Long, )
}
