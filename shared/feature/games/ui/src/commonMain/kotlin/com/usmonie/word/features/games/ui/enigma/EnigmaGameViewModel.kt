package com.usmonie.word.features.games.ui.enigma

import androidx.collection.ObjectList
import androidx.compose.runtime.Immutable
import com.usmonie.compass.viewmodel.StateViewModel
import com.usmonie.core.domain.usecases.invoke
import com.usmonie.word.core.analytics.Analytics
import com.usmonie.word.features.games.domain.usecases.GetEnigmaQuoteUseCase
import com.usmonie.word.features.games.ui.enigma.EnigmaState.Companion.MIN_LIVES_COUNT
import com.usmonie.word.features.settings.domain.usecase.AddUserHintsCountUseCase
import com.usmonie.word.features.settings.domain.usecase.GetUserHintsCountUseCase
import com.usmonie.word.features.settings.domain.usecase.UseUserHintsCountUseCase
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlin.time.Duration.Companion.minutes
import kotlin.time.measureTime

@Immutable
class EnigmaGameViewModel(
	private val analytics: Analytics,
	private val getNextPhraseUseCase: GetEnigmaQuoteUseCase,
	private val getUserHintsCountUseCase: GetUserHintsCountUseCase,
	private val useUserHintsCountUseCase: UseUserHintsCountUseCase,
	private val addUserHintsCountUseCase: AddUserHintsCountUseCase
) : StateViewModel<EnigmaState, EnigmaAction, EnigmaEvent, EnigmaEffect>(EnigmaState.Loading()) {

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
		viewModelScope.launchSafe {
			getUserHintsCountUseCase().collect {
				handleAction(EnigmaAction.UpdateUserHints(it))
			}
		}
	}

	override fun EnigmaState.reduce(event: EnigmaEvent): EnigmaState {
		return when (event) {
			is EnigmaEvent.Correct -> {
				val nextPosition = event.selectedPosition
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


			is EnigmaEvent.UpdateHints -> when (this) {
				is EnigmaState.Game.Playing -> copy(hintsCount = event.hintsCount)
				else -> this
			}

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
		is EnigmaAction.UpdateUserHints -> EnigmaEvent.UpdateHints(action.userHintsCount)
		is EnigmaAction.InputLetter -> inputLetter(action)

		is EnigmaAction.CellSelected -> {
			if (state.value is EnigmaState.Game.HintSelection) {
				val newPhrase =
					findLetter(action.cellPositionInWord, action.wordPosition, state.value.phrase)
				val currentHintsCount = getUserHintsCountUseCase().first()
				EnigmaEvent.UpdateCurrentPhrase(newPhrase, currentHintsCount)
			} else {
				EnigmaEvent.UpdateSelectedCell(
					action.wordPosition,
					action.cellPositionInWord
				)
			}
		}

		EnigmaAction.NextPhrase -> {
			val phrase = getNextPhraseUseCase(GetEnigmaQuoteUseCase.Param())
			val hintsCount = getUserHintsCountUseCase().first()
			EnigmaEvent.NextPhrase(phrase.map(), hintsCount)
		}

		EnigmaAction.ReviveClicked -> EnigmaEvent.ReviveClicked
		EnigmaAction.ReviveGranted -> EnigmaEvent.ReviveGranted
		EnigmaAction.UseHint -> {
			val state = state.value
			if (state.hintsCount > 0) EnigmaEvent.UseHint else EnigmaEvent.NoHints
		}

		EnigmaAction.HintGranted -> {
			addUserHintsCountUseCase(1)
			val updatedHintsCount = getUserHintsCountUseCase().first()
			EnigmaEvent.UpdateHints(updatedHintsCount)
		}

		is EnigmaAction.AddHints -> {
			addUserHintsCountUseCase(action.count)
			val updatedHintsCount = getUserHintsCountUseCase().first()
			EnigmaEvent.UpdateHints(updatedHintsCount)
		}

		EnigmaAction.AdTime -> EnigmaEvent.ShowMiddleGameAd
		EnigmaAction.Won -> EnigmaEvent.Won
	}

	override suspend fun handleEvent(event: EnigmaEvent) = when (event) {
		is EnigmaEvent.Incorrect -> EnigmaEffect.InputEffect.Incorrect()
		is EnigmaEvent.Lost -> EnigmaEffect.ShowMiddleGameAd()
		is EnigmaEvent.NextPhrase -> EnigmaEffect.ShowMiddleGameAd()
		is EnigmaEvent.ReviveClicked -> EnigmaEffect.ShowRewardedLifeAd()
		is EnigmaEvent.NoHints -> EnigmaEffect.ShowRewardedHintAd()
		is EnigmaEvent.Correct -> {
			if (event.phrase.encryptedPositions.count() < 1) {
				handleAction(EnigmaAction.Won)
			}
			EnigmaEffect.InputEffect.Correct()
		}

		is EnigmaEvent.UpdateCurrentPhrase -> {
			if (event.phrase.encryptedPositions.count() < 1) {
				handleAction(EnigmaAction.Won)
			}
			EnigmaEffect.InputEffect.Correct()
		}

		is EnigmaEvent.ShowMiddleGameAd -> EnigmaEffect.ShowMiddleGameAd()
		is EnigmaEvent.UpdateSelectedCell -> EnigmaEffect.CellSelected()

		else -> null
	}

	private fun inputLetter(action: EnigmaAction.InputLetter): EnigmaEvent {
		val event: EnigmaEvent
		val executionTime = measureTime {
			val state = state.value
			val currentPosition = state.currentSelectedCellPosition

			if (currentPosition == null) {
				event = EnigmaEvent.ClearSelectionCell
				return@measureTime
			}
			val encryptedPhrase = state.phrase
			val phrase = encryptedPhrase.encryptedPhrase
			val phrasePositions = encryptedPhrase.encryptedPositions
			val currentPositionIndex = phrasePositions.indexOf(currentPosition)
			val nextPosition = if (currentPositionIndex == phrasePositions.lastIndex) {
				phrasePositions.first()
			} else {
				phrasePositions[currentPositionIndex + 1]
			}

			val word = phrase[currentPosition.first].cells
			val cell = word[currentPosition.second]
			if (cell.symbol.equals(action.letter, true)) {
				phrasePositions.remove(currentPosition)
				cell.state = CellState.Correct
			} else if (state.lives > MIN_LIVES_COUNT) {
				event = EnigmaEvent.Incorrect
				return@measureTime
			} else {
				event = EnigmaEvent.Lost
				return@measureTime
			}

			event = EnigmaEvent.Correct(state.phrase, nextPosition)
		}
		println("INPUT execution time: $executionTime")
		return event
	}

	private suspend fun findLetter(
		cellPositionInWord: Int,
		wordPosition: Int,
		phrase: EnigmaEncryptedPhrase
	): EnigmaEncryptedPhrase {
		val encryptedPositions = phrase.encryptedPositions
		val charsCount = phrase.charsCount
		val word = phrase.encryptedPhrase[wordPosition]
		val newCell =
			word.cells[cellPositionInWord].apply { state = CellState.Correct }

		val newWord =
			word.copy(cells = word.cells.apply { set(cellPositionInWord, newCell) })
		charsCount[newCell.symbol] = charsCount.getOrPut(newCell.symbol) { 1 } - 1

		val newPhrase = phrase.encryptedPhrase.apply { set(wordPosition, newWord) }
		encryptedPositions.remove(Pair(wordPosition, cellPositionInWord))
		useUserHintsCountUseCase()

		return EnigmaEncryptedPhrase(
			newPhrase,
			phrase.encryptedPositions,
			phrase.quote,
			charsCount
		)
	}

	private tailrec fun getNextPosition(
		cells: ObjectList<Word>,
		wordPosition: Int,
		nextCellPosition: Int
	): Pair<Int, Int>? {
		if (cells.lastIndex < wordPosition) return null
		val word = cells[wordPosition]

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

fun EnigmaGameViewModel.onLetterInput(letter: Char) = handleAction(EnigmaAction.InputLetter(letter))
fun EnigmaGameViewModel.onNextPhrase() = handleAction(EnigmaAction.NextPhrase)
fun EnigmaGameViewModel.onAddLifeClick() = handleAction(EnigmaAction.ReviveClicked)
fun EnigmaGameViewModel.onReviveGranted(amount: Int) = handleAction(EnigmaAction.ReviveGranted)
fun EnigmaGameViewModel.onHintGranted(amount: Int) = handleAction(EnigmaAction.AddHints(amount))
fun EnigmaGameViewModel.useHint() = handleAction(EnigmaAction.UseHint)
fun EnigmaGameViewModel.onCellSelected(
	cellPositionInWord: Int,
	wordPosition: Int
) = handleAction(EnigmaAction.CellSelected(cellPositionInWord, wordPosition))
