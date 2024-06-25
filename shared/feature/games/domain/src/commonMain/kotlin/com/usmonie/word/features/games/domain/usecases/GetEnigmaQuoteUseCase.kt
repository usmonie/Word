package com.usmonie.word.features.games.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.core.domain.usecases.invoke
import com.usmonie.word.features.qutoes.domain.models.Quote
import com.usmonie.word.features.qutoes.domain.repositories.QuotesRepository
import kotlinx.coroutines.flow.first
import kotlin.math.roundToInt
import kotlin.random.Random

interface GetEnigmaQuoteUseCase :
	CoroutineUseCase<GetEnigmaQuoteUseCase.Param, GetEnigmaQuoteUseCase.EnigmaEncryptedPhrase> {

	class Param

	data class EnigmaEncryptedPhrase(
		val encryptedPhrase: List<Word>,
		val quote: Quote,
		val encryptedPositionsCount: Int,
		val charsCount: Map<Char, Int>
	)

	data class Word(val cells: List<Cell>) {
		val size: Int = cells.size
	}

	data class Cell(
		val symbol: Char,
		val number: Int,
		val state: CellState = CellState.Empty,
	)

	sealed class CellState {
		data object Empty : CellState()
		data object Found : CellState()
	}
}

internal class GetEnigmaQuoteUseCaseImpl(
	private val getEnigmaLevelUseCase: GetEnigmaLevelUseCase,
	private val quotesRepository: QuotesRepository
) : GetEnigmaQuoteUseCase {
	override suspend fun invoke(input: GetEnigmaQuoteUseCase.Param): GetEnigmaQuoteUseCase.EnigmaEncryptedPhrase {
		val level = getEnigmaLevelUseCase().first()
		val quote = quotesRepository.getRandomWasntPlayedQuote()
		return encryptQuote(quote, level)
	}

	private fun encryptQuote(quote: Quote, userLevel: Int): GetEnigmaQuoteUseCase.EnigmaEncryptedPhrase {
		val alphabet = ALPHABET
			.asSequence()
			.shuffled()
			.mapIndexed { index, c -> c to index }
			.associateBy({ it.first }, { it.second })
		val percentage = (userLevel.coerceIn(10, 100) / 100.0)
		val letters = quote.text.filter { it.isLetter() }
		val hiddenLetters = (percentage * letters.length).roundToInt().coerceAtLeast(3)
		val hiddenIndices = generateSequence { Random.nextInt(letters.length) }
			.distinct()
			.take(hiddenLetters)
			.toSet()
			.sorted()

		val charsCount = mutableMapOf<Char, Int>()
		var symbolIndex = 0

		val encryptedPhrase = quote.text.split(" ").map { word ->
			val cells = word.mapIndexed { index, char ->
				val charUppercase = char.uppercaseChar()
				charsCount[charUppercase] = charsCount.getOrElse(charUppercase) { 0 } + 1
				val alphabetIndex = alphabet[charUppercase]?.plus(1) ?: -1
				GetEnigmaQuoteUseCase.Cell(
					char,
					alphabetIndex,
					if (char.isLetter() && index in hiddenIndices) {
						GetEnigmaQuoteUseCase.CellState.Empty
					} else {
						symbolIndex++
						GetEnigmaQuoteUseCase.CellState.Found
					}
				)
			}
			GetEnigmaQuoteUseCase.Word(cells)
		}

		return GetEnigmaQuoteUseCase.EnigmaEncryptedPhrase(
			encryptedPhrase,
			quote,
			hiddenLetters,
			charsCount
		)
	}
}

private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
