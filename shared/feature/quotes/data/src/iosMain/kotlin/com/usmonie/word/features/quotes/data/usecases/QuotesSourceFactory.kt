package com.usmonie.word.features.quotes.data.usecases

import kotlinx.io.Source
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import platform.Foundation.NSBundle

actual class QuotesSourceFactory {
    actual fun getSource(fileName: String): Source {
        val assetFile = NSBundle.mainBundle.resourcePath + "/$fileName"

        return SystemFileSystem.source(Path(assetFile)).buffered()
    }
}
