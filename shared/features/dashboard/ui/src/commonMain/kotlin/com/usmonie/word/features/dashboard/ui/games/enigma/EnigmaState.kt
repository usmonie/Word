package com.usmonie.word.features.dashboard.ui.games.enigma

import androidx.compose.runtime.Immutable
import com.usmonie.word.features.dashboard.ui.games.hangman.GuessedLetters
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState


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
        EnigmaEncryptedPhrase(listOf(), "", "", 0, mapOf()),
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

    data class AddHints(val count: Int) : EnigmaAction()

    data object NextPhrase : EnigmaAction()
    data object ReviveClicked : EnigmaAction()
    data object ReviveGranted : EnigmaAction()
    data object UseHint : EnigmaAction()
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

    data object ClearSelectionCell : EnigmaEvent()
    data class NextPhrase(val phrase: EnigmaEncryptedPhrase, val hintsCount: Int) : EnigmaEvent()
    data object ReviveClicked : EnigmaEvent()
    data object ReviveGranted : EnigmaEvent()
    data class UseHint(val phrase: EnigmaEncryptedPhrase, val hintsLeft: Int) : EnigmaEvent()
    data class UpdateHints(val hintsCount: Int) : EnigmaEvent()
    data object NoHints : EnigmaEvent()
    data object ShowMiddleGameAd : EnigmaEvent()
}

@Suppress("CanSealedSubClassBeObject")
sealed class EnigmaEffect : ScreenEffect {
    class ShowMiddleGameAd : EnigmaEffect()

    class ShowRewardedAd : EnigmaEffect()

    sealed class InputEffect : EnigmaEffect() {
        class Incorrect : InputEffect()
        class Correct : InputEffect()
    }
}
