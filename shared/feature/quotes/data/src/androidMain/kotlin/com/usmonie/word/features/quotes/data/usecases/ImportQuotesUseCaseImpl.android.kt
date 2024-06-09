package com.usmonie.word.features.quotes.data.usecases

import android.content.Context
import kotlinx.io.Source
import kotlinx.io.asSource
import kotlinx.io.buffered

actual class QuotesSourceFactory(private val context: Context) {
    actual fun getSource(fileName: String): Source {
        return context.assets.open(fileName).asSource().buffered()
    }
}
