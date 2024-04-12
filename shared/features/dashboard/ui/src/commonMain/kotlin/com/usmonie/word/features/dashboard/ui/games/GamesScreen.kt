package com.usmonie.word.features.dashboard.ui.games

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.ui.dashboard.DashboardMenuItem
import com.usmonie.word.features.dashboard.ui.games.enigma.EnigmaGameScreen
import com.usmonie.word.features.dashboard.ui.games.hangman.HangmanGameScreen
import com.usmonie.word.features.dashboard.ui.ui.AdMob
import com.usmonie.word.features.dashboard.ui.ui.WordTopBar
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder

private val fillMaxWidthModifier = Modifier.fillMaxWidth()

class GamesScreen(private val adMob: AdMob) : Screen(null) {
    override val id: String = ID

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current
        val appBarState = remember { TopAppBarState(0f, 0f, 0f) }
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appBarState)

        Scaffold(
            topBar = {
                WordTopBar(
                    onBackClick = routeManager::navigateBack,
                    placeholder = "[G]ames",
                    showNavigationBack = remember { { true } },
                    getScrollBehavior = { scrollBehavior }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { insets ->

            Box(Modifier.fillMaxSize().padding(insets)) {
                Column(
                    Modifier.padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DashboardMenuItem(
                        "[H]angman",
                        // stringResource(Res.string.games_hangman_game),
                        fillMaxWidthModifier.clickable(
                            onClick = { routeManager.navigateTo(HangmanGameScreen.ID) }
                        )
                    )

                    DashboardMenuItem(
                        "[E]nigma",
                        // stringResource(Res.string.games_cryptogramma_game),
                        fillMaxWidthModifier.clickable(
                            onClick = { routeManager.navigateTo(EnigmaGameScreen.ID) }
                        ),
                        badgeEnable = true
                    )
                }

                adMob.Banner(Modifier.fillMaxWidth().align(Alignment.BottomCenter))
            }
        }
    }

    companion object {
        const val ID = "GAMES_SCREEN"
    }

    class Builder(private val adMob: AdMob) : ScreenBuilder {

        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return GamesScreen(adMob)
        }
    }
}