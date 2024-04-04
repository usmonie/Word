package com.usmonie.word.features.dashboard.ui.games.enigma

import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState

sealed class EnigmaState(
    open val phrase: EnigmaEncryptedPhrase,
    open val lives: Int,
    open val currentSelectedCellPosition: Pair<Int, Int>? = null,
    open val foundLetters: Set<Char> = setOf()
) : ScreenState {
    data object Loading : EnigmaState(EnigmaEncryptedPhrase(listOf(), 0), MAX_LIVES_COUNT)
    data class Playing(
        override val phrase: EnigmaEncryptedPhrase,
        override val lives: Int,
        override val currentSelectedCellPosition: Pair<Int, Int>? = null,
        override val foundLetters: Set<Char> = setOf()
    ) : EnigmaState(phrase, lives, currentSelectedCellPosition, foundLetters)

    data class Lost(
        override val phrase: EnigmaEncryptedPhrase,
    ) : EnigmaState(phrase, MIN_LIVES_COUNT, null)

    data class Won(
        override val phrase: EnigmaEncryptedPhrase,
        override val lives: Int,
    ) : EnigmaState(phrase, lives, null)

    companion object {
        const val MAX_LIVES_COUNT = 3
        const val MIN_LIVES_COUNT = 0
    }
}

sealed class EnigmaAction : ScreenAction {
    data class InputLetter(
        val letter: Char,
    ) : EnigmaAction()

    data class CellSelected(
        val cellPositionInWord: Int,
        val wordPosition: Int
    ) : EnigmaAction()

    data object NextPhrase : EnigmaAction()
}

sealed class EnigmaEvent : ScreenEvent {
    data class Correct(val cells: List<List<Cell>>) : EnigmaEvent()
    data object Incorrect : EnigmaEvent()
    data class UpdateSelectedCell(
        val wordPosition: Int,
        val cellPositionInWord: Int
    ) : EnigmaEvent()

    data object ClearSelectionCell : EnigmaEvent()

    data class NextPhrase(val phrase: EnigmaEncryptedPhrase) : EnigmaEvent()
}

sealed class EnigmaEffect : ScreenEffect
