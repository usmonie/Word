package com.usmonie.word.features.dashboard.ui.games

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.ui.dashboard.DashboardMenuItem
import com.usmonie.word.features.dashboard.ui.games.enigma.EnigmaGameScreen
import com.usmonie.word.features.dashboard.ui.games.hangman.HangmanGameScreen
import com.usmonie.word.features.dashboard.ui.ui.WordTopBar
import org.jetbrains.compose.resources.ExperimentalResourceApi
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder


private val fillMaxWidthModifier = Modifier.fillMaxWidth()
private val fillMaxWidthModifierHorizontalPadding =
    fillMaxWidthModifier.padding(horizontal = 24.dp)

class GamesScreen : Screen(null) {
    override val id: String = ID

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current
        val appBarState = remember { TopAppBarState(0f, 0f, 0f) }
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appBarState)

        Scaffold(
            topBar = {
                WordTopBar(
                    routeManager::navigateBack,
                    "[G]ames",
                    remember { { true } }
                ) { scrollBehavior }
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { insets ->
            Column(Modifier.padding(insets)) {
                DashboardMenuItem(
                    "stringResource(Res.string.games_hangman_game)",
                    fillMaxWidthModifierHorizontalPadding.clickable(
                        onClick = { routeManager.navigateTo(HangmanGameScreen.ID) }
                    )
                )
                DashboardMenuItem(
                    "stringResource(Res.string.games_cryptogramma_game)",
                    fillMaxWidthModifierHorizontalPadding.clickable(
                        onClick = { routeManager.navigateTo(EnigmaGameScreen.ID) }
                    )
                )
            }
        }
    }

    companion object : ScreenBuilder {
        const val ID = "GAMES_SCREEN"
        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return GamesScreen()
        }
    }
}