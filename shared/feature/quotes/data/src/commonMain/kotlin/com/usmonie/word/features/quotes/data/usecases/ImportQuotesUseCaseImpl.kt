package com.usmonie.word.features.quotes.data.usecases

import com.usmonie.core.domain.tools.fastMap
import com.usmonie.word.features.quotes.domain.models.Quote
import com.usmonie.word.features.quotes.domain.repositories.QuotesRepository
import com.usmonie.word.features.quotes.domain.usecases.InitiateQuotesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

internal const val QUOTES_COUNT = 404866L
internal const val BATCH_SIZE = 10000

internal class ImportQuotesUseCaseImpl(
	private val quotesSource: QuotesSourceFactory,
	private val quotesRepository: QuotesRepository
) : InitiateQuotesUseCase {
	private val regex = "[\\x00-\\xFF]+".toRegex()

	override fun invoke(input: Unit): Flow<Boolean> = flow {
		val quotesAlreadyInserted = quotesRepository.getQuotesCount()
		if (quotesAlreadyInserted >= QUOTES_COUNT) {
			emit(false)
		} else {
			withContext(Dispatchers.IO) {
				val result = insertQuotes(quotesAlreadyInserted, quotesSource.getSource("quotes.csv"))
				emit(result)
			}
		}
	}

	private suspend fun insertQuotes(quotesAlreadyInserted: Long, source: QuotesSource): Boolean {
		return try {
			var totalInserted = quotesAlreadyInserted
			var linesProcessed = 0L

			source.useLines { lines ->
				lines.drop(quotesAlreadyInserted.toInt())
					.chunked(BATCH_SIZE)
					.forEach { batch ->
						val items = batch.mapNotNull { line -> processLine(line) }
						quotesRepository.putAll(items)
						totalInserted += items.size
						linesProcessed += batch.size
						println("INSERTED $totalInserted")
					}
			}

			println("QUOTES ALL IMPORTED $totalInserted")
			true
		} catch (e: Exception) {
			println("exception: $e")
			e.printStackTrace()
			false
		}
	}

	private fun processLine(line: String): Quote? {
		val parts = line.split("|").fastMap { it.trim() }
		if (parts.size >= 3) {
			val quoteText = parts[0].removeSurrounding("|")
			if (regex.matches(quoteText)) {
				val author = parts[1].removeSurrounding("|")
				val categories = parts[2].removeSurrounding("|").split(", ").fastMap { it.trim() }
				return Quote("", quoteText, author, categories, false, wasPlayed = false)
			}
		}
		return null
	}
}

expect class QuotesSourceFactory {
	fun getSource(fileName: String): QuotesSource
}

expect class QuotesSource {
	suspend fun useLines(block: suspend (Sequence<String>) -> Unit)
}