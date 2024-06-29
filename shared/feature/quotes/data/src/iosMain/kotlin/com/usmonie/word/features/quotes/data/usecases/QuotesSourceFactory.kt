package com.usmonie.word.features.quotes.data.usecases

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.*

actual class QuotesSource(private val url: NSURL) {
	@OptIn(ExperimentalForeignApi::class)
	actual suspend fun useLines(block: suspend (Sequence<String>) -> Unit) {
		val fileContents = NSString.stringWithContentsOfURL(url, encoding = NSUTF8StringEncoding, error = null)
		fileContents?.let { contents ->
			val lines = sequence {
				contents.lines().forEach { line ->
					yield(line.toString())
				}
			}
			block(lines)
		}
	}
}

actual class QuotesSourceFactory {
	actual fun getSource(fileName: String): QuotesSource {
		val bundle = NSBundle.mainBundle
		val path = bundle.pathForResource(fileName.substringBeforeLast("."), ofType = fileName.substringAfterLast("."))
		val url = path?.let { NSURL.fileURLWithPath(it) }
		return QuotesSource(url ?: throw IllegalArgumentException("File not found: $fileName"))
	}
}
