package com.usmonie.word.features.quotes.data.usecases

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

actual class QuotesSourceFactory(private val context: Context) {
	actual fun getSource(fileName: String): QuotesSource {
		val inputStream = context.assets.open(fileName)
		return QuotesSource(InputStreamReader(inputStream))
	}
}

actual class QuotesSource(private val inputStream: InputStreamReader) {
	actual suspend fun useLines(block: suspend (Sequence<String>) -> Unit) {
		BufferedReader(inputStream).useLines { block(it) }
	}
}
