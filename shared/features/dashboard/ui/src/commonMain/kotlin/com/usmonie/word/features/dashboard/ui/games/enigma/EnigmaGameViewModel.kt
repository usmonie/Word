package com.usmonie.word.features.dashboard.ui.games.enigma

import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.usecase.GetNextPhraseUseCase
import wtf.speech.core.ui.BaseViewModel

@Stable
class EnigmaGameViewModel(
    private val getNextPhraseUseCase: GetNextPhraseUseCase
) : BaseViewModel<EnigmaState, EnigmaAction, EnigmaEvent, EnigmaEffect>(EnigmaState.Loading) {


    init {
        handleAction(EnigmaAction.NextPhrase)
    }

    fun onLetterInput(letter: Char) = handleAction(EnigmaAction.InputLetter(letter))

    fun onCellSelected(
        cellPositionInWord: Int,
        wordPosition: Int
    ) = handleAction(EnigmaAction.CellSelected(cellPositionInWord, wordPosition))

    override fun EnigmaState.reduce(event: EnigmaEvent): EnigmaState {
        return when (event) {
            is EnigmaEvent.Correct -> {
                val currentSelectedCellPosition = currentSelectedCellPosition
                val nextPosition = if (currentSelectedCellPosition != null) {
                    getNextPosition(
                        event.cells,
                        currentSelectedCellPosition.first,
                        currentSelectedCellPosition.second + 1
                    )
                } else {
                    null
                }
                EnigmaState.Playing(phrase.copy(phrase = event.cells), lives, nextPosition)
            }

            EnigmaEvent.Incorrect -> if (lives > EnigmaState.MIN_LIVES_COUNT) {
                EnigmaState.Playing(phrase, lives - 1, currentSelectedCellPosition)
            } else {
                EnigmaState.Lost(phrase)
            }

            is EnigmaEvent.UpdateSelectedCell -> EnigmaState.Playing(
                phrase,
                lives,
                event.wordPosition to event.cellPositionInWord
            )

            EnigmaEvent.ClearSelectionCell -> EnigmaState.Playing(phrase, lives, null)
            is EnigmaEvent.NextPhrase -> EnigmaState.Playing(
                event.phrase,
                EnigmaState.MAX_LIVES_COUNT
            )
        }
    }

    private tailrec fun getNextPosition(
        cells: List<List<Cell>>,
        wordPosition: Int,
        nextCellPosition: Int
    ): Pair<Int, Int>? {
        val word = cells.getOrNull(wordPosition) ?: return null

        return when {
            word.lastIndex >= nextCellPosition -> {
                val cell = word[nextCellPosition]
                val cellState = cell.state

                if (cellState != CellState.Found && cellState != CellState.Correct && cell.isLetter) {
                    println("current = $cell")
                    wordPosition to nextCellPosition
                } else {
                    println("skipped = $cell")
                    getNextPosition(cells, wordPosition, nextCellPosition +1 )
                }
            }

            cells.lastIndex > wordPosition -> getNextPosition(cells, wordPosition + 1, 0)

            else -> null
        }
    }

    override suspend fun processAction(action: EnigmaAction): EnigmaEvent {
        return when (action) {
            is EnigmaAction.InputLetter -> {
                val state = state.value
                val phrase = state.phrase.phrase.toMutableList()
                val currentPosition = state.currentSelectedCellPosition
                if (currentPosition != null) {
                    val word = phrase[currentPosition.first].toMutableList()
                    val cell = word[currentPosition.second]
                    if (cell.letter.uppercaseChar() == action.letter) {
                        word[currentPosition.second] = cell.copy(state = CellState.Correct)
                    } else {
                        return EnigmaEvent.Incorrect
                    }

                    phrase[currentPosition.first] = word
                    EnigmaEvent.Correct(phrase)
                } else {
                    EnigmaEvent.ClearSelectionCell
                }
            }

            is EnigmaAction.CellSelected -> EnigmaEvent.UpdateSelectedCell(
                action.wordPosition, action.cellPositionInWord
            )

            EnigmaAction.NextPhrase -> {
                val phrase = getNextPhraseUseCase(Unit)
                EnigmaEvent.NextPhrase(encryptPhrase(phrase))
            }
        }
    }

    override suspend fun handleEvent(event: EnigmaEvent): EnigmaEffect? {
        return null
    }
}