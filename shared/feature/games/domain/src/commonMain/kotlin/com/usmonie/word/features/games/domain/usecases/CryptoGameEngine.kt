package com.usmonie.word.features.games.domain.usecases

import com.usmonie.core.domain.tools.fastAll


class CryptogramGameEngine(
	private val cryptogramPhrase: GetCryptogramQuoteUseCase.CryptogramEncryptedPhrase
) {
	private val guessedLetters = mutableMapOf<Char, Char>()

	fun makeGuess(encryptedLetter: Char, guessedLetter: Char) {
		guessedLetters[encryptedLetter] = guessedLetter
		updateCellStates()
	}

	private fun updateCellStates() {
		cryptogramPhrase.encryptedPhrase.forEach { word ->
			word.cells.forEach { cell ->
				if (cell.state != GetCryptogramQuoteUseCase.CellState.Found /*&& cell.state != GetCryptogramQuoteUseCase.CellState.Hinted*/) {
					val guessedLetter = guessedLetters[cell.symbol.uppercaseChar()]
					if (guessedLetter != null) {
						cell.state = if (guessedLetter == cell.symbol.uppercaseChar()) {
							GetCryptogramQuoteUseCase.CellState.Found
						} else {
							GetCryptogramQuoteUseCase.CellState.PartiallyGuessed
						}
					}
				}
			}
		}
	}

	fun useHint(): Boolean {
		val availableHints = cryptogramPhrase.hints.filter { hint ->
			val (wordIndex, charIndex) = hint.position
			cryptogramPhrase.encryptedPhrase[wordIndex].cells[charIndex].state != GetCryptogramQuoteUseCase.CellState.Found
		}

		if (availableHints.isNotEmpty()) {
			val hint = availableHints.first()
			val (wordIndex, charIndex) = hint.position
			val cell = cryptogramPhrase.encryptedPhrase[wordIndex].cells[charIndex]
//            cell.state = GetCryptogramQuoteUseCase.CellState.Hinted
			guessedLetters[cell.symbol.uppercaseChar()] = hint.letter.uppercaseChar()
			updateCellStates()
			return true
		}
		return false
	}

	fun isGameComplete(): Boolean {
		return cryptogramPhrase.encryptedPhrase.asList().fastAll { word ->
			word.cells.all { cell ->
				cell.state == GetCryptogramQuoteUseCase.CellState.Found /*|| cell.state == GetCryptogramQuoteUseCase.CellState.Hinted*/
			}
		}
	}
}