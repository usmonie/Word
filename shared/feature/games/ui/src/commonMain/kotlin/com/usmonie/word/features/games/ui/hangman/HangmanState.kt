package com.usmonie.word.features.games.ui.hangman

import androidx.compose.runtime.Immutable
import com.usmonie.compass.viewmodel.ScreenAction
import com.usmonie.compass.viewmodel.ScreenEffect
import com.usmonie.compass.viewmodel.ScreenEvent
import com.usmonie.compass.viewmodel.ScreenState
import com.usmonie.word.features.games.ui.models.WordCombinedUi

@Immutable
sealed class HangmanState : ScreenState {
    abstract val word: WordCombinedUi
    abstract val guessedLetters: GuessedLetters
    abstract val lives: Int
    open val hintsCount: Int = 0
    val maxLives = MAX_LIVE_COUNT

    class Loading : HangmanState() {
        override val word: WordCombinedUi = WordCombinedUi("", listOf(), false)

        override val guessedLetters: GuessedLetters = GuessedLetters(setOf())
        override val lives: Int = maxLives
        override val hintsCount: Int = 0
    }

    class Error : HangmanState() {
        override val word: WordCombinedUi = WordCombinedUi("", listOf(), false)

        override val guessedLetters: GuessedLetters = GuessedLetters(setOf())
        override val lives: Int = maxLives
        override val hintsCount: Int = 0
    }

    sealed class Playing : HangmanState() {
        data class Input(
            override val word: WordCombinedUi,
            override val guessedLetters: GuessedLetters = GuessedLetters(setOf()),
            override val lives: Int = MAX_LIVE_COUNT,
            override val hintsCount: Int
        ) : Playing()

        data class Information(
            override val word: WordCombinedUi,
            override val guessedLetters: GuessedLetters = GuessedLetters(setOf()),
            override val lives: Int = MAX_LIVE_COUNT
        ) : Playing()
    }

    data class Lost(
        override val word: WordCombinedUi,
        override val guessedLetters: GuessedLetters,
        override val hintsCount: Int
    ) : HangmanState() {
        override val lives: Int = MIN_LIVE_COUNT
    }

    data class Won(
        override val word: WordCombinedUi,
        override val guessedLetters: GuessedLetters,
        override val lives: Int,
        override val hintsCount: Int
    ) : HangmanState()

    companion object {
        const val MAX_LIVE_COUNT = 6
        const val MIN_LIVE_COUNT = 0
    }
}

sealed class HangmanAction : ScreenAction {
    data object StartGame : HangmanAction()
    data class GuessLetter(val letter: Char) : HangmanAction()
    data class OpenWord(val word: WordCombinedUi) : HangmanAction()
    data class UpdateUserHints(val hintsCount: Int) : HangmanAction()
    data class HintReward(val amount: Int) : HangmanAction()
    data object UpdateWord : HangmanAction()
    data object ShowDescription : HangmanAction()
    data object ReviveClicked : HangmanAction()
    data object ReviveGranted : HangmanAction()
    data object UseHint : HangmanAction()
}

sealed class HangmanEvent : ScreenEvent {
    data class StartGame(val word: WordCombinedUi, val hintsCount: Int) : HangmanEvent()
    data class OpenWord(val word: WordCombinedUi) : HangmanEvent()
    data class UpdateWord(val word: WordCombinedUi) : HangmanEvent()
    data class UpdateHints(val hintsCount: Int) : HangmanEvent()
    data class RightLetterGuessed(val letter: Char, val hintsCount: Int) : HangmanEvent()
    data class WrongLetterGuessed(val letter: Char) : HangmanEvent()
    data class Lost(val letter: Char) : HangmanEvent()
    data class Won(val letter: Char) : HangmanEvent()
    data object UpdateDescription : HangmanEvent()
    data object Loading : HangmanEvent()
    data object CannotUseHints : HangmanEvent()
    data object ReviveLifeClicked : HangmanEvent()
    data object ReviveLifeGranted : HangmanEvent()
}

sealed class HangmanEffect : ScreenEffect {
    class Won : HangmanEffect()
    class Lost : HangmanEffect()
    class Wrong : HangmanEffect()
    class RestartGame : HangmanEffect()
    class ShowInterstitialAd : HangmanEffect()
    class ShowRewardedLifeAd : HangmanEffect()
    class ShowRewardedHintAd : HangmanEffect()
    class StartGame : HangmanEffect()
    data class OpenWord(val word: WordCombinedUi) : HangmanEffect()
}

@Immutable
data class GuessedLetters(val letters: Set<Char>)

val qwerty = listOf(
    listOf(
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
    ),
    listOf(
        'A',
        'S',
        'D',
        'F',
        'G',
        'H',
        'J',
        'K',
        'L'
    ),
    listOf(
        'Z',
        'X',
        'C',
        'V',
        'B',
        'N',
        'M'
    )
)

val alphabet = ('A'..'Z').toList()
