package com.usmonie.word.features.onboarding.ui.analytics

import com.usmonie.word.features.dashboard.domain.models.NotificationTime
import wtf.word.core.domain.models.AnalyticsEvent

sealed class OnboardingAnalyticsEvent(key: String, data: EventData) : AnalyticsEvent(key, data) {

    data class SelectedNativeLanguage(
        val language: String
    ) : OnboardingAnalyticsEvent("USER_NATIVE_LANG", Data(language))

    data class SelectedYears(
        val years: String
    ) : OnboardingAnalyticsEvent("USER_YEARS", Data(years))

    data class SelectedWordsPerDay(
        val wordsCount: Int
    ) : OnboardingAnalyticsEvent("USER_WORDS_COUNT", Data(wordsCount))

    data class SelectedReminderTime(
        val notificationTime: NotificationTime
    ) : OnboardingAnalyticsEvent("USER_REMINDER_TIME", Data(notificationTime))

}

data class Data<T>(val data: T) : AnalyticsEvent.EventData