package com.usmonie.word.features.games.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.usmonie.compass.core.LocalRouteManager
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.core.kit.composables.word.HeaderWordScaffold
import com.usmonie.word.features.ads.ui.LocalAdsManager
import com.usmonie.word.features.games.ui.GamesScreenFactory.Companion.ID
import com.usmonie.word.features.games.ui.enigma.EnigmaGameScreenFactory
import com.usmonie.word.features.games.ui.hangman.HangmanGameScreenFactory
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionPage
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionScreenState
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.games.ui.generated.resources.Res
import word.shared.feature.games.ui.generated.resources.games_enigma_title
import word.shared.feature.games.ui.generated.resources.games_hangman_title
import word.shared.feature.games.ui.generated.resources.games_title

class GamesScreen(private val subscriptionViewModel: SubscriptionViewModel) : Screen() {
    override val id: ScreenId = ID

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current
        val subscriptionAdState by subscriptionViewModel.state.collectAsState()
        val placeholder = stringResource(Res.string.games_title)

        HeaderWordScaffold(
            placeholder = { placeholder },
            header = if (subscriptionAdState is SubscriptionScreenState.Empty) {
                null
            } else {
                { SubscriptionPage(subscriptionViewModel) }
            },
            bottomAdBanner = {
                Box(
                    Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    val adMob = LocalAdsManager.current

                    adMob.Banner(
                        Modifier.fillMaxWidth()
                            .navigationBarsPadding()
                    )
                }
            },
            onBackClicked = routeManager::popBackstack,
            modifier = Modifier.fillMaxWidth()
        ) { insets ->
            Box(Modifier.fillMaxSize().padding(insets)) {
                Column(
                    Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GameMenuItem(
                        stringResource(Res.string.games_hangman_title),
                        Modifier.fillMaxWidth().clickable(
                            onClick = { routeManager.navigateTo(HangmanGameScreenFactory.ID) }
                        )
                    )

                    GameMenuItem(
                        stringResource(Res.string.games_enigma_title),
                        Modifier.fillMaxWidth().clickable(
                            onClick = { routeManager.navigateTo(EnigmaGameScreenFactory.ID) }
                        ),
                        badgeEnable = true
                    )
                }
            }
        }
    }
}

@Composable
fun GameMenuItem(
    title: String,
    modifier: Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    badgeEnable: Boolean = false
) {
    Box(Modifier.clip(MaterialTheme.shapes.large)) {
        Box(modifier) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = color,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
            )

            if (badgeEnable) {
                Badge(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterEnd)
                        .size(12.dp)
                )
            }
        }
    }
}
