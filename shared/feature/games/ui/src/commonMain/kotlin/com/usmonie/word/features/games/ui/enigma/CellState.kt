package com.usmonie.word.features.games.ui.enigma

import androidx.collection.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.usmonie.core.domain.tools.fastMap
import com.usmonie.word.features.games.domain.usecases.GetCryptogramQuoteUseCase
import com.usmonie.word.features.quotes.domain.models.Quote

@Immutable
sealed class CellState {
	data object Empty : CellState()
	data class Incorrect(val guessedLetter: Char) : CellState()
	data object Correct : CellState()
	data object Found : CellState()
}

@Stable
data class Cell(
	val symbol: Char,
	val number: Int,
	var state: CellState = CellState.Empty,
) {
	val isLetter: Boolean = symbol.isLetter()

	val letter: String
		get() = when (state) {
			CellState.Correct -> symbol.toString()
			CellState.Found -> symbol.toString()
			else -> ""
		}

	@Composable
	fun backgroundColor(hintSelection: Boolean, isSelected: Boolean): Color = when (state) {
		CellState.Empty -> if (hintSelection || isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
		else -> Color.Transparent
	}

	@Composable
	fun textColor(hintSelection: Boolean, isSelected: Boolean): Color = when (state) {
		CellState.Correct -> MaterialTheme.colorScheme.primary
		CellState.Empty -> if (hintSelection) {
			MaterialTheme.colorScheme.onBackground
		} else if (isSelected) {
			MaterialTheme.colorScheme.onPrimary
		} else {
			MaterialTheme.colorScheme.onBackground
		}

		CellState.Found -> MaterialTheme.colorScheme.onBackground
		is CellState.Incorrect -> MaterialTheme.colorScheme.error
	}
}

@Immutable
data class EnigmaEncryptedPhrase(
	val encryptedPhrase: MutableObjectList<Word>,
	val encryptedPositions: MutableObjectList<Triple<Int, Int, Int>>,
	val quote: Quote,
	val charsCount: MutableScatterMap<Char, Int>
)

@Immutable
data class Word(val cells: MutableObjectList<Cell>) {
	val size: Int = cells.size
}

fun map(cell: GetCryptogramQuoteUseCase.Cell) = Cell(
	cell.symbol,
	cell.number,
	when (cell.state) {
		GetCryptogramQuoteUseCase.CellState.Empty -> CellState.Empty
		GetCryptogramQuoteUseCase.CellState.Found -> CellState.Found
		GetCryptogramQuoteUseCase.CellState.PartiallyGuessed -> CellState.Correct
	}
)

fun map(word: GetCryptogramQuoteUseCase.Word): Word {
	val newCells = mutableObjectListOf<Cell>()
	newCells.addAll(word.cells.fastMap { map(it) })

	return Word(cells = newCells)
}

fun GetCryptogramQuoteUseCase.CryptogramEncryptedPhrase.map(): EnigmaEncryptedPhrase {
	val newPhrase = mutableObjectListOf<Word>()
	encryptedPhrase.forEach {
		newPhrase.add(map(it))
	}

	return EnigmaEncryptedPhrase(
		encryptedPhrase = newPhrase,
		encryptedPositions = encryptedPositions,
		quote = quote,
		charsCount = charsCount
	)
}
