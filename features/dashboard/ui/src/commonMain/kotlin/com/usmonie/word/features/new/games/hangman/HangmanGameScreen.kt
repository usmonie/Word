package com.usmonie.word.features.new.games.hangman

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCaseImpl
import com.usmonie.word.features.new.details.WordDetailsScreen
import com.usmonie.word.features.new.games.GameBoard
import com.usmonie.word.features.new.models.WordCombinedUi
import com.usmonie.word.features.ui.AdMob
import com.usmonie.word.features.ui.UpdateButton
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.RouteManager
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
        HangmanContent(hangmanGameViewModel, adMob)
    }

    companion object {
        const val ID = "HangmanGameScreen"
        const val KEY = "HangmanGameScreenExtra"
    }

    data class Extras(val word: WordCombinedUi) : Extra {
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
private fun HangmanContent(hangmanGameViewModel: HangmanGameViewModel, adMob: AdMob) {
    val routerManager = LocalRouteManager.current
    val state by hangmanGameViewModel.state.collectAsState()
    val effect by hangmanGameViewModel.effect.collectAsState(null)

    HangmanEffect(effect, routerManager)
    GameBoard(routerManager::navigateBack, {
        AnimatedVisibility(state !is HangmanState.Playing) {
            UpdateButton(
                hangmanGameViewModel::onUpdatePressed,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HangmanImage(state.incorrectGuesses, Modifier.fillMaxWidth().fillMaxHeight(0.4f))
            WordDisplay(state)

            Spacer(Modifier.height(8.dp))
            when (state) {
                is HangmanState.Playing.Information -> TextButton(
                    hangmanGameViewModel::onShowHintPressed,
                    Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                ) {
                    Text(
                        "Hide Hint",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                is HangmanState.Playing.Input -> TextButton(
                    hangmanGameViewModel::onShowHintPressed,
                    Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                ) {
                    Text(
                        "Show Hint",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                else -> TextButton(
                    { hangmanGameViewModel.onOpenWordPressed(state.word) },
                    Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                ) {
                    Text(
                        "Show Details",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Spacer(Modifier.height(8.dp))

            AnimatedContent(
                state,
                contentKey = { hangmanState -> hangmanState::class::simpleName }) { hangmanState ->
                when (hangmanState) {
                    is HangmanState.Playing.Information -> Text(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        text = hangmanState.word.wordEtymology.first().words.first().senses.first().glosses.first(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    else -> LetterButtons(
                        hangmanGameViewModel::onLetterGuessed,
                        hangmanState.guessedLetters,
                        Modifier.fillMaxWidth().weight(1f)
                    )
                }
            }

            adMob.Banner(
                AdKeys.BANNER_ID,
                Modifier.fillMaxWidth()
            )
        }

        if (effect !is HangmanEffect.RestartGame) {
            adMob.RewardedInterstitial(onAddDismissed = {})
        }
    }
}

@Composable
private fun HangmanEffect(effect: HangmanEffect?, routerManager: RouteManager) {
    LaunchedEffect(effect) {
        when (effect) {
            is HangmanEffect.OpenWord -> routerManager.navigateTo(
                WordDetailsScreen.ID,
                extras = WordDetailsScreen.Companion.WordExtra(effect.word)
            )

            else -> Unit
        }
    }
}

@Composable
fun WordDisplay(gameState: HangmanState, modifier: Modifier = Modifier) {
    val displayWord = if (gameState is HangmanState.Lost) {
        gameState.word.word.toCharArray().joinToString(" ")
    } else {
        gameState.word
            .word
            .asSequence()
            .map { if (it in gameState.guessedLetters) it else '_' }
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
        style = MaterialTheme.typography.displaySmall,
        textAlign = TextAlign.Center
    )
}

@Suppress("NonSkippableComposable")
@Composable
fun LetterButtons(onLetterClick: (Char) -> Unit, guessedLetters: Set<Char>, modifier: Modifier) {
    val alphabet = remember { ('A'..'Z').toList() }
    LazyVerticalGrid(
        GridCells.Fixed(7),
        modifier = modifier,
        userScrollEnabled = false,
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        items(alphabet) { letter ->
            val wasGuessed = letter.lowercaseChar() in guessedLetters
            TextButton(onClick = { onLetterClick(letter) }, enabled = !wasGuessed) {
                Text(
                    letter.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = if (wasGuessed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}