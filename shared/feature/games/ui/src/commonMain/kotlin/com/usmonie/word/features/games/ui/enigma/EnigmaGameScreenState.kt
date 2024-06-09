package com.usmonie.word.features.games.ui.enigma

import androidx.compose.runtime.Immutable
import com.usmonie.compass.viewmodel.ScreenAction
import com.usmonie.compass.viewmodel.ScreenEffect
import com.usmonie.compass.viewmodel.ScreenEvent
import com.usmonie.compass.viewmodel.ScreenState
import com.usmonie.word.features.games.ui.hangman.GuessedLetters
import com.usmonie.word.features.qutoes.domain.models.Quote

sealed class EnigmaState(
    open val phrase: EnigmaEncryptedPhrase,
    open val lives: Int,
    open val currentSelectedCellPosition: Pair<Int, Int>? = null,
    open val foundLetters: Set<Char> = setOf(),
    open val hintsCount: Int,
    open val guessedLetters: GuessedLetters
) : ScreenState {
    val maxLives: Int = MAX_LIVES_COUNT

    @Immutable
    class Loading : EnigmaState(
        EnigmaEncryptedPhrase(listOf(), Quote("", "", "", emptyList(), false, false), 0, mapOf()),
        MAX_LIVES_COUNT,
        hintsCount = 0,
        guessedLetters = GuessedLetters(setOf())
    )

    sealed class Game(
        override val phrase: EnigmaEncryptedPhrase,
        override val lives: Int,
        override val currentSelectedCellPosition: Pair<Int, Int>? = null,
        override val foundLetters: Set<Char> = setOf(),
        override val hintsCount: Int,
        override val guessedLetters: GuessedLetters
    ) : EnigmaState(
        phrase,
        lives,
        currentSelectedCellPosition,
        foundLetters,
        hintsCount,
        guessedLetters
    ) {

        @Immutable
        data class Playing(
            override val phrase: EnigmaEncryptedPhrase,
            override val lives: Int,
            override val currentSelectedCellPosition: Pair<Int, Int>? = null,
            override val foundLetters: Set<Char> = setOf(),
            override val hintsCount: Int,
            override val guessedLetters: GuessedLetters
        ) : Game(
            phrase,
            lives,
            currentSelectedCellPosition,
            foundLetters,
            hintsCount,
            guessedLetters
        )

        @Immutable
        data class HintSelection(
            override val phrase: EnigmaEncryptedPhrase,
            override val lives: Int,
            override val foundLetters: Set<Char> = setOf(),
            override val hintsCount: Int,
            override val guessedLetters: GuessedLetters
        ) : Game(
            phrase,
            lives,
            null,
            foundLetters,
            hintsCount,
            guessedLetters
        )
    }

    @Immutable
    data class Lost(
        override val phrase: EnigmaEncryptedPhrase,
        override val foundLetters: Set<Char> = setOf(),
        override val hintsCount: Int,
        override val guessedLetters: GuessedLetters
    ) : EnigmaState(phrase, MIN_LIVES_COUNT, null, foundLetters, hintsCount, guessedLetters)

    @Immutable
    data class Won(
        override val phrase: EnigmaEncryptedPhrase,
        override val lives: Int,
        override val hintsCount: Int,
        override val guessedLetters: GuessedLetters
    ) : EnigmaState(phrase, lives, null, hintsCount = hintsCount, guessedLetters = guessedLetters)

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

    data class UpdateUserHints(val userHintsCount: Int) : EnigmaAction()
    data class AddHints(val count: Int) : EnigmaAction()

    data object NextPhrase : EnigmaAction()
    data object ReviveClicked : EnigmaAction()
    data object ReviveGranted : EnigmaAction()
    data object UseHint : EnigmaAction()
    data object HintGranted : EnigmaAction()
    data object AdTime : EnigmaAction()
    data object Won : EnigmaAction()
}

sealed class EnigmaEvent : ScreenEvent {
    data object Won : EnigmaEvent()
    data class Correct(val phrase: EnigmaEncryptedPhrase) : EnigmaEvent()
    data object Incorrect : EnigmaEvent()
    data object Lost : EnigmaEvent()
    data class UpdateSelectedCell(
        val wordPosition: Int,
        val cellPositionInWord: Int
    ) : EnigmaEvent()

    data class UpdateHints(val hintsCount: Int) : EnigmaEvent()
    data object ClearSelectionCell : EnigmaEvent()
    data class NextPhrase(val phrase: EnigmaEncryptedPhrase, val hintsCount: Int) : EnigmaEvent()
    data class UpdateCurrentPhrase(val phrase: EnigmaEncryptedPhrase, val hintsCount: Int) :
        EnigmaEvent()

    data object ReviveClicked : EnigmaEvent()
    data object ReviveGranted : EnigmaEvent()
    data object UseHint : EnigmaEvent()
    data object NoHints : EnigmaEvent()
    data object ShowMiddleGameAd : EnigmaEvent()
}

sealed class EnigmaEffect : ScreenEffect {
    class ShowMiddleGameAd : EnigmaEffect()

    class ShowRewardedLifeAd : EnigmaEffect()
    class ShowRewardedHintAd : EnigmaEffect()
    class CellSelected : EnigmaEffect()

    sealed class InputEffect : EnigmaEffect() {
        class Incorrect : InputEffect()
        class Correct : InputEffect()
    }
}
