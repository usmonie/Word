package com.usmonie.word.features.games.ui.enigma

import androidx.collection.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.usmonie.core.domain.tools.fastMap
import com.usmonie.word.features.games.domain.usecases.GetEnigmaQuoteUseCase
import com.usmonie.word.features.qutoes.domain.models.Quote
import org.koin.core.time.measureDuration

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
	val encryptedPositions: MutableObjectList<Pair<Int, Int>>,
	val quote: Quote,
	val charsCount: MutableScatterMap<Char, Int>
)

@Immutable
data class Word(val cells: MutableObjectList<Cell>) {
	val size: Int = cells.size
}

fun map(cell: GetEnigmaQuoteUseCase.Cell) = Cell(
	cell.symbol,
	cell.number,
	when (cell.state) {
		GetEnigmaQuoteUseCase.CellState.Empty -> CellState.Empty
		GetEnigmaQuoteUseCase.CellState.Found -> CellState.Found
	}
)

fun map(word: GetEnigmaQuoteUseCase.Word): Word {
	val newCells = mutableObjectListOf<Cell>()
	newCells.addAll(word.cells.fastMap { map(it) })

	return Word(cells = newCells)
}

fun GetEnigmaQuoteUseCase.EnigmaEncryptedPhrase.map(): EnigmaEncryptedPhrase {
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
