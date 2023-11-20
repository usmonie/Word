package com.usmonie.word.features.games.hangman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCaseImpl
import com.usmonie.word.features.games.GameBoard
import com.usmonie.word.features.ui.AdMob
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.speech.core.ui.AdKeys

class HangmanGameScreen(
    private val hangmanGameViewModel: HangmanGameViewModel,
    private val adMob: AdMob
) : Screen(hangmanGameViewModel) {
    override val id: String = ID

    @Composable
    override fun Content() {
        val routerManager = LocalRouteManager.current
        val state by hangmanGameViewModel.state.collectAsState()
        GameBoard(routerManager::navigateBack) {
            Column(
                modifier = Modifier.fillMaxSize().padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PlayingBoard(state, hangmanGameViewModel)

                if (state !is HangmanState.Playing) {
                    adMob.RewardedInterstitial()
                }

//                when (state) {
//                    is HangmanState.Lost -> PlayingBoard(state, hangmanGameViewModel)
//                    is HangmanState.Playing -> PlayingBoard(state, hangmanGameViewModel)
//                    is HangmanState.Won -> PlayingBoard(state, hangmanGameViewModel)
//                }
                adMob.Banner(
                    AdKeys.BANNER_ID,
                    Modifier.fillMaxWidth()
                )
            }
        }
    }

    companion object {
        const val ID = "HangmanGameScreen"
        const val KEY = "HangmanGameScreenExtra"
    }

    data class Extras(val word: String) : Extra {
        override val key: String = KEY
    }

    class Builder(
        private val wordRepository: WordRepository,
        private val adMob: AdMob
    ) : ScreenBuilder {
        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            require(extra is Extras)
            return HangmanGameScreen(
                HangmanGameViewModel(
                    extra.word,
                    RandomWordUseCaseImpl(wordRepository)
                ), adMob
            )
        }
    }
}

@Composable
private fun ColumnScope.PlayingBoard(
    state: HangmanState,
    hangmanGameViewModel: HangmanGameViewModel
) {
    HangmanImage(state.incorrectGuesses, Modifier.fillMaxWidth().fillMaxHeight(0.5f))
    WordDisplay(state)
    LetterButtons(hangmanGameViewModel::onLetterGuessed, Modifier.fillMaxWidth().weight(1f))
}

@Composable
fun WordDisplay(gameState: HangmanState, modifier: Modifier = Modifier) {
    val displayWord = if (gameState is HangmanState.Lost) {
        gameState.word.toCharArray().joinToString(" ")
    } else {
        gameState.word.map { if (it in gameState.guessedLetters) it else '_' }
            .joinToString(" ")
    }
    Text(
        displayWord,
        modifier,
        color = when (gameState) {
            is HangmanState.Lost -> MaterialTheme.colorScheme.error
            is HangmanState.Playing -> MaterialTheme.colorScheme.onPrimary
            is HangmanState.Won -> MaterialTheme.colorScheme.surfaceVariant
        },

        style = MaterialTheme.typography.displayMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
fun LetterButtons(onLetterClick: (Char) -> Unit, modifier: Modifier) {
    val alphabet = remember { ('A'..'Z').toList() }
    LazyVerticalGrid(
        GridCells.Fixed(7),
        modifier = modifier,
        userScrollEnabled = false,
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        items(alphabet) { letter ->
            TextButton(onClick = { onLetterClick(letter) }) {
                Text(
                    letter.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}