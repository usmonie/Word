package com.usmonie.word.features.dashboard.ui.games.hangman

import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.ui.models.WordCombinedUi
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState


@Stable
sealed class HangmanState : ScreenState {
    abstract val word: WordCombinedUi
    abstract val guessedLetters: GuessedLetters
    abstract val lives: Int
    open val hintsCount: Int = 2
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
            override val lives: Int = MAX_LIVE_COUNT
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
    ): HangmanState() {
        override val lives: Int = MIN_LIVE_COUNT
    }

    data class Won(
        override val word: WordCombinedUi,
        override val guessedLetters: GuessedLetters,
        override val lives: Int,
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
    data object UpdateWord : HangmanAction()
    data object ShowHint : HangmanAction()
    data object ReviveClicked : HangmanAction()
    data object ReviveGranted : HangmanAction()
}

sealed class HangmanEvent : ScreenEvent {
    data class StartGame(val word: WordCombinedUi) : HangmanEvent()
    data object UpdateHint : HangmanEvent()
    data object Loading : HangmanEvent()
    data class OpenWord(val word: WordCombinedUi) : HangmanEvent()
    data class UpdateWord(val word: WordCombinedUi) : HangmanEvent()
    data class RightLetterGuessed(val letter: Char) : HangmanEvent()
    data class WrongLetterGuessed(val letter: Char) : HangmanEvent()
    data class Lost(val letter: Char) : HangmanEvent()
    data class Won(val letter: Char) : HangmanEvent()
    data object ReviveClicked : HangmanEvent()
    data object ReviveGranted : HangmanEvent()
}

@Stable
sealed class HangmanEffect : ScreenEffect {
    class Won : HangmanEffect()
    class Lost : HangmanEffect()
    class RestartGame : HangmanEffect()
    class ShowInterstitialAd : HangmanEffect()
    class ShowRewardedAd : HangmanEffect()
    class StartGame : HangmanEffect()
    data class OpenWord(val word: WordCombinedUi) : HangmanEffect()
}