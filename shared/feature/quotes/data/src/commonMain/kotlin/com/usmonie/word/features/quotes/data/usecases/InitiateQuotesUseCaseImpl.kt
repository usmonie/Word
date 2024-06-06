package com.usmonie.word.features.quotes.data.usecases

import com.usmonie.word.features.qutoes.domain.repositories.QuotesRepository
import com.usmonie.word.features.qutoes.domain.usecases.InitiateQuotesUseCase

class InitiateQuotesUseCaseImpl(private val quotesRepository: QuotesRepository) : InitiateQuotesUseCase {
    override suspend fun invoke(input: Unit) {
//        val inputStream = InputStreamReader(assets.open(fileName))
//
//        val reader = BufferedReader(inputStream)
//        reader.readLine()
//        var line = reader.readLine()
//        var number = 0
//        val items = ArrayList<Quote>(10000)
//        while (line != null) {
//            number++
//            val parts = line.split("|").map { it.trim() }
//            if (parts.size >= 3) {
//                val quoteText = parts[0].removeSurrounding("|")
//                val author = parts[1].removeSurrounding("|")
//                val categories = parts[2].removeSurrounding("|").split(", ").fastMap { it.trim() }
//                val quote = Quote(-1, quoteText, author, categories, false)
//                items.add(quote)
//            }
//            line = reader.readLine()
//            if (items.count() > 9999) {
//                quotesRepository.putAll(items)
//                items.clear()
//            }
//        }
//
//        quotesRepository.putAll(items)
//        Source
    }
}
