package com.usmonie.word.features.dashboard.domain.repository

import com.usmonie.word.features.dashboard.domain.models.Language
import com.usmonie.word.features.dashboard.domain.models.NotificationTime
import com.usmonie.word.features.dashboard.domain.models.Theme

interface UserRepository {
    var currentTheme: Theme

    var wasOnboardingShowed: Boolean

    var lastPracticeTime: Long

    var wordsPerDayCount: Int
    var nativeLanguage: Language
    var notificationsTime: NotificationTime

}
