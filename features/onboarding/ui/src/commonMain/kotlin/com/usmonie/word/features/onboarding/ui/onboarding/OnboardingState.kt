package com.usmonie.word.features.onboarding.ui.onboarding

import com.usmonie.word.features.dashboard.domain.models.Language
import com.usmonie.word.features.dashboard.domain.models.LanguageLevel
import com.usmonie.word.features.dashboard.domain.models.NotificationTime
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState

sealed class OnboardingState : ScreenState {
    data object SelectNativeLanguage : OnboardingState()
    data object HowOldAreUser : OnboardingState()
    data object SelectLanguageLevel : OnboardingState()
    data object SelectCountWordsPerDay : OnboardingState()
    data object SelectReminderTime : OnboardingState()
}

sealed class OnboardingAction : ScreenAction {
    data class SelectedNativeLanguage(val language: Language) : OnboardingAction()
    data class SelectedYears(val years: String) : OnboardingAction()
    data class SelectedLanguageLevel(val languageLevel: LanguageLevel): OnboardingAction()
    data class SelectedWordsCountPerDay(val count: Int) : OnboardingAction()
    data class SelectedReminderTime(val notificationTime: NotificationTime) : OnboardingAction()
    data class OnBackSelected(val page: Int) : OnboardingAction()
}

sealed class OnboardingEvent : ScreenEvent {
    data object SelectNativeLanguage : OnboardingEvent()
    data object SelectYears : OnboardingEvent()
    data object SelectLanguageLevel : OnboardingEvent()
    data object SelectCountWordsPerDay : OnboardingEvent()
    data object SelectReminderTime : OnboardingEvent()
    data object SelectedReminderTime : OnboardingEvent()
}

sealed class OnboardingEffect : ScreenEffect {
    class RequestNotificationPermission : OnboardingEffect()

    class NextGraph : OnboardingEffect()
}

