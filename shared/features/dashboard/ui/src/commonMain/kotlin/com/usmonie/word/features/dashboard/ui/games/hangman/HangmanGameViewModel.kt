package com.usmonie.word.features.dashboard.ui.games.hangman

import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.usecase.AddUserHintsCountUseCase
import com.usmonie.word.features.dashboard.domain.usecase.GetUserHintsCountUseCase
import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCase
import com.usmonie.word.features.dashboard.domain.usecase.UseUserHintsCountUseCase
import com.usmonie.word.features.dashboard.ui.models.WordCombinedUi
import com.usmonie.word.features.dashboard.ui.models.toUi
import wtf.speech.core.ui.BaseViewModel
import wtf.word.core.domain.tools.xor

@Stable
class HangmanGameViewModel(
    private val randomWordUseCase: RandomWordUseCase,
    private val getUserHintsCountUseCase: GetUserHintsCountUseCase,
    private val useUserHintsCountUseCase: UseUserHintsCountUseCase,
    private val addUserHintsCountUseCase: AddUserHintsCountUseCase
) : BaseViewModel<HangmanState, HangmanAction, HangmanEvent, HangmanEffect>(HangmanState.Loading()) {

    init {
        viewModelScope.launchSafe {
            addUserHintsCountUseCase(100)
        }
        handleAction(HangmanAction.StartGame)
    }

    fun onLetterGuessed(letter: Char) = handleAction(HangmanAction.GuessLetter(letter))
    fun onUpdatePressed() = handleAction(HangmanAction.UpdateWord)
    fun onShowHintPressed() = handleAction(HangmanAction.ShowDescription)
    fun onOpenWordPressed(word: WordCombinedUi) = handleAction(HangmanAction.OpenWord(word))
    fun onRewardGranted(amount: Int) = handleAction(HangmanAction.ReviveClicked)
    fun onReviveClick() = handleAction(HangmanAction.ReviveGranted)
    fun useHint() = handleAction(HangmanAction.UseHint)

    override fun HangmanState.reduce(event: HangmanEvent): HangmanState {
        val guessedLetter = this.guessedLetters.letters.toMutableSet()
        return when (event) {
            is HangmanEvent.Loading -> HangmanState.Loading()
            is HangmanEvent.RightLetterGuessed -> HangmanState.Playing.Input(
                this.word,
                GuessedLetters(guessedLetter.apply { add(event.letter) }),
                this.lives,
                hintsCount
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
                        newLives,
                        hintsCount
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
                lives,
                hintsCount
            )

            is HangmanEvent.UpdateWord -> HangmanState.Playing.Input(
                event.word,
                hintsCount = event.hintsCount
            )

            is HangmanEvent.StartGame -> HangmanState.Playing.Input(
                event.word,
                hintsCount = event.hintsCount
            )

            HangmanEvent.UpdateDescription -> when (this) {
                is HangmanState.Playing.Information -> HangmanState.Playing.Input(
                    word,
                    guessedLetters,
                    lives,
                    hintsCount
                )

                is HangmanState.Playing.Input -> HangmanState.Playing.Information(
                    word,
                    guessedLetters,
                    lives
                )

                else -> this
            }

            HangmanEvent.ReviveGranted -> HangmanState.Playing.Input(
                word, guessedLetters, lives + 1, hintsCount
            )

            else -> this
        }
    }

    override suspend fun processAction(action: HangmanAction): HangmanEvent {
        val state = state.value
        val word = state.word
        return when (action) {
            is HangmanAction.GuessLetter -> guessLetter(action, state, word)

            is HangmanAction.OpenWord -> HangmanEvent.OpenWord(action.word)

            HangmanAction.UpdateWord -> HangmanEvent.UpdateWord(nextRandomWord(), getHintsCount())

            HangmanAction.ShowDescription -> HangmanEvent.UpdateDescription
            HangmanAction.StartGame -> HangmanEvent.StartGame(nextRandomWord(), getHintsCount())

            HangmanAction.ReviveClicked -> HangmanEvent.ReviveClicked
            HangmanAction.ReviveGranted -> HangmanEvent.ReviveGranted
            HangmanAction.UseHint -> useHint(state)
        }
    }

    private suspend fun useHint(state: HangmanState): HangmanEvent {
        val userHintsCount = getUserHintsCountUseCase(Unit)

        val guessedLetters = state.guessedLetters
        val wordSet = state.word.word.toSet()
        if (userHintsCount < 1
            && guessedLetters.letters.size < wordSet.size
        ) {
            return HangmanEvent.CannotUseHints
        }
        useUserHintsCountUseCase(Unit)
        val newUserHintsCount = getUserHintsCountUseCase(Unit)

        val letter = (wordSet xor guessedLetters.letters).random()
        val gameStatusEvent = checkGameStatus(state, letter, state.word)
        if (gameStatusEvent != null) {
            return gameStatusEvent
        }

        return HangmanEvent.RightLetterGuessed(letter, newUserHintsCount)
    }

    private suspend fun guessLetter(
        action: HangmanAction.GuessLetter,
        state: HangmanState,
        word: WordCombinedUi
    ): HangmanEvent {
        val letter = action.letter.lowercaseChar()
        val gameStatusEvent = checkGameStatus(state, letter, word)
        if (gameStatusEvent != null) {
            return gameStatusEvent
        }

        if (letter !in word.word.lowercase()) {
            return HangmanEvent.WrongLetterGuessed(letter = letter)
        }

        val newUserHintsCount = getUserHintsCountUseCase(Unit)

        return HangmanEvent.RightLetterGuessed(letter, newUserHintsCount)
    }

    private fun checkGameStatus(
        state: HangmanState,
        letter: Char,
        word: WordCombinedUi
    ): HangmanEvent? {
        val guessedLetter = state.guessedLetters.letters + letter

        if (state.maxLives - state.lives > 5) return HangmanEvent.Lost(letter)

        val isUserWon = word.word
            .filter { it.isLetterOrDigit() }
            .all { it.lowercaseChar() in guessedLetter }

        if (isUserWon) return HangmanEvent.Won(letter)

        return null
    }

    private suspend fun nextRandomWord() = randomWordUseCase(RandomWordUseCase.Param(10)).toUi()
    private suspend fun getHintsCount() = getUserHintsCountUseCase(Unit)
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