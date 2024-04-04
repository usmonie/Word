package com.usmonie.word.features.dashboard.ui.games.enigma

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.GetNextPhraseUseCaseImpl
import com.usmonie.word.features.dashboard.ui.games.GameBoard
import com.usmonie.word.features.dashboard.ui.games.enigma.EnigmaState.Loading.lives
import com.usmonie.word.features.dashboard.ui.games.hangman.HangmanGameScreen
import com.usmonie.word.features.dashboard.ui.games.hangman.Keyboard
import com.usmonie.word.features.dashboard.ui.ui.AdMob
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.speech.core.ui.AppKeys
import wtf.word.core.design.themes.icons.MyIconPack
import wtf.word.core.design.themes.icons.myiconpack.IcLifebuoy
import wtf.word.core.domain.tools.fastForEachIndexed

class EnigmaGameScreen(
    private val adMob: AdMob,
    private val enigmaViewModel: EnigmaGameViewModel
) : Screen() {

    override val id: String = ID

    @Composable
    override fun Content() {
        val state by enigmaViewModel.state.collectAsState()
        val phrase = state.phrase
        val routerManager = LocalRouteManager.current

        GameBoard(
            routerManager::navigateBack,
            { LivesAmount(state) }
        ) { insets ->
            Column(Modifier.fillMaxSize().padding(insets)) {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth().weight(2f),
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
                    columns = GridCells.Fixed(14),
                ) {
                    itemsIndexed(
                        phrase.phrase,
                        span = { _, word -> GridItemSpan(word.size) }
                    ) { wordPosition, wordCells ->
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            wordCells.fastForEachIndexed { position, cell ->
                                key(cell, position) {
                                    CellItem(cell, state, wordPosition, position)
                                }
                            }
                        }
                    }
                }

                Keyboard(
                    enigmaViewModel::onLetterInput,
                    setOf(),
                    Modifier.fillMaxWidth().weight(1f)
                )

                adMob.Banner(AppKeys.BANNER_ID, Modifier.fillMaxWidth())
            }
        }
    }

    @Composable
    private fun LivesAmount(state: EnigmaState) {
        val animatedLives by animateIntAsState(lives, tween(durationMillis = 1000))
        repeat(animatedLives) {
            Icon(
                MyIconPack.IcLifebuoy,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(8.dp))
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
                    val (word, cellPosition) = state.currentSelectedCellPosition
                        ?: return@GuessedLetter false

                    word == wordPosition && cellPosition == position
                },
                Modifier.clickable(
                    enabled = cell.state != CellState.Correct && cell.state != CellState.Found
                ) { enigmaViewModel.onCellSelected(position, wordPosition) }
            )

            cell.letter.isWhitespace() -> Space()
            else -> Symbol(cell.letter, Modifier.fillMaxHeight())
        }
    }

    @Composable
    private fun GuessedLetter(
        cell: Cell,
        getIsSelected: () -> Boolean,
        modifier: Modifier = Modifier
    ) {
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

        Column(
            modifier = modifier
                .width(20.dp)
                .background(backgroundColor, RoundedCornerShape(4.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = letter.toString(),
                style = MaterialTheme.typography.titleMedium,
                color = letterColor,
                textAlign = TextAlign.Center
            )
            HorizontalDivider(Modifier.padding(2.dp))
            Text(
                text = if (cell.state != CellState.Found) cell.number.toString() else "",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = letterColor,
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
        private val adMob: AdMob
    ) : ScreenBuilder {
        override val id: String = HangmanGameScreen.ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return EnigmaGameScreen(
                adMob,
                EnigmaGameViewModel(GetNextPhraseUseCaseImpl()),
            )
        }
    }
}

val phrase = "If you look at what you have in life, you'll always have more. " +
        "If you look at what you don't have in life, you'll never have enough."
