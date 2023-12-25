package com.usmonie.word.features.new.games.hangman

import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCase
import com.usmonie.word.features.new.models.WordCombinedUi
import com.usmonie.word.features.new.models.toUi
import wtf.speech.core.ui.BaseViewModel

@Stable
class HangmanGameViewModel(
    word: WordCombinedUi,
    private val randomWordUseCase: RandomWordUseCase
) : BaseViewModel<HangmanState, HangmanAction, HangmanEvent, HangmanEffect>(
    HangmanState.Playing.Input(word)
) {
    fun onLetterGuessed(letter: Char) = handleAction(HangmanAction.GuessLetter(letter))
    fun onUpdatePressed() = handleAction(HangmanAction.UpdateWord)
    fun onShowHintPressed() = handleAction(HangmanAction.ShowHint)
    fun onOpenWordPressed(word: WordCombinedUi) = handleAction(HangmanAction.OpenWord(word))

    override fun HangmanState.reduce(event: HangmanEvent) = when (event) {
        is HangmanEvent.RightLetterGuessed -> HangmanState.Playing.Input(
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
                HangmanState.Playing.Input(
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

        is HangmanEvent.UpdateWord -> HangmanState.Playing.Input(event.word)
        HangmanEvent.UpdateHint -> when (this) {
            is HangmanState.Playing.Information -> HangmanState.Playing.Input(
                word,
                guessedLetters,
                incorrectGuesses
            )

            is HangmanState.Playing.Input -> HangmanState.Playing.Information(
                word,
                guessedLetters,
                incorrectGuesses
            )

            else -> this
        }

        else -> this
    }

    override suspend fun processAction(action: HangmanAction): HangmanEvent {
        val word = state.value.word
        return when (action) {
            is HangmanAction.GuessLetter -> {
                val letter = action.letter.lowercaseChar()
                val guessedLetter = state.value.guessedLetters + letter
                if (state.value.incorrectGuesses > 5) {
                    return HangmanEvent.Lost(letter)
                }
                if (word.word.all { it in guessedLetter }) {
                    return HangmanEvent.Won(letter)
                }

                if (action.letter.lowercase() !in word.word
                    && action.letter.lowercase() != word.word.lowercase()
                ) {
                    return HangmanEvent.WrongLetterGuessed(letter = letter)
                }
                HangmanEvent.RightLetterGuessed(letter)
            }

            is HangmanAction.OpenWord -> HangmanEvent.OpenWord(action.word)

            HangmanAction.UpdateWord -> HangmanEvent.UpdateWord(
                randomWordUseCase(RandomWordUseCase.Param(9)).toUi()
            )

            HangmanAction.ShowHint -> HangmanEvent.UpdateHint
        }
    }

    override suspend fun handleEvent(event: HangmanEvent) = when (event) {
        is HangmanEvent.Won -> HangmanEffect.Won()
        is HangmanEvent.Lost -> HangmanEffect.Lost()
        is HangmanEvent.UpdateWord -> HangmanEffect.RestartGame()
        is HangmanEvent.OpenWord -> HangmanEffect.OpenWord(event.word)
        else -> null
    }
}