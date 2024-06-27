package com.usmonie.word.features.games.ui.enigma

import androidx.collection.ObjectList
import com.usmonie.compass.viewmodel.StateManager
import com.usmonie.word.features.games.ui.enigma.EnigmaState.Companion.MIN_LIVES_COUNT

class EnigmaStateManager : StateManager<EnigmaEvent, EnigmaState> {
	override fun reduce(state: EnigmaState, event: EnigmaEvent) = when (event) {
		is EnigmaEvent.Correct -> state.handleCorrectEvent(event)
		is EnigmaEvent.Incorrect -> state.handleIncorrectEvent()
		is EnigmaEvent.Lost -> EnigmaState.Lost(
			state.phrase,
			state.foundLetters,
			state.hintsCount,
			state.guessedLetters
		)

		is EnigmaEvent.UpdateSelectedCell -> state.handleUpdateSelectedCellEvent(event)
		is EnigmaEvent.ClearSelectionCell -> state.handleClearSelectionCellEvent()
		is EnigmaEvent.UpdateHints -> state.handleUpdateHintsEvent(event)
		is EnigmaEvent.NextPhrase -> state.handleNextPhraseEvent(event)
		is EnigmaEvent.ReviveGranted -> state.handleReviveGrantedEvent()
		is EnigmaEvent.UseHint -> state.handleUseHintEvent()
		is EnigmaEvent.Won -> EnigmaState.Won(state.phrase, state.lives, state.hintsCount, state.guessedLetters)
		is EnigmaEvent.UpdateCurrentPhrase -> state.handleUpdateCurrentPhraseEvent(event)
		else -> state
	}

	private fun EnigmaState.handleCorrectEvent(event: EnigmaEvent.Correct): EnigmaState {
		val nextPosition = event.selectedPosition
		return EnigmaState.Game.Playing(
			event.phrase,
			lives,
			nextPosition,
			hintsCount = hintsCount,
			guessedLetters = guessedLetters
		)
	}

	private fun EnigmaState.handleIncorrectEvent(): EnigmaState {
		return if (lives > MIN_LIVES_COUNT + 1) {
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
	}

	private fun EnigmaState.handleUpdateSelectedCellEvent(event: EnigmaEvent.UpdateSelectedCell): EnigmaState {
		if (this is EnigmaState.Lost) return this

		return EnigmaState.Game.Playing(
			phrase,
			lives,
			Triple(event.index, event.wordPosition, event.cellPositionInWord),
			hintsCount = hintsCount,
			guessedLetters = guessedLetters
		)
	}

	private fun EnigmaState.handleClearSelectionCellEvent(): EnigmaState {
		return EnigmaState.Game.Playing(
			phrase,
			lives,
			hintsCount = hintsCount,
			guessedLetters = guessedLetters
		)
	}

	private fun EnigmaState.handleUpdateHintsEvent(event: EnigmaEvent.UpdateHints): EnigmaState {
		return when (this) {
			is EnigmaState.Game.Playing -> copy(hintsCount = event.hintsCount)
			else -> this
		}
	}

	private fun EnigmaState.handleNextPhraseEvent(event: EnigmaEvent.NextPhrase): EnigmaState {
		return EnigmaState.Game.Playing(
			event.phrase,
			EnigmaState.MAX_LIVES_COUNT,
			currentSelectedCellPosition = getNextPosition(
				event.phrase.encryptedPositions,
				Triple(0, 0, 0)
			),
			hintsCount = event.hintsCount,
			guessedLetters = guessedLetters
		)
	}

	private fun EnigmaState.handleReviveGrantedEvent(): EnigmaState {
		return EnigmaState.Game.Playing(
			phrase,
			lives + 1,
			currentSelectedCellPosition,
			foundLetters,
			hintsCount,
			guessedLetters
		)
	}

	private fun EnigmaState.handleUseHintEvent(): EnigmaState {
		return if (this is EnigmaState.Game.HintSelection) {
			EnigmaState.Game.Playing(
				phrase,
				lives,
				getNextPosition(phrase.encryptedPositions, Triple(0, -1, -1)),
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
	}

	private fun EnigmaState.handleUpdateCurrentPhraseEvent(event: EnigmaEvent.UpdateCurrentPhrase): EnigmaState {
		return EnigmaState.Game.Playing(
			event.phrase,
			lives,
			getNextPosition(phrase.encryptedPositions, Triple(0, -1, -1)),
			foundLetters,
			event.hintsCount,
			guessedLetters
		)
	}

	private fun getNextPosition(
		phrasePositions: ObjectList<Triple<Int, Int, Int>>,
		currentPosition: Triple<Int, Int, Int>,
	): Triple<Int, Int, Int> {
		val currentPositionIndex = phrasePositions.indexOfFirst {
			it.second == currentPosition.second && it.third == currentPosition.third
		}

		return if (currentPositionIndex == -1 || currentPositionIndex == phrasePositions.lastIndex) {
			phrasePositions.first()
		} else {
			phrasePositions[currentPositionIndex + 1]
		}
	}
}