package com.usmonie.word.features.dashboard.ui.games.hangman

import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCase
import com.usmonie.word.features.dashboard.ui.models.WordCombinedUi
import com.usmonie.word.features.dashboard.ui.models.toUi
import wtf.speech.core.ui.BaseViewModel

@Stable
class HangmanGameViewModel(
    private val randomWordUseCase: RandomWordUseCase,
) : BaseViewModel<HangmanState, HangmanAction, HangmanEvent, HangmanEffect>(HangmanState.Loading()) {

    init {
        handleAction(HangmanAction.StartGame)
    }

    fun onLetterGuessed(letter: Char) = handleAction(HangmanAction.GuessLetter(letter))
    fun onUpdatePressed() = handleAction(HangmanAction.UpdateWord)
    fun onShowHintPressed() = handleAction(HangmanAction.ShowHint)
    fun onOpenWordPressed(word: WordCombinedUi) = handleAction(HangmanAction.OpenWord(word))
    fun onRewardGranted(amount: Int) = handleAction(HangmanAction.ReviveClicked)
    fun onReviveClick() = handleAction(HangmanAction.ReviveClicked)

    override fun HangmanState.reduce(event: HangmanEvent): HangmanState {
        val guessedLetter = this.guessedLetters.letters.toMutableSet()
        return when (event) {
            is HangmanEvent.Loading -> HangmanState.Loading()
            is HangmanEvent.RightLetterGuessed -> HangmanState.Playing.Input(
                this.word,
                GuessedLetters(guessedLetter.apply { add(event.letter) }),
                this.lives
            )

            is HangmanEvent.WrongLetterGuessed -> {
                val newLives = lives - 1
                val newIncorrectGuesses = maxLives - newLives
                if (newIncorrectGuesses == 6) {
                    HangmanState.Lost(
                        this.word,
                        GuessedLetters(guessedLetter.apply { add(event.letter) }),
                        hintsCount
                    )
                } else {
                    HangmanState.Playing.Input(
                        this.word,
                        GuessedLetters(guessedLetter.apply { add(event.letter) }),
                        newLives
                    )
                }
            }

            is HangmanEvent.Lost -> HangmanState.Lost(
                this.word,
                GuessedLetters(guessedLetter.apply { add(event.letter) }),
                hintsCount
            )

            is HangmanEvent.Won -> HangmanState.Won(
                word,
                GuessedLetters(guessedLetter.apply { add(event.letter) }),
                lives
            )

            is HangmanEvent.UpdateWord -> HangmanState.Playing.Input(event.word)

            is HangmanEvent.StartGame -> HangmanState.Playing.Input(event.word)

            HangmanEvent.UpdateHint -> when (this) {
                is HangmanState.Playing.Information -> HangmanState.Playing.Input(
                    word,
                    guessedLetters,
                    lives
                )

                is HangmanState.Playing.Input -> HangmanState.Playing.Information(
                    word,
                    guessedLetters,
                    lives
                )

                else -> this
            }

            else -> this
        }
    }

    override suspend fun processAction(action: HangmanAction): HangmanEvent {
        val state = state.value
        val word = state.word
        return when (action) {
            is HangmanAction.GuessLetter -> {
                val letter = action.letter.lowercaseChar()
                val guessedLetter = state.guessedLetters.letters + letter

                if (state.maxLives - state.lives > 5) return HangmanEvent.Lost(letter)

                val isUserWon = word.word.filter { it.isLetterOrDigit() }
                        .all { it.lowercaseChar() in guessedLetter }

                if (isUserWon) return HangmanEvent.Won(letter)

                if (letter !in word.word.lowercase()) {
                    return HangmanEvent.WrongLetterGuessed(letter = letter)
                }
                HangmanEvent.RightLetterGuessed(letter)
            }

            is HangmanAction.OpenWord -> HangmanEvent.OpenWord(action.word)

            HangmanAction.UpdateWord -> HangmanEvent.UpdateWord(nextRandomWord())

            HangmanAction.ShowHint -> HangmanEvent.UpdateHint
            HangmanAction.StartGame -> HangmanEvent.StartGame(nextRandomWord())

            HangmanAction.ReviveClicked -> HangmanEvent.ReviveClicked
            HangmanAction.ReviveGranted -> HangmanEvent.ReviveGranted
        }
    }

    private suspend fun nextRandomWord() = randomWordUseCase(RandomWordUseCase.Param(10)).toUi()

    override suspend fun handleEvent(event: HangmanEvent) = when (event) {
        is HangmanEvent.Won -> HangmanEffect.Won()
        is HangmanEvent.Lost -> HangmanEffect.Lost()
        is HangmanEvent.UpdateWord -> HangmanEffect.RestartGame()
        is HangmanEvent.OpenWord -> HangmanEffect.OpenWord(event.word)
        is HangmanEvent.StartGame -> HangmanEffect.StartGame()
        is HangmanEvent.ReviveClicked -> HangmanEffect.ShowRewardedAd()
        else -> null
    }
}