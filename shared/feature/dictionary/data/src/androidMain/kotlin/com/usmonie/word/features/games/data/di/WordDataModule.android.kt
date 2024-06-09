package com.usmonie.word.features.games.data.di

import androidx.room.Room
import com.usmonie.word.features.games.data.db.room.DictionaryDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val roomModule: Module = module {
    factory {
        val appContext = androidContext().applicationContext
        val dbFile = appContext.getDatabasePath("dictionary.db")
        Room.databaseBuilder<DictionaryDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}
