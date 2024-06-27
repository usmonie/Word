package com.usmonie.word.features.games.ui.enigma

import com.usmonie.compass.viewmodel.ActionProcessor
import com.usmonie.core.domain.usecases.invoke
import com.usmonie.word.core.analytics.Analytics
import com.usmonie.word.features.games.domain.usecases.GetCryptogramQuoteUseCase
import com.usmonie.word.features.games.ui.enigma.EnigmaState.Companion.MIN_LIVES_COUNT
import com.usmonie.word.features.settings.domain.usecase.AddUserHintsCountUseCase
import com.usmonie.word.features.settings.domain.usecase.GetUserHintsCountUseCase
import com.usmonie.word.features.settings.domain.usecase.UseUserHintsCountUseCase
import kotlinx.coroutines.flow.first

class EnigmaActionProcessor(
	private val analytics: Analytics,
	private val getNextPhraseUseCase: GetCryptogramQuoteUseCase,
	private val getUserHintsCountUseCase: GetUserHintsCountUseCase,
	private val useUserHintsCountUseCase: UseUserHintsCountUseCase,
	private val addUserHintsCountUseCase: AddUserHintsCountUseCase
) : ActionProcessor<EnigmaAction, EnigmaState, EnigmaEvent> {
	override suspend fun process(action: EnigmaAction, state: EnigmaState): EnigmaEvent = when (action) {
		is EnigmaAction.UpdateUserHints -> EnigmaEvent.UpdateHints(action.userHintsCount)
		is EnigmaAction.InputLetter -> inputLetter(action, state)
		is EnigmaAction.CellSelected -> handleCellSelected(action, state)
		EnigmaAction.NextPhrase -> handleNextPhrase()
		EnigmaAction.ReviveClicked -> EnigmaEvent.ReviveClicked
		EnigmaAction.ReviveGranted -> EnigmaEvent.ReviveGranted
		EnigmaAction.UseHint -> handleUseHint(state)
		EnigmaAction.HintGranted -> handleHintGranted()
		is EnigmaAction.AddHints -> handleAddHints(action)
		EnigmaAction.AdTime -> EnigmaEvent.ShowMiddleGameAd
		EnigmaAction.Won -> EnigmaEvent.Won
	}

	private suspend fun handleCellSelected(action: EnigmaAction.CellSelected, state: EnigmaState): EnigmaEvent {
		return if (state is EnigmaState.Game.HintSelection) {
			val newPhrase = findLetter(action.cellPositionInWord, action.wordPosition, state.phrase)
			val currentHintsCount = getUserHintsCountUseCase().first()
			EnigmaEvent.UpdateCurrentPhrase(newPhrase, currentHintsCount)
		} else {
			EnigmaEvent.UpdateSelectedCell(
				action.letterIndex,
				action.wordPosition,
				action.cellPositionInWord
			)
		}
	}

	private suspend fun handleNextPhrase(): EnigmaEvent {
		val phrase = getNextPhraseUseCase(GetCryptogramQuoteUseCase.Param())
		val hintsCount = getUserHintsCountUseCase().first()
		return EnigmaEvent.NextPhrase(phrase.map(), hintsCount)
	}

	private fun handleUseHint(state: EnigmaState): EnigmaEvent {
		return if (state.hintsCount > 0) EnigmaEvent.UseHint else EnigmaEvent.NoHints
	}

	private suspend fun handleHintGranted(): EnigmaEvent {
		addUserHintsCountUseCase(1)
		val updatedHintsCount = getUserHintsCountUseCase().first()
		return EnigmaEvent.UpdateHints(updatedHintsCount)
	}

	private suspend fun handleAddHints(action: EnigmaAction.AddHints): EnigmaEvent {
		addUserHintsCountUseCase(action.count)
		val updatedHintsCount = getUserHintsCountUseCase().first()
		return EnigmaEvent.UpdateHints(updatedHintsCount)
	}


	private fun inputLetter(action: EnigmaAction.InputLetter, state: EnigmaState): EnigmaEvent {
		val currentPosition = state.currentSelectedCellPosition ?: return EnigmaEvent.ClearSelectionCell

		val encryptedPhrase = state.phrase
		val phrase = encryptedPhrase.encryptedPhrase
		val phrasePositions = encryptedPhrase.encryptedPositions
		val currentPositionIndex = phrasePositions.indexOf(currentPosition)
		val nextPosition = if (currentPositionIndex == phrasePositions.lastIndex) {
			phrasePositions.first()
		} else {
			phrasePositions[currentPositionIndex + 1]
		}

		val word = phrase[currentPosition.second].cells
		val cell = word[currentPosition.third]
		return when {
			cell.symbol.equals(action.letter, true) -> {
				phrasePositions.remove(currentPosition)
				cell.state = CellState.Correct
				EnigmaEvent.Correct(state.phrase, nextPosition)
			}

			state.lives > MIN_LIVES_COUNT -> EnigmaEvent.Incorrect
			else -> EnigmaEvent.Lost
		}
	}

	private suspend fun findLetter(
		cellPositionInWord: Int,
		wordPosition: Int,
		phrase: EnigmaEncryptedPhrase
	): EnigmaEncryptedPhrase {
		val encryptedPositions = phrase.encryptedPositions
		val charsCount = phrase.charsCount
		val word = phrase.encryptedPhrase[wordPosition]
		val newCell = word.cells[cellPositionInWord].apply { state = CellState.Correct }

		val newWord = word.copy(cells = word.cells.apply { set(cellPositionInWord, newCell) })
		charsCount[newCell.symbol] = charsCount.getOrPut(newCell.symbol) { 1 } - 1

		val newPhrase = phrase.encryptedPhrase.apply { set(wordPosition, newWord) }
		encryptedPositions.remove(Triple(newCell.number, wordPosition, cellPositionInWord))
		useUserHintsCountUseCase()

		return EnigmaEncryptedPhrase(
			newPhrase,
			phrase.encryptedPositions,
			phrase.quote,
			charsCount
		)
	}
}