package com.usmonie.word.features.new.games.hangman

import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCase
import com.usmonie.word.features.new.models.WordCombinedUi
import com.usmonie.word.features.new.models.toUi
import wtf.speech.core.ui.BaseViewModel

@Stable
class HangmanGameViewModel(
    private val word: WordCombinedUi,
    private val randomWordUseCase: RandomWordUseCase
) : BaseViewModel<HangmanState, HangmanAction, HangmanEvent, HangmanEffect>(
    HangmanState.Playing(word.word)
) {
    fun onLetterGuessed(letter: Char) = handleAction(HangmanAction.GuessLetter(letter))
    fun onUpdatePressed() = handleAction(HangmanAction.UpdateWord)

    override fun HangmanState.reduce(event: HangmanEvent) = when (event) {
        is HangmanEvent.RightLetterGuessed -> HangmanState.Playing(
            this.word,
            this.guessedLetters.toMutableSet().apply { add(event.letter) },
            this.incorrectGuesses
        )

        is HangmanEvent.WrongLetterGuessed -> {
            val newIncorrectGuesses = incorrectGuesses + 1
            if (newIncorrectGuesses == 6) {
                HangmanState.Lost(
                    this.word,
                    this.guessedLetters.toMutableSet().apply { add(event.letter) },
                )
            } else {
                HangmanState.Playing(
                    this.word,
                    this.guessedLetters.toMutableSet().apply { add(event.letter) },
                    newIncorrectGuesses
                )
            }
        }

        is HangmanEvent.Lost -> HangmanState.Lost(
            this.word,
            this.guessedLetters.toMutableSet().apply { add(event.letter) },
        )

        is HangmanEvent.Won -> HangmanState.Won(
            word,
            this.guessedLetters.toMutableSet().apply { add(event.letter) },
        )

        is HangmanEvent.UpdateWord -> HangmanState.Playing(event.word.word)
    }

    override suspend fun processAction(action: HangmanAction): HangmanEvent {
        return when (action) {
            is HangmanAction.GuessLetter -> {
                if (state.value.incorrectGuesses > 5) {
                    return HangmanEvent.Lost(action.letter.lowercaseChar())
                }
                if (word.word.all { it in state.value.guessedLetters }) {
                    return HangmanEvent.Won(action.letter.lowercaseChar())
                }

                if (action.letter.lowercase() !in word.word) {
                    return HangmanEvent.WrongLetterGuessed(letter = action.letter.lowercaseChar())
                }
                HangmanEvent.RightLetterGuessed(action.letter.lowercaseChar())
            }

            HangmanAction.UpdateWord -> HangmanEvent.UpdateWord(
                randomWordUseCase(RandomWordUseCase.Param(9)).toUi()
            )
        }
    }

    override suspend fun handleEvent(event: HangmanEvent) = when (event) {
        is HangmanEvent.Won -> HangmanEffect.Won()
        is HangmanEvent.Lost -> HangmanEffect.Lost()
        else -> null
    }
}