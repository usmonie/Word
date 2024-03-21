@file:OptIn(ExperimentalResourceApi::class)

package com.usmonie.word.features.onboarding.ui

import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import com.usmonie.word.features.onboarding.ui.onboarding.OnboardingScreen
import com.usmonie.word.features.onboarding.ui.welcome.WelcomeScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import wtf.speech.compass.core.NavigationGraph
import wtf.speech.compass.core.Route
import wtf.word.core.domain.Analytics

fun getWelcomeGraph(
    nextGraph: () -> Unit,
    userRepository: UserRepository,
    analytics: Analytics
): NavigationGraph? {
    return null
    if (userRepository.wasOnboardingShowed) return null
    val welcomeScreenBuilder = WelcomeScreen.Builder()

    val onboardingScreenBuilder = OnboardingScreen.Builder(nextGraph, userRepository, analytics)
    return NavigationGraph(
        WELCOME_GRAPH_ID,
        Route(WelcomeScreen.ID, welcomeScreenBuilder),
        storeInBackStack = false
    ).apply {
        register(Route(OnboardingScreen.ID, onboardingScreenBuilder))
    }
}

const val WELCOME_GRAPH_ID = "WELCOME_GRAPH"