package com.usmonie.word.features.dashboard.ui.games.enigma

import wtf.word.core.domain.tools.fastMap

sealed class CellState {
    data object Empty : CellState()
    data class Incorrect(val guessedLetter: Char) : CellState()
    data object Correct : CellState()
    data object Found : CellState()
}

data class Cell(
    val letter: Char,
    val number: Int,
    val isLetter: Boolean,
    val state: CellState = CellState.Empty,
)

data class EnigmaEncryptedPhrase(
    val phrase: List<List<Cell>>,
    val encryptedPositionsCount: Int,
)

fun encryptPhrase(phrase: String): EnigmaEncryptedPhrase {
    var encryptedPositionsCount = 0
    val alphabet = alphabet
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

    val randomCharFirst = charsShuffled.first().uppercaseChar()
    val randomCharSecond = charsShuffled[1].uppercaseChar()
    val randomCharThird = charsShuffled[2].uppercaseChar()
    val encryptedPhrase = phrase
        .split(" ")
        .fastMap {
            val cells = it.toList().fastMap { char ->
                val charUppercase = char.uppercaseChar()
                val index = alphabet.getOrElse(charUppercase) { -1 }

                val cell = Cell(
                    char,
                    index + 1,
                    char.isLetter(),
                    if (charUppercase == randomCharFirst || charUppercase == randomCharSecond || charUppercase == randomCharThird) CellState.Found else CellState.Empty
                )

                if (cell.state == CellState.Empty && cell.isLetter) {
                    encryptedPositionsCount++
                }

                cell
            }
            cells
        }
    return EnigmaEncryptedPhrase(
        encryptedPhrase,
        encryptedPositionsCount
    )
}

private const val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"