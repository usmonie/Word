package com.usmonie.word.features.onboarding.ui

import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import com.usmonie.word.features.onboarding.ui.onboarding.OnboardingScreen
import wtf.speech.compass.core.NavigationGraph
import wtf.speech.compass.core.Route
import wtf.word.core.domain.Analytics

fun getWelcomeGraph(
    nextGraph: () -> Unit,
    userRepository: UserRepository,
    analytics: Analytics
): NavigationGraph? {
    if (userRepository.wasOnboardingShowed) return null
    val welcomeScreenBuilder = WelcomeScreen

    val onboardingScreenBuilder = OnboardingScreen.Builder(nextGraph, userRepository, analytics)
    return NavigationGraph(WELCOME_GRAPH_ID, welcomeScreenBuilder).apply {
        register(Route(OnboardingScreen.ID, onboardingScreenBuilder))
    }
}

const val WELCOME_GRAPH_ID = "WELCOME_GRAPH"