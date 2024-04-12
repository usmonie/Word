package com.usmonie.word.features.dashboard.ui.games.enigma

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
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
import com.usmonie.word.features.dashboard.ui.games.GameBoard
import com.usmonie.word.features.dashboard.ui.games.HintButton
import com.usmonie.word.features.dashboard.ui.games.LivesAmount
import com.usmonie.word.features.dashboard.ui.games.QuoteGameWon
import com.usmonie.word.features.dashboard.ui.games.ReviveLifeDialog
import com.usmonie.word.features.dashboard.ui.games.hangman.HangmanGameScreen
import com.usmonie.word.features.dashboard.ui.games.hangman.Keyboard
import com.usmonie.word.features.dashboard.ui.ui.AdMob
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.speech.core.ui.ShakeConfig
import wtf.speech.core.ui.rememberShakeController
import wtf.speech.core.ui.shake
import wtf.word.core.domain.tools.fastForEachIndexed

class EnigmaGameScreen(
    private val adMob: AdMob,
    private val enigmaViewModel: EnigmaGameViewModel
) : Screen() {

    override val id: String = ID

    @Composable
    override fun Content() {
        val state by enigmaViewModel.state.collectAsState()
        val effect by enigmaViewModel.effect.collectAsState(null)
        val phrase = state.phrase
        val routerManager = LocalRouteManager.current
        val hintsCount = state.hintsCount

        GameBoard(
            routerManager::navigateBack,
            {
                Spacer(Modifier.width(24.dp))

                LivesAmount(state.lives, state.maxLives, Modifier.weight(1f))
                HintButton(enigmaViewModel::useHint, hintsCount)

//                Spacer(Modifier.width(24.dp))
//
//                IconButton({}) {
//                    Icon(Icons.Rounded.ShoppingCart, contentDescription = "Buy hint")
//                }
                Spacer(Modifier.width(24.dp))
            }
        ) { insets ->

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
                QuoteGameWon(
                    routerManager::navigateBack,
                    enigmaViewModel::onNextPhrase,
                    nextTitle = "Next phrase",
                    state.phrase.phrase,
                    state.phrase.author
                )
            }

            val shakeController = rememberShakeController()
            val hapticFeedback = LocalHapticFeedback.current

            LaunchedEffect(effect) {
                when (effect) {
                    is EnigmaEffect.InputEffect.Correct ->
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)

                    is EnigmaEffect.InputEffect.Incorrect -> {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        shakeController.shake(ShakeConfig(4, translateX = 5f))
                    }

                    else -> Unit
                }
            }

            Column(Modifier.fillMaxSize().padding(insets).shake(shakeController)) {
                Phrase(phrase, state)

                Keyboard(
                    enigmaViewModel::onLetterInput,
                    state.guessedLetters,
                    Modifier.fillMaxWidth().weight(1f)
                )

                adMob.Banner(Modifier.fillMaxWidth())
            }
        }
    }

    @Composable
    private fun ColumnScope.Phrase(
        phrase: EnigmaEncryptedPhrase,
        state: EnigmaState
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize().weight(2f),
            contentPadding = PaddingValues(24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            columns = GridCells.Fixed(12),
        ) {
            itemsIndexed(
                phrase.encryptedPhrase,
                span = { _, word -> GridItemSpan(word.size) },
                itemContent = { position, cells -> Word(cells, state, position) }
            )
        }
    }

    @Composable
    private fun Word(word: Word, state: EnigmaState, wordPosition: Int) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxHeight()
        ) {
            word.cells.fastForEachIndexed { position, cell ->
                key(cell, position) {
                    CellItem(cell, state, wordPosition, position)
                }
            }
        }
    }

    @Composable
    private fun EnigmaGameScreen.CellItem(
        cell: Cell,
        state: EnigmaState,
        wordPosition: Int,
        position: Int
    ) {
        when {
            cell.isLetter -> GuessedLetter(
                cell,
                {
                    val (word, cellPosition) =
                        state.currentSelectedCellPosition ?: return@GuessedLetter false

                    word == wordPosition && cellPosition == position
                },
                enabled = state is EnigmaState.Playing
                        && cell.state != CellState.Correct
                        && cell.state != CellState.Found,
                onClick = { enigmaViewModel.onCellSelected(position, wordPosition) }
            )

            cell.letter.isWhitespace() -> Space()
            else -> Symbol(cell.letter, Modifier.fillMaxHeight())
        }
    }

    @Composable
    private fun GuessedLetter(
        cell: Cell,
        getIsSelected: () -> Boolean,
        onClick: () -> Unit,
        enabled: Boolean
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val primaryColor = MaterialTheme.colorScheme.primary
        val textColor = MaterialTheme.colorScheme.onBackground
        val selectedTextColor = MaterialTheme.colorScheme.onPrimary
        val errorColor = MaterialTheme.colorScheme.error
        val isSelected = getIsSelected()

        val (letter, letterColor, backgroundColor) = remember(cell, isSelected) {
            val state = cell.state
            when {
                state is CellState.Correct -> Triple(cell.letter, primaryColor, Color.Transparent)
                state is CellState.Found -> Triple(cell.letter, textColor, Color.Transparent)
                state is CellState.Incorrect -> Triple(
                    state.guessedLetter,
                    errorColor,
                    Color.Transparent
                )

                isSelected -> Triple("", selectedTextColor, primaryColor)
                else -> Triple(
                    if (cell.isLetter) "" else cell.letter,
                    textColor,
                    Color.Transparent
                )
            }
        }
        val backgroundAnimatedColor by animateColorAsState(
            backgroundColor,
            animationSpec = tween(400)
        )
        val letterAnimatedColor by animateColorAsState(letterColor, animationSpec = tween(400))
        Column(
            modifier = Modifier
                .width(20.dp)
                .background(backgroundAnimatedColor, RoundedCornerShape(6.dp))
                .clip(RoundedCornerShape(6.dp))
                .clickable(
                    indication = LocalIndication.current,
                    interactionSource = interactionSource,
                    enabled = enabled,
                    onClick = onClick,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            val pressed by interactionSource.collectIsPressedAsState()
            val dividerHorizontalPadding by animateDpAsState(if (pressed) 4.dp else 2.dp)

            val letterSize by animateFloatAsState(
                if (cell.state == CellState.Empty) 0f else 1f,
                label = "letter_size_cell_${cell.letter}"
            )

            Text(
                text = letter.toString(),
                fontSize = MaterialTheme.typography.titleMedium.fontSize * letterSize,
                style = MaterialTheme.typography.titleMedium,
                color = letterAnimatedColor,
                textAlign = TextAlign.Center
            )

            HorizontalDivider(
                Modifier.padding(
                    horizontal = dividerHorizontalPadding,
                    vertical = 2.dp
                )
            )

            Text(
                text = if (cell.state != CellState.Found) cell.number.toString() else "",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = letterAnimatedColor,
            )
        }
    }

    @Composable
    private fun Space() = Box(modifier = Modifier.width(20.dp))

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
        private val wordRepository: WordRepository,
        private val userRepository: UserRepository,
        private val adMob: AdMob
    ) : ScreenBuilder {
        override val id: String = HangmanGameScreen.ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return EnigmaGameScreen(
                adMob,
                EnigmaGameViewModel(
                    GetNextPhraseUseCaseImpl(),
                    GetUserHintsCountUseCaseImpl(userRepository),
                    UseUserHintsCountUseCaseImpl(userRepository),
                    AddUserHintsCountUseCaseImpl(userRepository)
                ),
            )
        }
    }
}
