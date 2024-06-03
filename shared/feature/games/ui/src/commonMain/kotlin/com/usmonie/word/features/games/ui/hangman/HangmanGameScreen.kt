package com.usmonie.word.features.games.ui.hangman

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.compass.core.LocalRouteManager
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.compass.viewmodel.StateScreen
import com.usmonie.core.domain.tools.fastForEach
import com.usmonie.word.features.ads.ui.LocalAdMob
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import com.usmonie.word.features.games.ui.hangman.HangmanGameScreenFactory.Companion.ID
import com.usmonie.word.features.games.ui.kit.GameBoard
import com.usmonie.word.features.games.ui.kit.HangmanGameWon
import com.usmonie.word.features.games.ui.kit.LivesAmount
import com.usmonie.word.features.games.ui.kit.ReviveLifeDialog
import com.usmonie.word.features.games.ui.kit.UseHintButton
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionScreenAction
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.games.ui.generated.resources.Res
import word.shared.feature.games.ui.generated.resources.games_hide_hint
import word.shared.feature.games.ui.generated.resources.games_next_word
import word.shared.feature.games.ui.generated.resources.games_show_details
import word.shared.feature.games.ui.generated.resources.games_show_hint
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
internal class HangmanGameScreen(
    viewModel: HangmanGameViewModel,
    private val onOpenWord: (WordCombinedUi) -> Unit,
    private val subscriptionViewModel: SubscriptionViewModel
) : StateScreen<HangmanState, HangmanAction, HangmanEvent, HangmanEffect, HangmanGameViewModel>(
    viewModel
) {
    override val id: ScreenId = ID

    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current

        val state by viewModel.state.collectAsState()
        val effect by viewModel.effect.collectAsState(null)

        HangmanEffect(onOpenWord, effect)
        DisposableEffect(Unit) {
            subscriptionViewModel.handleAction(SubscriptionScreenAction.Minify)
            onDispose {
                subscriptionViewModel.handleAction(SubscriptionScreenAction.Collapse)
            }
        }

        GameBoard(
            routeManager::popBackstack,
            subscriptionViewModel,
            actions = {
                if (state !is HangmanState.Loading && state !is HangmanState.Error) {
                    val adMob = LocalAdMob.current

                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                    ) {
                        val isHintReady by remember(effect, state.hintsCount) {
                            derivedStateOf {
                                state.hintsCount > 0 || adMob.getAdMobState().isRewardHintReady
                            }
                        }
                        LivesAmount(state.lives, state.maxLives, Modifier.align(Alignment.Center))
                        if (isHintReady) {
                            UseHintButton(viewModel::useHint, state.hintsCount)
                        }
                    }
                }
            }
        ) { insets ->
            val adMob = LocalAdMob.current
            AnimatedContent(state is HangmanState.Loading) { isLoading ->
                if (isLoading) {
                    LoadingProgress(insets)
                } else {
                    PlayBoard(state, viewModel, insets)
                }
            }

            AnimatedVisibility(state is HangmanState.Won) {
                HangmanGameWon(
                    routeManager::popBackstack,
                    viewModel::onUpdatePressed,
                    stringResource(Res.string.games_next_word),
                    state.word
                )
            }

            AnimatedVisibility(state is HangmanState.Lost) {
                ReviveLifeDialog(
                    routeManager::popBackstack,
                    viewModel::onReviveClick,
                    viewModel::onUpdatePressed,
                    isReviveAvailable = { adMob.getAdMobState().isRewardLifeReady },
                    nextTitle = stringResource(Res.string.games_next_word)
                )
            }

            AnimatedVisibility(
                effect is HangmanEffect.ShowRewardedLifeAd,
            ) { adMob.RewardedLifeInterstitial({}, viewModel::onRewardLifeGranted) }

            AnimatedVisibility(
                effect is HangmanEffect.ShowRewardedHintAd,
            ) { adMob.RewardedHintInterstitial({}, viewModel::onRewardHintGranted) }

            AnimatedVisibility(
                effect is HangmanEffect.StartGame ||
                        effect is HangmanEffect.RestartGame ||
                        effect is HangmanEffect.ShowInterstitialAd
            ) { adMob.Interstitial() }
        }
    }
}

@Composable
private fun LoadingProgress(insets: PaddingValues) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(insets),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun PlayBoard(
    state: HangmanState,
    hangmanGameViewModel: HangmanGameViewModel,
    insets: PaddingValues,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = insets.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HangmanImage(
            state.maxLives - state.lives,
            Modifier.fillMaxWidth().weight(1f).padding(vertical = 24.dp)
        )
        WordDisplay(state)

        Spacer(Modifier.height(8.dp))
        when (state) {
            is HangmanState.Playing.Information -> HintButton(
                hangmanGameViewModel::onShowHintPressed,
                stringResource(Res.string.games_hide_hint)
            )

            is HangmanState.Playing.Input -> HintButton(
                hangmanGameViewModel::onShowHintPressed,
                stringResource(Res.string.games_show_hint)
            )

            else -> HintButton(
                { hangmanGameViewModel.onOpenWordPressed(state.word) },
                stringResource(Res.string.games_show_details)
            )
        }
        Spacer(Modifier.height(8.dp))

        AnimatedContent(
            state,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentKey = { hangmanState -> hangmanState::class::simpleName }
        ) { hangmanState ->
            if (hangmanState is HangmanState.Playing.Input) {
                Keyboard(
                    hangmanGameViewModel::onLetterGuessed,
                    hangmanState.guessedLetters,
                    Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
                )
            } else {
                Column(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.height(32.dp))
                    val text = remember(hangmanState.word.wordEtymology.first()) {
                        hangmanState.word.wordEtymology.first().words.first().senses.first().gloss
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth().padding(24.dp),
                        text = text,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun HintButton(
    onClick: () -> Unit,
    title: String,
) {
    TextButton(
        onClick,
        Modifier.fillMaxWidth().padding(horizontal = 20.dp)
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun HangmanEffect(onOpenWord: (WordCombinedUi) -> Unit, effect: HangmanEffect?) {
    LaunchedEffect(effect) {
        when (effect) {
            is HangmanEffect.OpenWord -> onOpenWord(effect.word)

            else -> Unit
        }
    }
}

@Composable
fun WordDisplay(gameState: HangmanState, modifier: Modifier = Modifier) {
    val displayWord = remember(gameState.guessedLetters.letters.size) {
        gameState.word
            .word
            .asSequence()
            .map { letter ->
                if (letter.lowercaseChar() in gameState.guessedLetters.letters || !letter.isLetterOrDigit()) {
                    letter.uppercaseChar()
                } else {
                    '_'
                }
            }
            .joinToString(" ")
    }

    Text(
        displayWord,
        modifier,
        color = MaterialTheme.colorScheme.onBackground,
        style = when (gameState) {
            is HangmanState.Won -> MaterialTheme.typography.displayMedium
            else -> MaterialTheme.typography.displaySmall
        },
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Keyboard(onLetterClick: (Char) -> Unit, guessedLetters: GuessedLetters, modifier: Modifier) {
    FlowRow(
        modifier.padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        val density = LocalDensity.current
        val size by remember(density) { derivedStateOf { 128.dp / density.density } }
        val letterModifier = Modifier.size(size)

        alphabet.fastForEach { letter ->
            val wasGuessed = remember(
                guessedLetters,
                letter
            ) { letter.lowercaseChar() in guessedLetters.letters }
            KeyboardButton(onLetterClick, letter, letterModifier, wasGuessed)
        }
    }
}

@Composable
private fun KeyboardButton(
    onLetterClick: (Char) -> Unit,
    letter: Char,
    modifier: Modifier,
    wasGuessed: Boolean
) {
    val hapticFeedback = LocalHapticFeedback.current
    FilledTonalButton(
        onClick = {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onLetterClick(letter)
        },
        enabled = !wasGuessed,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
    ) {
        Text(
            letter.toString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.primary)
        )
    }
}
