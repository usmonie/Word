package com.usmonie.word.features.dashboard.domain.models

sealed class UserLearningStatus {
    data object NotStarted : UserLearningStatus()

    data class Started(
        val learned: Int,
        val pending: Int,
        val new: Int,
        val streak: Int,
        val wordsPerDayCount: Int,
        val lastPracticeTime: Long,
        val reminderTime: NotificationTime,
        val nativeLanguage: Language
    ): UserLearningStatus()
}
