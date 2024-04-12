package com.usmonie.word.features.dashboard.ui.games.enigma

import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.usecase.AddUserHintsCountUseCase
import com.usmonie.word.features.dashboard.domain.usecase.GetNextPhraseUseCase
import com.usmonie.word.features.dashboard.domain.usecase.GetUserHintsCountUseCase
import com.usmonie.word.features.dashboard.domain.usecase.UseUserHintsCountUseCase
import com.usmonie.word.features.dashboard.ui.games.enigma.EnigmaState.Companion.MIN_LIVES_COUNT
import kotlin.time.Duration.Companion.minutes
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import wtf.speech.core.ui.BaseViewModel
import wtf.word.core.domain.tools.fastMap

@Stable
class EnigmaGameViewModel(
    private val getNextPhraseUseCase: GetNextPhraseUseCase,
    private val getUserHintsCountUseCase: GetUserHintsCountUseCase,
    private val useUserHintsCountUseCase: UseUserHintsCountUseCase,
    private val addUserHintsCountUseCase: AddUserHintsCountUseCase
) : BaseViewModel<EnigmaState, EnigmaAction, EnigmaEvent, EnigmaEffect>(EnigmaState.Loading) {

    private val timer = flow {
        while (currentCoroutineContext().isActive) {
            emit(Unit)
            delay(2.minutes)
        }
    }

    init {
        handleAction(EnigmaAction.NextPhrase)
        viewModelScope.launchSafe {
            timer.collect {
                handleAction(EnigmaAction.AdTime)
            }
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
                EnigmaState.Playing(
                    event.phrase,
                    lives,
                    nextPosition,
                    hintsCount = hintsCount,
                    guessedLetters = guessedLetters
                )
            }

            EnigmaEvent.Incorrect -> if (lives > MIN_LIVES_COUNT + 1) {
                EnigmaState.Playing(
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

                EnigmaState.Playing(
                    phrase,
                    lives,
                    event.wordPosition to event.cellPositionInWord,
                    hintsCount = hintsCount,
                    guessedLetters = guessedLetters
                )
            }

            EnigmaEvent.ClearSelectionCell -> EnigmaState.Playing(
                phrase,
                lives,
                hintsCount = hintsCount,
                guessedLetters = guessedLetters
            )

            is EnigmaEvent.NextPhrase -> EnigmaState.Playing(
                event.phrase,
                EnigmaState.MAX_LIVES_COUNT,
                hintsCount = event.hintsCount,
                guessedLetters = guessedLetters
            )

            EnigmaEvent.ReviveGranted -> EnigmaState.Playing(
                phrase,
                lives + 1,
                currentSelectedCellPosition,
                foundLetters,
                hintsCount,
                guessedLetters = guessedLetters
            )

            is EnigmaEvent.UseHint -> EnigmaState.Playing(
                event.phrase,
                lives,
                currentSelectedCellPosition,
                foundLetters,
                event.hintsLeft,
                guessedLetters = guessedLetters
            )

            is EnigmaEvent.Won -> EnigmaState.Won(phrase, lives, hintsCount, guessedLetters)

            else -> this
        }
    }

    override suspend fun processAction(action: EnigmaAction) = when (action) {
        is EnigmaAction.InputLetter -> inputLetter(action)

        is EnigmaAction.CellSelected -> EnigmaEvent.UpdateSelectedCell(
            action.wordPosition, action.cellPositionInWord
        )

        EnigmaAction.NextPhrase -> {
            val phrase = getNextPhraseUseCase(Unit)
            val hintsCount = getUserHintsCountUseCase(Unit)
            EnigmaEvent.NextPhrase(encryptPhrase(phrase, "Joe Liberman"), hintsCount)
        }

        EnigmaAction.ReviveClicked -> EnigmaEvent.ReviveClicked
        EnigmaAction.ReviveGranted -> EnigmaEvent.ReviveGranted
        EnigmaAction.UseHint -> {
            val state = state.value
            if (state.hintsCount > 0) {
                val newPhrase = findRandomLetter(state.phrase)
                val hintsLeft = getUserHintsCountUseCase(Unit)
                EnigmaEvent.UseHint(newPhrase, hintsLeft)
            } else {
                EnigmaEvent.NoHints
            }
        }

        is EnigmaAction.AddHints -> {
            addUserHintsCountUseCase(action.count)
            val updatedHintsCount = getUserHintsCountUseCase(Unit)
            EnigmaEvent.UpdateHints(updatedHintsCount)
        }

        EnigmaAction.AdTime -> EnigmaEvent.ShowMiddleGameAd
        EnigmaAction.Won -> EnigmaEvent.Won
    }

    private fun inputLetter(action: EnigmaAction.InputLetter): EnigmaEvent {
        val state = state.value
        val phrase = state.phrase.encryptedPhrase.toMutableList()
        val currentPosition = state.currentSelectedCellPosition
        var encryptedPositionsCount = state.phrase.encryptedPositionsCount
        return if (currentPosition != null) {
            val word = phrase[currentPosition.first].cells.toMutableList()
            val cell = word[currentPosition.second]
            if (cell.letter.uppercaseChar() == action.letter) {
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

    private suspend fun findRandomLetter(phrase: EnigmaEncryptedPhrase): EnigmaEncryptedPhrase {
        val randomCell = randomCell(phrase.encryptedPhrase) ?: return phrase
        var encryptedPositionsCount = phrase.encryptedPositionsCount
        val charsCount = phrase.charsCount.toMutableMap()
        val newPhrase = phrase.encryptedPhrase.fastMap { word ->
            word.copy(
                cells = word.cells.fastMap { cell ->
                    val letter = cell.letter.uppercaseChar()
                    if (letter == randomCell.letter.uppercaseChar()) {
                        encryptedPositionsCount--
                        charsCount[letter] = charsCount.getOrPut(letter) { 1 } - 1
                        cell.copy(state = CellState.Found)
                    } else {
                        cell
                    }
                }
            )
        }

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

    private fun randomCell(phrase: List<Word>): Cell? {
        val phraseWithEmptyStatesOnly = phrase
            .asSequence()
            .map { word -> Word(word.cells.filter { cell -> cell.state == CellState.Empty }) }
            .filter { word -> word.cells.isNotEmpty() }
            .toList()

        val randomWord = phraseWithEmptyStatesOnly.randomOrNull()?.cells ?: return null

        val randomCell = randomWord.randomOrNull() ?: randomCell(phraseWithEmptyStatesOnly)

        return randomCell
    }

    override suspend fun handleEvent(event: EnigmaEvent) = when (event) {
        is EnigmaEvent.Incorrect -> EnigmaEffect.InputEffect.Incorrect()
        is EnigmaEvent.Lost -> EnigmaEffect.ShowMiddleGameAd()
        is EnigmaEvent.ReviveClicked -> EnigmaEffect.ShowRewardedAd()
        is EnigmaEvent.Correct -> {
            println("Encrypted ${event.phrase.encryptedPositionsCount}")
            if (event.phrase.encryptedPositionsCount == 0) {
                handleAction(EnigmaAction.Won)
            }
            EnigmaEffect.InputEffect.Correct()
        }


        is EnigmaEvent.ShowMiddleGameAd -> EnigmaEffect.ShowMiddleGameAd()
        else -> null
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