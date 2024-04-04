package com.usmonie.word.features.onboarding.ui.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import com.usmonie.word.features.dashboard.domain.usecase.SetOnboardingWasShowedUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.SetupLanguageLevelUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.SetupNativeLanguageUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.SetupRemindersTimeUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.SetupWordsPerDayUseCaseImpl
import org.jetbrains.compose.resources.ExperimentalResourceApi
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.word.core.domain.Analytics

@ExperimentalResourceApi
class OnboardingScreen internal constructor(
    private val onNextGraph: () -> Unit,
    private val onboardingViewModel: OnboardingViewModel
) : Screen(onboardingViewModel) {
    override val id: String = ID

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current
        val state by onboardingViewModel.state.collectAsState()
        val currentPage = remember(state) {
            when (state) {
                is OnboardingState.SelectNativeLanguage -> 0
                is OnboardingState.HowOldAreUser -> 1
                is OnboardingState.SelectLanguageLevel -> 2
                is OnboardingState.SelectCountWordsPerDay -> 3
                is OnboardingState.SelectReminderTime -> 4
            }
        }

        val initialPage = remember { currentPage }
        val pagerState = rememberPagerState(initialPage) { 5 }
        val effect by onboardingViewModel.effect.collectAsState(null)

        LaunchedEffect(state) {
            pagerState.animateScrollToPage(currentPage)
        }

        LaunchedEffect(effect) {
            when (val e = effect) {
                is OnboardingEffect.RequestNotificationPermission -> Unit
                is OnboardingEffect.NextGraph -> onNextGraph()
                null -> Unit
            }
        }

        HorizontalPager(pagerState, userScrollEnabled = false) { pageNumber ->
            when (pageNumber) {
                0 -> SelectNativeLanguagePage(
                    onboardingViewModel::onLanguageSelected,
                    routeManager::navigateBack
                )

                1 -> HowOldAreUserPage(
                    onboardingViewModel::onYearsSelected
                ) { onboardingViewModel.onBackClicked(1) }

                2 -> WhatAreUserLevelPage(onboardingViewModel::onLanguageLevel) {
                    onboardingViewModel.onBackClicked(2)
                }

                3 -> HowManyWordsPerDayPage(
                    onboardingViewModel::onWordsSelected
                ) { onboardingViewModel.onBackClicked(3) }

                4 -> ChooseReminderTimePage(
                    onboardingViewModel::onNotificationTimeSelected
                ) { onboardingViewModel.onBackClicked(4) }
            }
        }
    }

    companion object {
        const val ID = "ONBOARDING"
    }

    class Builder(
        private val onNextGraph: () -> Unit,
        private val userRepository: UserRepository,
        private val analytics: Analytics
    ) : ScreenBuilder {
        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return OnboardingScreen(
                onNextGraph,
                OnboardingViewModel(
                    SetupWordsPerDayUseCaseImpl(userRepository),
                    SetupNativeLanguageUseCaseImpl(userRepository),
                    SetupRemindersTimeUseCaseImpl(userRepository),
                    SetOnboardingWasShowedUseCaseImpl(userRepository),
                    SetupLanguageLevelUseCaseImpl(userRepository),
                    analytics
                )
            )
        }
    }
}
