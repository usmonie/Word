package com.usmonie.word.features.games.data.di

import androidx.room.Room
import com.usmonie.word.features.games.data.db.room.DictionaryDatabase
import com.usmonie.word.features.games.data.db.room.instantiateImpl
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
internal actual val roomModule: Module = module {
	single {
		val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
			directory = NSDocumentDirectory,
			inDomain = NSUserDomainMask,
			appropriateForURL = null,
			create = false,
			error = null,
		)

		val dbFilePath = documentDirectory?.path!! + "/dictionary.db"
		Room.databaseBuilder<DictionaryDatabase>(
			name = dbFilePath,
			factory = { DictionaryDatabase::class.instantiateImpl() }
		)
	}
}
