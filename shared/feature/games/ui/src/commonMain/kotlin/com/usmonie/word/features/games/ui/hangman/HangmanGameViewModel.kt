package com.usmonie.word.features.games.ui.hangman

import androidx.compose.runtime.Immutable
import com.usmonie.compass.viewmodel.StateViewModel
import com.usmonie.core.domain.tools.xor
import com.usmonie.core.domain.usecases.invoke
import com.usmonie.word.features.dictionary.domain.usecases.GetRandomWordUseCase
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import com.usmonie.word.features.dictionary.ui.models.toUi
import com.usmonie.word.features.settings.domain.usecase.AddUserHintsCountUseCase
import com.usmonie.word.features.settings.domain.usecase.GetUserHintsCountUseCase
import com.usmonie.word.features.settings.domain.usecase.UseUserHintsCountUseCase
import kotlinx.coroutines.flow.first

@Immutable
class HangmanGameViewModel(
    private val randomWordUseCase: GetRandomWordUseCase,
    private val getUserHintsCountUseCase: GetUserHintsCountUseCase,
    private val useUserHintsCountUseCase: UseUserHintsCountUseCase,
    private val addUserHintsCountUseCase: AddUserHintsCountUseCase
) : StateViewModel<HangmanState, HangmanAction, HangmanEvent, HangmanEffect>(HangmanState.Loading()) {

    init {
        handleAction(HangmanAction.StartGame)

        viewModelScope.launchSafe {
            getUserHintsCountUseCase().collect {
                handleAction(HangmanAction.UpdateUserHints(it))
            }
        }
    }

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
                hintsCount = hintsCount
            )

            is HangmanEvent.StartGame -> HangmanState.Playing.Input(
                event.word,
                hintsCount = event.hintsCount
            )

            is HangmanEvent.UpdateHints -> when (this) {
                is HangmanState.Playing.Input -> copy(hintsCount = event.hintsCount)
                else -> this
            }

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

            HangmanEvent.ReviveLifeGranted -> HangmanState.Playing.Input(
                word,
                guessedLetters,
                lives + 1,
                hintsCount
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
            is HangmanAction.UpdateUserHints -> HangmanEvent.UpdateHints(action.hintsCount)
            is HangmanAction.HintReward -> useHint(state, action.amount)
            HangmanAction.UpdateWord -> HangmanEvent.UpdateWord(nextRandomWord())
            HangmanAction.ShowDescription -> HangmanEvent.UpdateDescription
            HangmanAction.StartGame -> HangmanEvent.StartGame(nextRandomWord(), getUserHintsCountUseCase().first())
            HangmanAction.ReviveClicked -> HangmanEvent.ReviveLifeClicked
            HangmanAction.ReviveGranted -> HangmanEvent.ReviveLifeGranted
            HangmanAction.UseHint -> useHint(state, getUserHintsCountUseCase().first())
        }
    }

    override suspend fun handleEvent(event: HangmanEvent) = when (event) {
        is HangmanEvent.Won -> HangmanEffect.Won()
        is HangmanEvent.Lost -> HangmanEffect.Lost()
        is HangmanEvent.UpdateWord -> HangmanEffect.RestartGame()
        is HangmanEvent.OpenWord -> HangmanEffect.OpenWord(event.word)
        is HangmanEvent.StartGame -> HangmanEffect.StartGame()
        is HangmanEvent.ReviveLifeClicked -> HangmanEffect.ShowRewardedLifeAd()
        HangmanEvent.CannotUseHints -> HangmanEffect.ShowRewardedHintAd()
        else -> null
    }

    private suspend fun useHint(state: HangmanState, userHintsCount: Int): HangmanEvent {
        val guessedLetters = state.guessedLetters
        val wordSet = state.word.word.toSet()
        if (userHintsCount < 1 &&
            guessedLetters.letters.size < wordSet.size
        ) {
            return HangmanEvent.CannotUseHints
        }
        useUserHintsCountUseCase()
        val newUserHintsCount = getUserHintsCountUseCase().first()

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

        val newUserHintsCount = getUserHintsCountUseCase().first()

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

    private suspend fun nextRandomWord() = randomWordUseCase(
        GetRandomWordUseCase.Param(10)
    ).toUi()
}

fun HangmanGameViewModel.onLetterGuessed(letter: Char) =
    handleAction(HangmanAction.GuessLetter(letter))

fun HangmanGameViewModel.onUpdatePressed() = handleAction(HangmanAction.UpdateWord)
fun HangmanGameViewModel.onShowHintPressed() = handleAction(HangmanAction.ShowDescription)
fun HangmanGameViewModel.onOpenWordPressed(word: WordCombinedUi) =
    handleAction(HangmanAction.OpenWord(word))

fun HangmanGameViewModel.onRewardLifeGranted(amount: Int) = handleAction(HangmanAction.ReviveClicked)
fun HangmanGameViewModel.onRewardHintGranted(amount: Int) = handleAction(HangmanAction.HintReward(amount))
fun HangmanGameViewModel.onReviveClick() = handleAction(HangmanAction.ReviveClicked)
fun HangmanGameViewModel.useHint() = handleAction(HangmanAction.UseHint)
