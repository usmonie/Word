package com.usmonie.word.features.games.hangman

import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCase
import com.usmonie.word.features.models.WordCombinedUi
import com.usmonie.word.features.models.toUi
import wtf.speech.core.ui.BaseViewModel

@Stable
class HangmanGameViewModel(
    private val randomWordUseCase: RandomWordUseCase
) : BaseViewModel<HangmanState, HangmanAction, HangmanEvent, HangmanEffect>(HangmanState.Loading()) {

    init {
        handleAction(HangmanAction.StartGame)
    }

    fun onLetterGuessed(letter: Char) = handleAction(HangmanAction.GuessLetter(letter))
    fun onUpdatePressed() = handleAction(HangmanAction.UpdateWord)
    fun onShowHintPressed() = handleAction(HangmanAction.ShowHint)
    fun onOpenWordPressed(word: WordCombinedUi) = handleAction(HangmanAction.OpenWord(word))

    override fun HangmanState.reduce(event: HangmanEvent): HangmanState {
        val guessedLetter = this.guessedLetters.toMutableSet()
        return when (event) {
            is HangmanEvent.Loading -> HangmanState.Loading()
            is HangmanEvent.RightLetterGuessed -> HangmanState.Playing.Input(
                this.word,
                guessedLetter.apply { add(event.letter) },
                this.incorrectGuesses
            )

            is HangmanEvent.WrongLetterGuessed -> {
                val newIncorrectGuesses = incorrectGuesses + 1
                if (newIncorrectGuesses == 6) {
                    HangmanState.Lost(
                        this.word,
                        guessedLetter.apply { add(event.letter) },
                    )
                } else {
                    HangmanState.Playing.Input(
                        this.word,
                        guessedLetter.apply { add(event.letter) },
                        newIncorrectGuesses
                    )
                }
            }

            is HangmanEvent.Lost -> HangmanState.Lost(
                this.word,
                guessedLetter.apply { add(event.letter) },
            )

            is HangmanEvent.Won -> HangmanState.Won(
                word,
                guessedLetter.apply { add(event.letter) },
            )

            is HangmanEvent.UpdateWord -> HangmanState.Playing.Input(event.word)

            is HangmanEvent.StartGame -> HangmanState.Playing.Input(event.word)

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

                if (
                    word.word.filter { it.isLetterOrDigit() }
                        .all { it.lowercaseChar() in guessedLetter }
                ) {
                    return HangmanEvent.Won(letter)
                }

                if (letter !in word.word.lowercase()) {
                    return HangmanEvent.WrongLetterGuessed(letter = letter)
                }
                HangmanEvent.RightLetterGuessed(letter)
            }

            is HangmanAction.OpenWord -> HangmanEvent.OpenWord(action.word)

            HangmanAction.UpdateWord -> HangmanEvent.UpdateWord(
                randomWordUseCase(RandomWordUseCase.Param(9)).toUi()
            )

            HangmanAction.ShowHint -> HangmanEvent.UpdateHint
            HangmanAction.StartGame -> {
                HangmanEvent.StartGame(
                    randomWordUseCase(RandomWordUseCase.Param(9)).toUi()
                )
            }
        }
    }

    override suspend fun handleEvent(event: HangmanEvent) = when (event) {
        is HangmanEvent.Won -> HangmanEffect.Won()
        is HangmanEvent.Lost -> HangmanEffect.Lost()
        is HangmanEvent.UpdateWord -> HangmanEffect.RestartGame()
        is HangmanEvent.OpenWord -> HangmanEffect.OpenWord(event.word)
        is HangmanEvent.StartGame -> HangmanEffect.StartGame()
        else -> null
    }
}