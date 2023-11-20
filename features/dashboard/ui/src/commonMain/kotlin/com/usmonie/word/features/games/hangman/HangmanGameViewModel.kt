package com.usmonie.word.features.games.hangman

import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCase
import com.usmonie.word.features.models.to
import wtf.speech.core.ui.BaseViewModel

class HangmanGameViewModel(
    private val word: String,
    private val randomWordUseCase: RandomWordUseCase
) : BaseViewModel<HangmanState, HangmanAction, HangmanEvent, HangmanEffect>(
    HangmanState.Playing(word)
) {

    init {
        println("WORD: $word")
    }

    fun onLetterGuessed(letter: Char) = handleAction(HangmanAction.GuessLetter(letter))

    override fun HangmanState.reduce(event: HangmanEvent): HangmanState {
        return when (event) {
            is HangmanEvent.RightLetterGuessed -> HangmanState.Playing(
                this.word,
                this.guessedLetters.toMutableSet().apply { add(event.letter) },
                incorrectGuesses
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

            is HangmanEvent.Lost -> {
                HangmanState.Lost(
                    this.word,
                    this.guessedLetters.toMutableSet().apply { add(event.letter) },
                )
            }

            is HangmanEvent.Won -> HangmanState.Won(
                word,
                this.guessedLetters.toMutableSet().apply { add(event.letter) },
                0
            )

            is HangmanEvent.UpdateWord -> HangmanState.Playing(word)
        }
    }

    override suspend fun processAction(action: HangmanAction): HangmanEvent {
        return when (action) {
            is HangmanAction.GuessLetter -> {
                if (state.value.incorrectGuesses > 5) {
                    return HangmanEvent.Lost(action.letter)
                }
                if (word.all { it in state.value.guessedLetters }) {
                    return HangmanEvent.Won(action.letter)
                }

                if (action.letter !in word) {
                    return HangmanEvent.WrongLetterGuessed(letter = action.letter)
                }
                HangmanEvent.RightLetterGuessed(action.letter)
            }

            HangmanAction.UpdateWord -> HangmanEvent.UpdateWord(
                randomWordUseCase(RandomWordUseCase.Param(9)).to()
            )
        }
    }

    override suspend fun handleEvent(event: HangmanEvent) = when (event) {
        is HangmanEvent.Won -> HangmanEffect.Won()
        is HangmanEvent.Lost -> HangmanEffect.Lost()
        else -> null
    }
}