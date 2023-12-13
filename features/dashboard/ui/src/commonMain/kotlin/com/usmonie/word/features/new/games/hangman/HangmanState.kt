package com.usmonie.word.features.new.games.hangman

import com.usmonie.word.features.new.models.WordCombinedUi
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState

sealed class HangmanState(
    open val word: String,
    open val guessedLetters: Set<Char>,
    open val incorrectGuesses: Int,
) : ScreenState {

    data class Playing(
        override val word: String,
        override val guessedLetters: Set<Char> = setOf(),
        override val incorrectGuesses: Int = 0
    ) : HangmanState(word, guessedLetters, incorrectGuesses)

    data class Lost(
        override val word: String,
        override val guessedLetters: Set<Char>,
    ) : HangmanState(word, guessedLetters, 6)

    data class Won(
        override val word: String,
        override val guessedLetters: Set<Char>,
        override val incorrectGuesses: Int
    ) : HangmanState(word, guessedLetters, incorrectGuesses)
}

sealed class HangmanAction : ScreenAction {
    data class GuessLetter(val letter: Char) : HangmanAction()
    data object UpdateWord : HangmanAction()
}

sealed class HangmanEvent : ScreenEvent {
    data class UpdateWord(val word: WordCombinedUi) : HangmanEvent()
    data class RightLetterGuessed(val letter: Char) : HangmanEvent()
    data class WrongLetterGuessed(val letter: Char) : HangmanEvent()
    data class Lost(val letter: Char) : HangmanEvent()
    data class Won(val letter: Char) : HangmanEvent()
}

sealed class HangmanEffect : ScreenEffect {
    class Won : HangmanEffect()

    class Lost : HangmanEffect()
}