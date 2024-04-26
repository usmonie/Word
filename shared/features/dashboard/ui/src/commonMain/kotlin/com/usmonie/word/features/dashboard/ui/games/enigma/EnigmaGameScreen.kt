package com.usmonie.word.features.dashboard.ui.games.enigma

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.AddUserHintsCountUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.GetNextPhraseUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.GetUserHintsCountUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.UseUserHintsCountUseCaseImpl
import com.usmonie.word.features.dashboard.ui.games.EnigmaGameWon
import com.usmonie.word.features.dashboard.ui.games.GameBoard
import com.usmonie.word.features.dashboard.ui.games.HintButton
import com.usmonie.word.features.dashboard.ui.games.LivesAmount
import com.usmonie.word.features.dashboard.ui.games.ReviveLifeDialog
import com.usmonie.word.features.dashboard.ui.games.hangman.GuessedLetters
import com.usmonie.word.features.dashboard.ui.games.hangman.HangmanGameScreen
import com.usmonie.word.features.dashboard.ui.games.hangman.Keyboard
import com.usmonie.word.features.dashboard.ui.ui.AdMob
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.speech.core.ui.ShakeConfig
import wtf.speech.core.ui.ShakeController
import wtf.speech.core.ui.rememberShakeController
import wtf.speech.core.ui.shake
import wtf.word.core.domain.Analytics
import wtf.word.core.domain.tools.fastForEachIndexed

class EnigmaGameScreen(
    private val adMob: AdMob,
    private val enigmaViewModel: EnigmaGameViewModel
) : Screen() {

    override val id: String = ID

    @Composable
    override fun Content() {
        val state by enigmaViewModel.state.collectAsState()

        EnigmaGameBoard(
            remember { { state.lives } },
            remember { { state.maxLives } },
            remember { { state.hintsCount } },
            modifier = Modifier.fillMaxSize()
        ) { insets ->
            val shakeController = rememberShakeController()

            EnigmaGameContent(
                remember { { state.phrase } },
                remember {
                    { state.currentSelectedCellPosition }
                },
                remember {
                    { cellState ->
                        state is EnigmaState.Game
                                && cellState != CellState.Correct
                                && cellState != CellState.Found
                    }
                },
                {
                    state is EnigmaState.Game.HintSelection
                },
                remember {
                    { state.guessedLetters }
                },
                shakeController,
                insets
            )
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

        EnigmaEffectListener(enigmaViewModel, shakeController)

        Column(
            Modifier
                .fillMaxSize()
                .padding(insets)
                .shake(shakeController)
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
                enigmaViewModel::onLetterInput,
                guessedLetters(),
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            )

            adMob.Banner(Modifier.fillMaxWidth().height(54.dp))
        }
    }

    @Composable
    private fun GameStatusState() {
        val routerManager = LocalRouteManager.current
        val effect by enigmaViewModel.effect.collectAsState(null)
        val state by enigmaViewModel.state.collectAsState()

        AnimatedVisibility(
            state is EnigmaState.Loading || effect is EnigmaEffect.ShowMiddleGameAd
        ) {
            adMob.Interstitial()
        }

        AnimatedVisibility(effect is EnigmaEffect.ShowRewardedAd) {
            adMob.RewardedInterstitial({}, enigmaViewModel::onReviveGranted)
        }

        AnimatedVisibility(state is EnigmaState.Lost) {
            ReviveLifeDialog(
                routerManager::navigateBack,
                enigmaViewModel::onAddLifeClick,
                enigmaViewModel::onNextPhrase,
                isReviveAvailable = { adMob.adMobState.isRewardReady },
                nextTitle = "Next phrase"
            )
        }

        AnimatedVisibility(state is EnigmaState.Won) {
            EnigmaGameWon(
                routerManager::navigateBack,
                enigmaViewModel::onNextPhrase,
                nextTitle = "Next phrase",
                state.phrase.phrase,
                state.phrase.author
            )
        }
    }

    @Composable
    fun EnigmaGameBoard(
        getLives: () -> Int,
        getMaxLives: () -> Int,
        getHintsCount: () -> Int,
        modifier: Modifier,
        content: @Composable (insets: PaddingValues) -> Unit
    ) {
        val routerManager = LocalRouteManager.current

        GameBoard(
            routerManager::navigateBack,
            actions = {
                Spacer(Modifier.width(24.dp))
                LivesAmount(getLives(), getMaxLives(), Modifier.weight(1f))
                HintButton(enigmaViewModel::useHint, getHintsCount())
                Spacer(Modifier.width(24.dp))
            },
            content = content,
            modifier = modifier,
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            key(phrase.phrase) {
                phrase.encryptedPhrase.fastForEachIndexed { index, word ->
                    key(index) {
                        Word(word, getCurrentSelectedPosition, isEnabled, hintSelectionState, index)
                    }
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
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
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
                onClick = { enigmaViewModel.onCellSelected(position, wordPosition) }
            )

            else -> Symbol(cell.symbol, Modifier.fillMaxHeight())
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
        Column(
            modifier = modifier
                .drawBehind {
                    drawRoundRect(
                        Color(backgroundColor.value),
                        cornerRadius = CornerRadius(6.dp.toPx()),
                        style = if (hintSelectionState()) Stroke(2.dp.toPx()) else Fill
                    )
                }
                .clip(RoundedCornerShape(6.dp))
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
            val dividerHorizontalPadding by animateFloatAsState(if (pressed) 0.7f else 1f)

            Text(
                text = letter,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(top = 4.dp),
                color = letterColor,
                textAlign = TextAlign.Center
            )

            HorizontalDivider(
                Modifier
                    .graphicsLayer { scaleX = dividerHorizontalPadding }
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
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(text = symbol.toString(), style = MaterialTheme.typography.titleMedium)
        }
    }

    companion object {
        const val ID = "ENIGMA_SCREEN"
    }

    class Builder(
        private val analytics: Analytics,
        private val wordRepository: WordRepository,
        private val userRepository: UserRepository,
        private val adMob: AdMob
    ) : ScreenBuilder {
        override val id: String = HangmanGameScreen.ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return EnigmaGameScreen(
                adMob,
                EnigmaGameViewModel(
                    analytics,
                    GetNextPhraseUseCaseImpl(),
                    GetUserHintsCountUseCaseImpl(userRepository),
                    UseUserHintsCountUseCaseImpl(userRepository),
                    AddUserHintsCountUseCaseImpl(userRepository)
                ),
            )
        }
    }
}

@Composable
private fun EnigmaEffectListener(
    enigmaViewModel: EnigmaGameViewModel,
    shakeController: ShakeController
) {
    val effect by enigmaViewModel.effect.collectAsState(null)
    val hapticFeedback = LocalHapticFeedback.current

    LaunchedEffect(effect) {
        when (effect) {
            is EnigmaEffect.InputEffect.Correct ->
                hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)

            is EnigmaEffect.InputEffect.Incorrect -> {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                shakeController.shake(ShakeConfig(iterations = 4, translateX = 5f))
            }

            else -> Unit
        }
    }
}
