package com.usmonie.word.features.new.games.hangman

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
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
import com.usmonie.word.features.ui.AdMob
import com.usmonie.word.features.ui.UpdateButton
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.RouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.speech.core.ui.AppKeys
import wtf.speech.core.ui.gradientBackground

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

    class Builder(
        private val wordRepository: WordRepository,
        private val adMob: AdMob
    ) : ScreenBuilder {
        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return HangmanGameScreen(
                HangmanGameViewModel(
                    RandomWordUseCaseImpl(wordRepository)
                ), adMob
            )
        }
    }
}

@Composable
private fun HangmanContent(
    hangmanGameViewModel: HangmanGameViewModel,
    adMob: AdMob
) {
    val routerManager = LocalRouteManager.current
    val state by hangmanGameViewModel.state.collectAsState()
    val effect by hangmanGameViewModel.effect.collectAsState(null)

    HangmanEffect(effect, routerManager)
    GameBoard(
        routerManager::navigateBack,
        {
            UpdateButton(
                hangmanGameViewModel::onUpdatePressed,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) { insets ->
        if (state is HangmanState.Loading) {
            LoadingProgress(insets)
        } else {
            PlayBoard(insets, state, hangmanGameViewModel, adMob)
        }

        if (effect is HangmanEffect.RestartGame) {
            adMob.RewardedInterstitial(onAddDismissed = {})
        }

        if (effect is HangmanEffect.StartGame) {
            adMob.Startup(AppKeys.STARTUP_ID)
        }
    }
}

@Composable
private fun LoadingProgress(insets: PaddingValues) {
    Box(
        Modifier
            .gradientBackground()
            .fillMaxSize()
            .padding(insets), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            Modifier.size(32.dp),
            MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun PlayBoard(
    insets: PaddingValues,
    state: HangmanState,
    hangmanGameViewModel: HangmanGameViewModel,
    adMob: AdMob
) {
    Column(
        modifier = Modifier
            .gradientBackground()
            .fillMaxSize()
            .padding(insets),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HangmanImage(state.incorrectGuesses, Modifier.fillMaxWidth().weight(1f))
        WordDisplay(state)

        Spacer(Modifier.height(8.dp))
        when (state) {
            is HangmanState.Playing.Information -> HintButton(
                hangmanGameViewModel::onShowHintPressed,
                "Hide Hint"
            )

            is HangmanState.Playing.Input -> HintButton(
                hangmanGameViewModel::onShowHintPressed,
                "Show Hint"
            )

            else -> HintButton(
                { hangmanGameViewModel.onOpenWordPressed(state.word) },
                "Show Details"
            )
        }
        Spacer(Modifier.height(8.dp))

        AnimatedContent(
            state,
            contentKey = { hangmanState -> hangmanState::class::simpleName }
        ) { hangmanState ->
            if (hangmanState is HangmanState.Playing.Input) {
                LetterButtons(
                    hangmanGameViewModel::onLetterGuessed,
                    hangmanState.guessedLetters,
                    Modifier.fillMaxWidth().weight(1f)
                )
            } else {
                Column(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.height(32.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        text = hangmanState.word.wordEtymology.first().words.first().senses.first().gloss,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(32.dp))
                }
            }
        }

        adMob.Banner(
            AppKeys.BANNER_ID,
            Modifier.fillMaxWidth()
        )
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
            color = MaterialTheme.colorScheme.onPrimary
        )
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
            .map { letter ->
                if (letter.lowercaseChar() in gameState.guessedLetters || !letter.isLetterOrDigit()) {
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
        color = when (gameState) {
            is HangmanState.Lost -> MaterialTheme.colorScheme.error
            is HangmanState.Playing -> MaterialTheme.colorScheme.onPrimary
            is HangmanState.Won -> MaterialTheme.colorScheme.tertiary
            is HangmanState.Loading -> MaterialTheme.colorScheme.onPrimary
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