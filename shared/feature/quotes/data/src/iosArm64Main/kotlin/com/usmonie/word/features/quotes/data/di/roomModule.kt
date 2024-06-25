package com.usmonie.word.features.quotes.data.di

import androidx.room.Room
import kotlinx.cinterop.ExperimentalForeignApi
import com.usmonie.word.features.quotes.data.QuotesDatabase
import com.usmonie.word.features.quotes.data.instantiateImpl
import com.usmonie.word.features.quotes.data.usecases.QuotesSourceFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.Foundation.NSURL
import platform.Foundation.NSFileManager
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
internal actual val roomModule: Module = module {
	single(named(QuotesDatabase::class.toString())) {
		val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
			directory = NSDocumentDirectory,
			inDomain = NSUserDomainMask,
			appropriateForURL = null,
			create = false,
			error = null,
		)

		val dbFilePath = documentDirectory?.path!! + "/quotes.db"
		Room.databaseBuilder<QuotesDatabase>(
			name = dbFilePath,
			factory = { QuotesDatabase::class.instantiateImpl() }
		)
	}

	factory<QuotesSourceFactory> {
		QuotesSourceFactory()
	}
}
