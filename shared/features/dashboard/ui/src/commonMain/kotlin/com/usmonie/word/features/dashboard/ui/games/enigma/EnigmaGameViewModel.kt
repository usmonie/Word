package com.usmonie.word.features.dashboard.ui.games.enigma

import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.usecase.AddUserHintsCountUseCase
import com.usmonie.word.features.dashboard.domain.usecase.GetNextPhraseUseCase
import com.usmonie.word.features.dashboard.domain.usecase.GetUserHintsCountUseCase
import com.usmonie.word.features.dashboard.domain.usecase.UseUserHintsCountUseCase
import com.usmonie.word.features.dashboard.ui.games.enigma.EnigmaState.Companion.MIN_LIVES_COUNT
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import wtf.speech.core.ui.BaseViewModel
import wtf.word.core.domain.Analytics
import kotlin.time.Duration.Companion.minutes

@Stable
class EnigmaGameViewModel(
    private val analytics: Analytics,
    private val getNextPhraseUseCase: GetNextPhraseUseCase,
    private val getUserHintsCountUseCase: GetUserHintsCountUseCase,
    private val useUserHintsCountUseCase: UseUserHintsCountUseCase,
    private val addUserHintsCountUseCase: AddUserHintsCountUseCase
) : BaseViewModel<EnigmaState, EnigmaAction, EnigmaEvent, EnigmaEffect>(EnigmaState.Loading()) {

    private val timer = flow {
        while (currentCoroutineContext().isActive) {
            emit(Unit)
            delay(4.minutes)
        }
    }

    init {
        handleAction(EnigmaAction.NextPhrase)
        viewModelScope.launchSafe {
            timer.collect { handleAction(EnigmaAction.AdTime) }
        }
    }

    override fun EnigmaState.reduce(event: EnigmaEvent): EnigmaState {
        return when (event) {
            is EnigmaEvent.Correct -> {
                val currentSelectedCellPosition = currentSelectedCellPosition
                val nextPosition = if (currentSelectedCellPosition != null) {
                    getNextPosition(
                        event.phrase.encryptedPhrase,
                        currentSelectedCellPosition.first,
                        currentSelectedCellPosition.second + 1
                    )
                } else {
                    null
                }
                EnigmaState.Game.Playing(
                    event.phrase,
                    lives,
                    nextPosition,
                    hintsCount = hintsCount,
                    guessedLetters = guessedLetters
                )
            }

            EnigmaEvent.Incorrect -> if (lives > MIN_LIVES_COUNT + 1) {
                EnigmaState.Game.Playing(
                    phrase,
                    lives - 1,
                    currentSelectedCellPosition,
                    hintsCount = hintsCount,
                    guessedLetters = guessedLetters,
                )
            } else {
                EnigmaState.Lost(phrase, hintsCount = hintsCount, guessedLetters = guessedLetters)
            }

            EnigmaEvent.Lost -> EnigmaState.Lost(
                phrase,
                foundLetters,
                hintsCount = hintsCount,
                guessedLetters
            )

            is EnigmaEvent.UpdateSelectedCell -> {
                if (this is EnigmaState.Lost) return this

                EnigmaState.Game.Playing(
                    phrase,
                    lives,
                    event.wordPosition to event.cellPositionInWord,
                    hintsCount = hintsCount,
                    guessedLetters = guessedLetters
                )
            }

            EnigmaEvent.ClearSelectionCell -> EnigmaState.Game.Playing(
                phrase,
                lives,
                hintsCount = hintsCount,
                guessedLetters = guessedLetters
            )

            is EnigmaEvent.NextPhrase -> EnigmaState.Game.Playing(
                event.phrase,
                EnigmaState.MAX_LIVES_COUNT,
                currentSelectedCellPosition = getNextPosition(
                    event.phrase.encryptedPhrase,
                    0,
                    0
                ),
                hintsCount = event.hintsCount,
                guessedLetters = guessedLetters
            )

            EnigmaEvent.ReviveGranted -> EnigmaState.Game.Playing(
                phrase,
                lives + 1,
                currentSelectedCellPosition,
                foundLetters,
                hintsCount,
                guessedLetters
            )

            is EnigmaEvent.UseHint -> if (this is EnigmaState.Game.HintSelection) {
                EnigmaState.Game.Playing(
                    phrase,
                    lives,
                    getNextPosition(phrase.encryptedPhrase, 0, 0),
                    foundLetters,
                    hintsCount,
                    guessedLetters
                )
            } else {
                EnigmaState.Game.HintSelection(
                    phrase,
                    lives,
                    foundLetters,
                    hintsCount,
                    guessedLetters
                )
            }

            is EnigmaEvent.Won -> EnigmaState.Won(phrase, lives, hintsCount, guessedLetters)

            is EnigmaEvent.UpdateCurrentPhrase -> EnigmaState.Game.Playing(
                event.phrase,
                lives,
                getNextPosition(event.phrase.encryptedPhrase, 0, 0),
                foundLetters,
                event.hintsCount,
                guessedLetters
            )

            else -> this
        }
    }

    override suspend fun processAction(action: EnigmaAction) = when (action) {
        is EnigmaAction.InputLetter -> {
            inputLetter(action)
        }

        is EnigmaAction.CellSelected -> {
            if (state.value is EnigmaState.Game.HintSelection) {
                val newPhrase =
                    findLetter(action.cellPositionInWord, action.wordPosition, state.value.phrase)
                val currentHintsCount = getUserHintsCountUseCase(Unit)
                EnigmaEvent.UpdateCurrentPhrase(newPhrase, currentHintsCount)
            } else {
                EnigmaEvent.UpdateSelectedCell(
                    action.wordPosition, action.cellPositionInWord
                )
            }
        }

        EnigmaAction.NextPhrase -> {
            val phrase = getNextPhraseUseCase(Unit)
            val hintsCount = getUserHintsCountUseCase(Unit)
            EnigmaEvent.NextPhrase(encryptPhrase(phrase, "Joe Liberman"), hintsCount)
        }

        EnigmaAction.ReviveClicked -> EnigmaEvent.ReviveClicked
        EnigmaAction.ReviveGranted -> EnigmaEvent.ReviveGranted
        EnigmaAction.UseHint -> {
            val state = state.value
            if (state.hintsCount > 0) EnigmaEvent.UseHint else EnigmaEvent.NoHints
        }

        is EnigmaAction.AddHints -> {
            addUserHintsCountUseCase(action.count)
            val updatedHintsCount = getUserHintsCountUseCase(Unit)
            EnigmaEvent.UpdateHints(updatedHintsCount)
        }

        EnigmaAction.AdTime -> EnigmaEvent.ShowMiddleGameAd
        EnigmaAction.Won -> EnigmaEvent.Won
    }


    override suspend fun handleEvent(event: EnigmaEvent) = when (event) {
        is EnigmaEvent.Incorrect -> EnigmaEffect.InputEffect.Incorrect()
        is EnigmaEvent.Lost -> EnigmaEffect.ShowMiddleGameAd()
        is EnigmaEvent.NextPhrase -> EnigmaEffect.ShowMiddleGameAd()
        is EnigmaEvent.ReviveClicked -> EnigmaEffect.ShowRewardedAd()
        is EnigmaEvent.Correct -> {
            if (event.phrase.encryptedPositionsCount < 1) {
                handleAction(EnigmaAction.Won)
            }
            EnigmaEffect.InputEffect.Correct()
        }

        is EnigmaEvent.UpdateCurrentPhrase -> {
            if (event.phrase.encryptedPositionsCount < 1) {
                handleAction(EnigmaAction.Won)
            }
            EnigmaEffect.InputEffect.Correct()
        }


        is EnigmaEvent.ShowMiddleGameAd -> EnigmaEffect.ShowMiddleGameAd()
        else -> null
    }

    private fun inputLetter(action: EnigmaAction.InputLetter): EnigmaEvent {
        val state = state.value
        val phrase = state.phrase.encryptedPhrase.toMutableList()
        val currentPosition = state.currentSelectedCellPosition
        var encryptedPositionsCount = state.phrase.encryptedPositionsCount
        return if (currentPosition != null) {
            val word = phrase[currentPosition.first].cells.toMutableList()
            val cell = word[currentPosition.second]
            if (cell.symbol.uppercaseChar() == action.letter) {
                encryptedPositionsCount -= 1
                word[currentPosition.second] = cell.copy(state = CellState.Correct)
            } else if (state.lives > MIN_LIVES_COUNT) {
                return EnigmaEvent.Incorrect
            } else {
                return EnigmaEvent.Lost
            }

            phrase[currentPosition.first] = Word(word)
            EnigmaEvent.Correct(
                state.phrase.copy(
                    encryptedPhrase = phrase,
                    encryptedPositionsCount = encryptedPositionsCount
                )
            )
        } else {
            EnigmaEvent.ClearSelectionCell
        }
    }

    private suspend fun findLetter(
        cellPositionInWord: Int,
        wordPosition: Int,
        phrase: EnigmaEncryptedPhrase
    ): EnigmaEncryptedPhrase {
        var encryptedPositionsCount = phrase.encryptedPositionsCount
        val charsCount = phrase.charsCount.toMutableMap()
        val word = phrase.encryptedPhrase[wordPosition]
        val newCell =
            word.cells[cellPositionInWord].copy(state = CellState.Correct)

        val newWord =
            word.copy(cells = word.cells.toMutableList().apply { set(cellPositionInWord, newCell) })
        charsCount[newCell.symbol] = charsCount.getOrPut(newCell.symbol) { 1 } - 1

        val newPhrase = phrase.encryptedPhrase.toMutableList().apply { set(wordPosition, newWord) }
        encryptedPositionsCount--
        if (encryptedPositionsCount < phrase.encryptedPositionsCount) {
            useUserHintsCountUseCase(Unit)
        }
        return EnigmaEncryptedPhrase(
            newPhrase,
            phrase.phrase,
            phrase.author,
            encryptedPositionsCount,
            charsCount
        )
    }

    fun onLetterInput(letter: Char) = handleAction(EnigmaAction.InputLetter(letter))
    fun onNextPhrase() = handleAction(EnigmaAction.NextPhrase)
    fun onAddLifeClick() = handleAction(EnigmaAction.ReviveClicked)
    fun onReviveGranted(amount: Int) = handleAction(EnigmaAction.ReviveGranted)
    fun useHint() = handleAction(EnigmaAction.UseHint)
    fun onCellSelected(
        cellPositionInWord: Int,
        wordPosition: Int
    ) = handleAction(EnigmaAction.CellSelected(cellPositionInWord, wordPosition))

    private tailrec fun getNextPosition(
        cells: List<Word>,
        wordPosition: Int,
        nextCellPosition: Int
    ): Pair<Int, Int>? {
        val word = cells.getOrNull(wordPosition) ?: return null

        return when {
            word.cells.lastIndex >= nextCellPosition -> {
                val cell = word.cells[nextCellPosition]
                val cellState = cell.state

                if (cellState != CellState.Found && cellState != CellState.Correct && cell.isLetter) {
                    wordPosition to nextCellPosition
                } else {
                    getNextPosition(cells, wordPosition, nextCellPosition + 1)
                }
            }

            cells.lastIndex > wordPosition -> getNextPosition(
                cells,
                wordPosition + 1,
                0
            )

            else -> null
        }
    }
}