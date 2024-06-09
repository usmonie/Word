package com.usmonie.word.features.games.ui.enigma

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.usmonie.core.domain.tools.fastMap
import com.usmonie.word.features.games.domain.usecases.GetEnigmaQuoteUseCase
import com.usmonie.word.features.qutoes.domain.models.Quote

@Immutable
sealed class CellState {
    data object Empty : CellState()
    data class Incorrect(val guessedLetter: Char) : CellState()
    data object Correct : CellState()
    data object Found : CellState()
}

@Immutable
data class Cell(
    val symbol: Char,
    val number: Int,
    val state: CellState = CellState.Empty,
) {
    val isLetter: Boolean = symbol.isLetter()

    val letter: String = when (state) {
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
    val encryptedPhrase: List<Word>,
    val quote: Quote,
    val encryptedPositionsCount: Int,
    val charsCount: Map<Char, Int>
)

@Immutable
data class Word(val cells: List<Cell>) {
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

fun map(word: GetEnigmaQuoteUseCase.Word) = Word(cells = word.cells.fastMap { map(it) })

fun GetEnigmaQuoteUseCase.EnigmaEncryptedPhrase.map() = EnigmaEncryptedPhrase(
    encryptedPhrase = encryptedPhrase.fastMap { map(it) },
    quote = quote,
    encryptedPositionsCount = encryptedPositionsCount,
    charsCount = charsCount
)

fun encryptPhrase(phrase: Quote): EnigmaEncryptedPhrase {
    var encryptedPositionsCount = 0
    val alphabet = ALPHABET
        .asSequence()
        .shuffled()
        .mapIndexed { index, c -> c to index }
        .associateBy({ it.first }, { it.second })

    val chars = phrase.text.toList()
    val charsShuffled = chars.asSequence()
        .shuffled()
        .distinct()
        .filter { char -> char.isLetter() }
        .toList()

    val charsCount = mutableMapOf<Char, Int>()

    val randomCharFirst = charsShuffled.first().uppercaseChar()
    val randomCharSecond = charsShuffled[1].uppercaseChar()
    val randomCharThird = charsShuffled[2].uppercaseChar()
    val encryptedPhrase = phrase
        .text
        .split(" ")
        .fastMap {
            val cells = it.toList().fastMap { char ->
                val charUppercase = char.uppercaseChar()
                val index = alphabet.getOrElse(charUppercase) { -1 }
                charsCount[charUppercase] = charsCount.getOrElse(charUppercase) { 0 } + 1

                val cell = Cell(
                    char,
                    index + 1,
                    if (
                        charUppercase == randomCharFirst ||
                        charUppercase == randomCharSecond ||
                        charUppercase == randomCharThird
                    ) {
                        CellState.Found
                    } else {
                        CellState.Empty
                    }
                )

                if (cell.state == CellState.Empty && cell.isLetter) {
                    encryptedPositionsCount++
                }

                cell
            }
            Word(cells)
        }

    return EnigmaEncryptedPhrase(
        encryptedPhrase,
        phrase,
        encryptedPositionsCount,
        charsCount
    )
}

private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
