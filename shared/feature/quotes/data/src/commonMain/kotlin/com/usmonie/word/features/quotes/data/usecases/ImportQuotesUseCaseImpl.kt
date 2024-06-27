package com.usmonie.word.features.quotes.data.usecases

import com.usmonie.core.domain.tools.fastMap
import com.usmonie.word.features.quotes.domain.models.Quote
import com.usmonie.word.features.quotes.domain.repositories.QuotesRepository
import com.usmonie.word.features.quotes.domain.usecases.InitiateQuotesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.io.Source
import kotlinx.io.readLine

internal const val QUOTES_COUNT = 404866L

internal class ImportQuotesUseCaseImpl(
	private val quotesSource: QuotesSourceFactory,
	private val quotesRepository: QuotesRepository
) : InitiateQuotesUseCase {

	private val regex = "[\\x00-\\xFF]+".toRegex()
	override fun invoke(input: Unit): Flow<Boolean> = flow {
		val quotesAlreadyInserted = quotesRepository.getQuotesCount()

		if (quotesRepository.getQuotesCount() >= QUOTES_COUNT) {
			emit(false)
		} else {
			withContext(Dispatchers.IO) {
				insertQuotes(quotesAlreadyInserted, quotesSource.getSource("quotes.csv"))
			}
		}
	}

	private suspend fun FlowCollector<Boolean>.insertQuotes(quotesAlreadyInserted: Long, source: Source) {
		try {
			var line = source.readLine()
			var i = 0
			while (quotesAlreadyInserted > i) {
				source.readLine()
				i++
			}

			var number = 0
			val items = ArrayList<Quote>(10_000)
			while (line != null) {
				val parts = line.split("|").fastMap { it.trim() }
				if (parts.size >= 3) {
					val quoteText = parts[0].removeSurrounding("|")
					if (regex.matches(quoteText)) {
						number++

						val author = parts[1].removeSurrounding("|")
						val categories = parts[2].removeSurrounding("|").split(", ").fastMap { it.trim() }
						val quote = Quote("", quoteText, author, categories, false, wasPlayed = false)
						items.add(quote)
					}
				}
				line = source.readLine()
				if (items.count() > 9999) {

					quotesRepository.putAll(items)
					items.clear()
				}
			}

			quotesRepository.putAll(items)
			println("QUOTES ALL IMPORTED $number")

			emit(true)
		} catch (e: Exception) {
			println("exception: $e")
			e.printStackTrace()
			emit(false)
		}
	}
}

expect class QuotesSourceFactory {
	fun getSource(fileName: String): Source
}
