package com.usmonie.word.features.games.ui.enigma

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.usmonie.core.domain.tools.fastMap

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
    val phrase: String,
    val author: String,
    val encryptedPositionsCount: Int,
    val charsCount: Map<Char, Int>
)

@Immutable
data class Word(val cells: List<Cell>) {
    val size: Int = cells.size
}

fun encryptPhrase(phrase: String, author: String): EnigmaEncryptedPhrase {
    var encryptedPositionsCount = 0
    val alphabet = ALPHABET
        .asSequence()
        .shuffled()
        .mapIndexed { index, c -> c to index }
        .associateBy({ it.first }, { it.second })

    val chars = phrase.toList()
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
        author,
        encryptedPositionsCount,
        charsCount
    )
}

private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
