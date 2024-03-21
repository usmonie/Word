package com.usmonie.word.features.onboarding.ui.welcome

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import word.features.onboarding.ui.generated.resources.Res
import word.features.onboarding.ui.generated.resources.in_memory_of_alina
import word.features.onboarding.ui.generated.resources.sign_in_with_google
import word.features.onboarding.ui.generated.resources.welcome_description
import word.features.onboarding.ui.generated.resources.welcome_title
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.word.core.design.themes.Resources

class WelcomeScreen(
    private val welcomeViewModel: WelcomeViewModel
) : Screen(welcomeViewModel, false) {
    override val id: String = ID

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val state by welcomeViewModel.state.collectAsState()
        val routeManager = LocalRouteManager.current

        LaunchedEffect(Unit) {
//            if (Firebase.auth.currentUser != null) routeManager.navigateTo(OnboardingScreen.ID)
        }

        var showMemorable by remember {
            mutableStateOf(!state.wasShowedForAlina)
        }
        LaunchedEffect(showMemorable) {
            delay(2000L)
            showMemorable = false
            welcomeViewModel.handleAction(WelcomeAction.ForAlinaShowed)
        }
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AnimatedContent(
                showMemorable,
                transitionSpec = {
                    fadeIn(
                        animationSpec = tween(220, delayMillis = 90)
                    ) togetherWith fadeOut(animationSpec = tween(90))
                }
            ) { show ->
                if (show) {
                    Text(
                        stringResource(Res.string.in_memory_of_alina),
                        style = MaterialTheme.typography.headlineMedium,
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
                            Resources.ic_rocket_launch_outline,
                            modifier = Modifier.size(128.dp),
                            contentDescription = null
                        )

                        Spacer(Modifier.height(32.dp))

                        Text(
                            stringResource(Res.string.welcome_title),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.displayMedium,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(32.dp))
                        Text(
                            stringResource(Res.string.welcome_description),
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
                    .padding(bottom = 52.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

//                    GoogleButtonUiContainer(onGoogleSignInResult = {
//                        welcomeViewModel.handleAction(WelcomeAction.SignInWithGoogle(it))
//                    }) {
                        OutlinedButton(
                            {  },
                            modifier = Modifier.fillMaxWidth().height(56.dp)
                        ) {
                            Icon(
                                Resources.ic_rocket_launch_outline,
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(24.dp).padding(end = 8.dp)
                            )

                            Text(
                                stringResource(Res.string.sign_in_with_google),
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
//                    }

//                    AppleButtonUiContainer(onResult = {
//                        println(it.toString())
//                    }) {
//                        Button(
//                            this::onClick,
//                            modifier = Modifier.fillMaxWidth().height(56.dp),
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = Color.Black,
//                                contentColor = Color.White
//                            )
//                        ) {
//                            Icon(
//                                painterResource(Resources.ic_apple_logo),
//                                contentDescription = null,
//                                modifier = Modifier.size(24.dp).padding(end = 8.dp)
//                            )
//
//                            Text(
//                                stringResource(Res.string.sign_in_with_apple),
//                                style = MaterialTheme.typography.titleMedium
//                            )
//                        }
//                    }
                }
            }
        }
    }

    companion object {
        const val ID = "welcome"
    }

    class Builder : ScreenBuilder {

        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return WelcomeScreen(WelcomeViewModel())
        }
    }
}