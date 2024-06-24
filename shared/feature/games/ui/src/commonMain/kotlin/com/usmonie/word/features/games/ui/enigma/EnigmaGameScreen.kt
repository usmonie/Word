package com.usmonie.word.features.games.ui.enigma

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.compass.core.LocalRouteManager
import com.usmonie.compass.viewmodel.StateScreen
import com.usmonie.core.domain.tools.fastForEachIndexed
import com.usmonie.core.tools.ui.ShakeConfig
import com.usmonie.core.tools.ui.ShakeController
import com.usmonie.core.tools.ui.rememberShakeController
import com.usmonie.core.tools.ui.shake
import com.usmonie.word.features.ads.ui.LocalAdsManager
import com.usmonie.word.features.games.ui.enigma.EnigmaGameScreenFactory.Companion.ID
import com.usmonie.word.features.games.ui.hangman.GuessedLetters
import com.usmonie.word.features.games.ui.kit.EnigmaGameWon
import com.usmonie.word.features.games.ui.kit.GameBoard
import com.usmonie.word.features.games.ui.kit.Keyboard
import com.usmonie.word.features.games.ui.kit.LivesAmount
import com.usmonie.word.features.games.ui.kit.ReviveLifeDialog
import com.usmonie.word.features.games.ui.kit.UseHintButton
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionScreenAction
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.games.ui.generated.resources.Res
import word.shared.feature.games.ui.generated.resources.games_next_phrase

internal class EnigmaGameScreen(
    viewModel: EnigmaGameViewModel,
    private val subscriptionViewModel: SubscriptionViewModel
) : StateScreen<EnigmaState, EnigmaAction, EnigmaEvent, EnigmaEffect, EnigmaGameViewModel>(viewModel) {

    override val id = ID

    @Composable
    override fun Content() {
        val state by viewModel.state.collectAsState()

        DisposableEffect(Unit) {
            subscriptionViewModel.handleAction(SubscriptionScreenAction.Minify)
            onDispose {
                subscriptionViewModel.handleAction(SubscriptionScreenAction.Collapse)
            }
        }

        val shakeController = rememberShakeController()
        EnigmaGameEffect(shakeController, viewModel)
        EnigmaGameBoard(
            { state.lives },
            { state.maxLives },
            { state.hintsCount },
            subscriptionViewModel
        ) { insets ->
            EnigmaGameContent(
                { state.phrase },
                { state.currentSelectedCellPosition },
                { cellState ->
                    state is EnigmaState.Game &&
                            cellState != CellState.Correct &&
                            cellState != CellState.Found
                },
                { state is EnigmaState.Game.HintSelection },
                { state.guessedLetters },
                shakeController,
                insets
            )
        }
    }

    @Composable
    private fun EnigmaGameEffect(shakeController: ShakeController, viewModel: EnigmaGameViewModel) {
        val effect by viewModel.effect.collectAsState(null)

        val hapticFeedback = LocalHapticFeedback.current
        LaunchedEffect(effect) {
            when (effect) {
                is EnigmaEffect.InputEffect.Incorrect -> {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    shakeController.shake(ShakeConfig(iterations = 3, translateX = 10f))
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                }

                else -> Unit
            }
        }
    }

    @Composable
    private fun EnigmaGameContent(
        getPhrase: () -> EnigmaEncryptedPhrase,
        getCurrentSelectedPosition: () -> Pair<Int, Int>?,
        isEnabled: (CellState) -> Boolean,
        hintSelectionState: () -> Boolean,
        guessedLetters: () -> GuessedLetters,
        shakeController: ShakeController,
        insets: PaddingValues
    ) {
        GameStatusState()
        val topInsets = insets.calculateTopPadding()

        Column(
            Modifier
                .fillMaxSize()
                .padding(top = topInsets)
        ) {
            Column(
                Modifier
                    .shake(shakeController)
                    .weight(1f)
            ) {
                Phrase(
                    getPhrase(),
                    getCurrentSelectedPosition,
                    isEnabled,
                    hintSelectionState,
                    Modifier
                        .weight(1.5f)
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp)
                )

                Keyboard(
                    viewModel::onLetterInput,
                    guessedLetters(),
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }
    }

    @Composable
    private fun GameStatusState() {
        val routerManager = LocalRouteManager.current
        val effect by viewModel.effect.collectAsState(null)
        val state by viewModel.state.collectAsState()

        val adMob = LocalAdsManager.current
        AnimatedVisibility(
            state is EnigmaState.Loading || effect is EnigmaEffect.ShowMiddleGameAd,
            content = { adMob.Interstitial() }
        )

        AnimatedVisibility(effect is EnigmaEffect.ShowRewardedLifeAd) {
            adMob.RewardedLifeInterstitial(routerManager::popBackstack, viewModel::onReviveGranted)
        }

        AnimatedVisibility(effect is EnigmaEffect.ShowRewardedHintAd) {
            adMob.RewardedHintInterstitial(routerManager::popBackstack, viewModel::onHintGranted)
        }

        AnimatedVisibility(state is EnigmaState.Lost) {
            ReviveLifeDialog(
                routerManager::popBackstack,
                viewModel::onAddLifeClick,
                viewModel::onNextPhrase,
                isReviveAvailable = { adMob.getAdMobState().isRewardLifeReady },
                nextTitle = stringResource(Res.string.games_next_phrase)
            )
        }

        AnimatedVisibility(state is EnigmaState.Won) {
            EnigmaGameWon(
                routerManager::popBackstack,
                viewModel::onNextPhrase,
                nextTitle = stringResource(Res.string.games_next_phrase),
                quote = state.phrase.quote,
                onFavoriteQuote = {}
            )
        }
    }

    @Composable
    fun EnigmaGameBoard(
        getLives: () -> Int,
        getMaxLives: () -> Int,
        getHintsCount: () -> Int,
        subscriptionViewModel: SubscriptionViewModel,
        content: @Composable (insets: PaddingValues) -> Unit
    ) {
        val routerManager = LocalRouteManager.current

        GameBoard(
            routerManager::popBackstack,
            actions = {
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                ) {
                    val adMob = LocalAdsManager.current
                    LivesAmount(getLives(), getMaxLives(), Modifier.align(Alignment.Center))
                    if (getHintsCount() > 0 || adMob.getAdMobState().isRewardHintReady) {
                        UseHintButton(viewModel::useHint, getHintsCount())
                    }
                }
            },
            subscriptionViewModel = subscriptionViewModel,
            content = content,
        )
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun Phrase(
        phrase: EnigmaEncryptedPhrase,
        getCurrentSelectedPosition: () -> Pair<Int, Int>?,
        isEnabled: (CellState) -> Boolean,
        hintSelectionState: () -> Boolean,
        modifier: Modifier
    ) {
        val scrollState = rememberScrollState()
        FlowRow(
            modifier.verticalScroll(scrollState).padding(vertical = 52.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            phrase.encryptedPhrase.fastForEachIndexed { index, word ->
                key(index) {
                    Word(word, getCurrentSelectedPosition, isEnabled, hintSelectionState, index)
                }
            }
        }
    }

    @Composable
    private fun Word(
        word: Word,
        getCurrentSelectedPosition: () -> Pair<Int, Int>?,
        isEnabled: (CellState) -> Boolean,
        hintSelectionState: () -> Boolean,
        wordPosition: Int
    ) {
        Row(
//            horizontalArrangement = Arrangement.,
        ) {
            word.cells.fastForEachIndexed { position, cell ->
                key(cell.symbol, position) {
                    CellItem(
                        cell,
                        getCurrentSelectedPosition,
                        isEnabled,
                        hintSelectionState,
                        wordPosition,
                        position
                    )
                }
            }
        }
    }

    @Composable
    private fun RowScope.CellItem(
        cell: Cell,
        getCurrentSelectedPosition: () -> Pair<Int, Int>?,
        isEnabled: (CellState) -> Boolean,
        hintSelectionState: () -> Boolean,
        wordPosition: Int,
        position: Int
    ) {
        when {
            cell.isLetter -> GuessedLetter(
                cell,
                {
                    val (word, cellPosition) = getCurrentSelectedPosition()
                        ?: return@GuessedLetter false

                    word == wordPosition && cellPosition == position
                },
                enabled = isEnabled,
                hintSelectionState = hintSelectionState,
                onClick = { viewModel.onCellSelected(position, wordPosition) }
            )

            else -> Symbol(cell.symbol, Modifier.fillMaxHeight().alignByBaseline())
        }
    }

    @Composable
    private fun RowScope.GuessedLetter(
        cell: Cell,
        getIsSelected: () -> Boolean,
        hintSelectionState: () -> Boolean,
        onClick: () -> Unit,
        enabled: (CellState) -> Boolean
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        val hintSelection = hintSelectionState()
        val isSelected = getIsSelected()
        Letter(
            cell.backgroundColor(hintSelection, isSelected),
            { enabled(cell.state) },
            hintSelectionState = hintSelectionState,
            cell.letter,
            cell.textColor(hintSelection, isSelected),
            if (cell.state != CellState.Found) cell.number.toString() else "",
            interactionSource,
            onClick,
            Modifier.alignByBaseline().graphicsLayer {
                shadowElevation = if (isSelected) 8.dp.toPx() else 0f
            }
        )
    }

    @Composable
    fun Letter(
        backgroundColor: Color,
        enabled: () -> Boolean,
        hintSelectionState: () -> Boolean,
        letter: String,
        letterColor: Color,
        cellNumber: String,
        interactionSource: MutableInteractionSource,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val shape = MaterialTheme.shapes.small
        Column(
            modifier = modifier
                .drawBehind {
                    drawRoundRect(
                        Color(backgroundColor.value),
                        cornerRadius = CornerRadius(6.dp.toPx()),
                        style = if (hintSelectionState()) Stroke(2.dp.toPx()) else Fill
                    )
                }
                .clip(shape)
                .clickable(
                    indication = LocalIndication.current,
                    interactionSource = interactionSource,
                    enabled = enabled(),
                    onClick = onClick,
                )
                .width(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            val pressed by interactionSource.collectIsPressedAsState()
            val dividerScale by animateFloatAsState(if (pressed) 0.7f else 1f)

            Text(
                text = letter,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(top = 4.dp),
                color = letterColor,
                textAlign = TextAlign.Center
            )

            HorizontalDivider(
                Modifier
                    .graphicsLayer { scaleX = dividerScale }
                    .padding(
                        horizontal = 2.dp,
                        vertical = 2.dp
                    ),
                color = letterColor
            )

            Text(
                text = cellNumber,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(bottom = 4.dp),
                textAlign = TextAlign.Center,
                color = letterColor,
            )
        }
    }

    @Composable
    private fun Symbol(symbol: Char, modifier: Modifier) {
        Column(
            modifier = modifier.width(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = symbol.toString(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}
