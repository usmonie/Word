package com.usmonie.word.features.onboarding.ui.onboarding

import androidx.compose.runtime.Immutable
import com.usmonie.word.features.dashboard.domain.models.Language
import com.usmonie.word.features.dashboard.domain.models.NotificationTime
import com.usmonie.word.features.dashboard.domain.usecase.SetOnboardingWasShowedUseCase
import com.usmonie.word.features.dashboard.domain.usecase.SetupNativeLanguageUseCase
import com.usmonie.word.features.dashboard.domain.usecase.SetupRemindersTimeUseCase
import com.usmonie.word.features.dashboard.domain.usecase.SetupWordsPerDayUseCase
import com.usmonie.word.features.onboarding.ui.analytics.OnboardingAnalyticsEvent
import wtf.speech.core.ui.BaseViewModel
import wtf.word.core.domain.Analytics

@Immutable
internal class OnboardingViewModel(
    private val setupWordsPerDayUseCase: SetupWordsPerDayUseCase,
    private val setupNativeLanguageUseCase: SetupNativeLanguageUseCase,
    private val setupRemindersTimeUseCase: SetupRemindersTimeUseCase,
    private val setupOnboardingWasShowedUseCase: SetOnboardingWasShowedUseCase,
    private val analytics: Analytics
) : BaseViewModel<OnboardingState, OnboardingAction, OnboardingEvent, OnboardingEffect>(
    OnboardingState.SelectNativeLanguage
) {
    override fun OnboardingState.reduce(event: OnboardingEvent) = when (event) {
        OnboardingEvent.SelectNativeLanguage -> OnboardingState.SelectNativeLanguage
        OnboardingEvent.SelectYears -> OnboardingState.HowOldAreUser
        OnboardingEvent.SelectCountWordsPerDay -> OnboardingState.SelectCountWordsPerDay
        OnboardingEvent.SelectReminderTime -> OnboardingState.SelectReminderTime
        OnboardingEvent.SelectedReminderTime -> this
    }

    override suspend fun processAction(action: OnboardingAction): OnboardingEvent = when (action) {
        is OnboardingAction.SelectedNativeLanguage -> {
            setupNativeLanguageUseCase(action.language)
            analytics.log(OnboardingAnalyticsEvent.SelectedNativeLanguage(action.language.name))

            OnboardingEvent.SelectYears
        }

        is OnboardingAction.SelectedYears -> {
            analytics.log(OnboardingAnalyticsEvent.SelectedYears(action.years))
            OnboardingEvent.SelectCountWordsPerDay
        }

        is OnboardingAction.SelectedWordsCountPerDay -> {
            analytics.log(OnboardingAnalyticsEvent.SelectedWordsPerDay(action.count))

            setupWordsPerDayUseCase(action.count)
            OnboardingEvent.SelectReminderTime
        }

        is OnboardingAction.SelectedReminderTime -> {
            analytics.log(OnboardingAnalyticsEvent.SelectedReminderTime(action.notificationTime))

            setupRemindersTimeUseCase(action.notificationTime)
            setupOnboardingWasShowedUseCase(Unit)
            OnboardingEvent.SelectedReminderTime
        }

        is OnboardingAction.OnBackSelected -> when (action.page) {
            1 -> OnboardingEvent.SelectNativeLanguage
            2 -> OnboardingEvent.SelectYears
            else -> OnboardingEvent.SelectCountWordsPerDay
        }
    }

    override suspend fun handleEvent(event: OnboardingEvent) = when (event) {
        is OnboardingEvent.SelectedReminderTime -> OnboardingEffect.NextGraph()
        else -> null
    }

    fun onLanguageSelected(language: Language) {
        handleAction(OnboardingAction.SelectedNativeLanguage(language))
    }

    fun onYearsSelected(years: String) {
        handleAction(OnboardingAction.SelectedYears(years))
    }

    fun onWordsSelected(words: Int) {
        handleAction(OnboardingAction.SelectedWordsCountPerDay(words))
    }

    fun onNotificationTimeSelected(time: NotificationTime) {
        handleAction(OnboardingAction.SelectedReminderTime(time))
    }

    fun onBackClicked(page: Int) = handleAction(OnboardingAction.OnBackSelected(page))
}