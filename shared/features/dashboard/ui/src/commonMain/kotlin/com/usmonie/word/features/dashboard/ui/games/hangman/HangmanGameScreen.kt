package com.usmonie.word.features.dashboard.ui.games.hangman

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.AddUserHintsCountUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.GetUserHintsCountUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.UseUserHintsCountUseCaseImpl
import com.usmonie.word.features.dashboard.ui.details.WordDetailsScreen
import com.usmonie.word.features.dashboard.ui.games.GameBoard
import com.usmonie.word.features.dashboard.ui.games.HangmanGameWon
import com.usmonie.word.features.dashboard.ui.games.LivesAmount
import com.usmonie.word.features.dashboard.ui.games.ReviveLifeDialog
import com.usmonie.word.features.dashboard.ui.ui.AdMob
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.RouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.word.core.domain.tools.fastForEach

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
    }

    class Builder(
        private val wordRepository: WordRepository,
        private val userRepository: UserRepository,
        private val adMob: AdMob
    ) : ScreenBuilder {
        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return HangmanGameScreen(
                HangmanGameViewModel(
                    RandomWordUseCaseImpl(wordRepository),
                    GetUserHintsCountUseCaseImpl(userRepository),
                    UseUserHintsCountUseCaseImpl(userRepository),
                    AddUserHintsCountUseCaseImpl(userRepository),
                ),
                adMob
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

    val hintsCount = state.hintsCount
    HangmanEffect(effect, routerManager)
    GameBoard(
        routerManager::navigateBack,
        actions = {
            if (state !is HangmanState.Loading && state !is HangmanState.Error) {
                Spacer(Modifier.width(24.dp))

                LivesAmount(state.lives, state.maxLives, Modifier.weight(1f))
                com.usmonie.word.features.dashboard.ui.games.HintButton(
                    hangmanGameViewModel::useHint,
                    hintsCount
                )

//                Spacer(Modifier.width(24.dp))
//
//                IconButton({}) {
//                    Icon(Icons.Rounded.ShoppingCart, contentDescription = "Buy hint")
//                }
                Spacer(Modifier.width(24.dp))
            }
        }
    ) { insets ->
        AnimatedContent(state is HangmanState.Loading) { isLoading ->
            if (isLoading) {
                LoadingProgress(insets)
            } else {
                PlayBoard(insets, state, hangmanGameViewModel, adMob)
            }
        }

        AnimatedVisibility(
            state is HangmanState.Won,
        ) {
            HangmanGameWon(
                routerManager::navigateBack,
                hangmanGameViewModel::onUpdatePressed,
                "Next word",
                state.word
            )
        }

        AnimatedVisibility(
            state is HangmanState.Lost,
        ) {
            ReviveLifeDialog(
                routerManager::navigateBack,
                hangmanGameViewModel::onReviveClick,
                hangmanGameViewModel::onUpdatePressed,
                isReviveAvailable = { adMob.adMobState.isRewardReady },
                nextTitle = "Next word"
            )
        }

        AnimatedVisibility(
            effect is HangmanEffect.ShowRewardedAd,
        ) { adMob.RewardedInterstitial({}, hangmanGameViewModel::onRewardGranted) }

        AnimatedVisibility(
            effect is HangmanEffect.StartGame
                    || effect is HangmanEffect.RestartGame
                    || effect is HangmanEffect.ShowInterstitialAd
        ) { adMob.Interstitial() }
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
    insets: PaddingValues,
    state: HangmanState,
    hangmanGameViewModel: HangmanGameViewModel,
    adMob: AdMob
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(insets),
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
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .navigationBarsPadding(),
            contentKey = { hangmanState -> hangmanState::class::simpleName }
        ) { hangmanState ->
            if (hangmanState is HangmanState.Playing.Input) {
                Keyboard(
                    hangmanGameViewModel::onLetterGuessed,
                    hangmanState.guessedLetters,
                    Modifier.fillMaxSize(),
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

        adMob.Banner(Modifier.fillMaxWidth())
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
        val size  by remember(density) { derivedStateOf { 128.dp / density.density } }
        val modifier = Modifier.size(size)
        alphabet.fastForEach { letter ->
            val wasGuessed = remember(
                guessedLetters,
                letter
            ) { letter.lowercaseChar() in guessedLetters.letters }
            KeyboardButton(onLetterClick, letter, modifier, wasGuessed)
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
    ElevatedButton(
        onClick = { onLetterClick(letter) },
        enabled = !wasGuessed,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
    ) {
        Text(
            letter.toString(),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Immutable
data class GuessedLetters(val letters: Set<Char>)

val qwerty = listOf(
    'Q',
    'W',
    'E',
    'R',
    'T',
    'Y',
    'U',
    'I',
    'O',
    'P',
    'A',
    'S',
    'D',
    'F',
    'G',
    'H',
    'J',
    'K',
    'L',
    'Z',
    'X',
    'C',
    'V',
    'B',
    'N',
    'M'
)

val alphabet = ('A'..'Z').toList()
