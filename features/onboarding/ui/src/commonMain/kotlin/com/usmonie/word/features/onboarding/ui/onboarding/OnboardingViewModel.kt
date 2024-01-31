package com.usmonie.word.features.onboarding.ui.onboarding

import androidx.compose.runtime.Immutable
import com.usmonie.word.features.dashboard.domain.models.Language
import com.usmonie.word.features.dashboard.domain.models.NotificationTime
import com.usmonie.word.features.dashboard.domain.usecase.SetOnboardingWasShowedUseCase
import com.usmonie.word.features.dashboard.domain.usecase.SetupNativeLanguageUseCase
import com.usmonie.word.features.dashboard.domain.usecase.SetupRemindersTimeUseCase
import com.usmonie.word.features.dashboard.domain.usecase.SetupWordsPerDayUseCase
import wtf.speech.core.ui.BaseViewModel

@Immutable
internal class OnboardingViewModel(
    private val setupWordsPerDayUseCase: SetupWordsPerDayUseCase,
    private val setupNativeLanguageUseCase: SetupNativeLanguageUseCase,
    private val setupRemindersTimeUseCase: SetupRemindersTimeUseCase,
    private val setupOnboardingWasShowedUseCase: SetOnboardingWasShowedUseCase
) : BaseViewModel<OnboardingState, OnboardingAction, OnboardingEvent, OnboardingEffect>(
    OnboardingState.SelectNativeLanguage
) {
    override fun OnboardingState.reduce(event: OnboardingEvent) = when (event) {
        OnboardingEvent.SelectNativeLanguage -> OnboardingState.SelectNativeLanguage
        OnboardingEvent.SelectCountWordsPerDay -> OnboardingState.SelectCountWordsPerDay
        OnboardingEvent.SelectReminderTime -> OnboardingState.SelectReminderTime
        OnboardingEvent.SelectedReminderTime -> this
    }

    override suspend fun processAction(action: OnboardingAction): OnboardingEvent = when (action) {
        is OnboardingAction.SelectedNativeLanguage -> {
            setupNativeLanguageUseCase(action.language)
            OnboardingEvent.SelectCountWordsPerDay
        }

        is OnboardingAction.SelectedReminderTime -> {
            setupRemindersTimeUseCase(action.notificationTime)
            OnboardingEvent.SelectedReminderTime
        }

        is OnboardingAction.SelectedWordsCountPerDay -> {
            setupWordsPerDayUseCase(action.count)
            setupOnboardingWasShowedUseCase(Unit)
            OnboardingEvent.SelectReminderTime
        }

        is OnboardingAction.OnBackSelected -> when (action.page) {
            1 -> OnboardingEvent.SelectNativeLanguage
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

    fun onWordsSelected(words: Int) {
        handleAction(OnboardingAction.SelectedWordsCountPerDay(words))
    }

    fun onNotificationTimeSelected(time: NotificationTime) {
        handleAction(OnboardingAction.SelectedReminderTime(time))
    }

    fun onBackClicked(page: Int) = handleAction(OnboardingAction.OnBackSelected(page))
}