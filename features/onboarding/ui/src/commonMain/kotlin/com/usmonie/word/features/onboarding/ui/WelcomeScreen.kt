package com.usmonie.word.features.onboarding.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.onboarding.ui.onboarding.OnboardingScreen
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder

class WelcomeScreen : Screen() {
    override val id: String = ID

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current
        var showMemorable by remember {
            mutableStateOf(true)
        }
        LaunchedEffect(showMemorable) {
            delay(2000L)
            showMemorable = false
        }
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AnimatedContent(
                showMemorable,
                transitionSpec = {
                    fadeIn(
                        animationSpec = tween(
                            220,
                            delayMillis = 90
                        )
                    ) togetherWith fadeOut(animationSpec = tween(90))
                }
            ) { show ->
                if (show) {
                    Text(
                        "In memory of\n Alina Isaeva",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painterResource("drawable/ic_app_icon.png"),
                            modifier = Modifier.size(128.dp),

                            contentDescription = null
                        )
                        Spacer(Modifier.height(32.dp))

                        Text(
                            "Welcome to English",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.displayMedium,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(32.dp))
                        Text(
                            "Expand your vocabulary, learn new words,\n" +
                                    "and improve your English skills.",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            AnimatedVisibility(
                !showMemorable,
                enter = fadeIn(
                    animationSpec = tween(
                        220,
                        delayMillis = 90
                    )
                ),
                exit = fadeOut(animationSpec = tween(90)),
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(24.dp)
                    .padding(bottom = 48.dp)
            ) {
                Button(
                    { routeManager.navigateTo(OnboardingScreen.ID) },
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    Text("Continue", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }

    companion object : ScreenBuilder {
        const val ID = "welcome"
        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return WelcomeScreen()
        }
    }
}