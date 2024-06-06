package com.usmonie.word.features.quotes.data.di

import androidx.room.Room
import com.usmonie.word.features.quotes.data.QuotesDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal actual val roomModule: Module = module {
    single(named(QuotesDatabase::class.toString())) {
        val appContext = androidContext().applicationContext
        val dbFile = appContext.getDatabasePath("quotes")
        Room.databaseBuilder<QuotesDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
            .createFromAsset("quotes.db")
    }
}
