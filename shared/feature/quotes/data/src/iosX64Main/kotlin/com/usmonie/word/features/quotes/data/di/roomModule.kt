package com.usmonie.word.features.quotes.data.di

import androidx.room.Room
import com.usmonie.word.features.quotes.data.QuotesDatabase
import com.usmonie.word.features.quotes.data.instantiateImpl
import com.usmonie.word.features.quotes.data.usecases.QuotesSourceFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

internal actual val roomModule: Module = module {
    single(named(QuotesDatabase::class.toString())) {
        val dbFilePath = NSHomeDirectory() + "/quotes.db"
        Room.databaseBuilder<QuotesDatabase>(
            name = dbFilePath,
            factory = { QuotesDatabase::class.instantiateImpl() }
        )
    }

    factory<QuotesSourceFactory> {
        QuotesSourceFactory()
    }
}
