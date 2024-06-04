package com.usmonie.word.features.quotes.data.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.usmonie.word.features.quotes.data.QuotesDatabase
import com.usmonie.word.features.qutoes.domain.di.quotesDomainModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect val roomModule: Module

val quotesDataModule = module {
    includes(quotesDomainModule)
    single {
        get<RoomDatabase.Builder<QuotesDatabase>>()
            .setQueryCoroutineContext(Dispatchers.IO)
            .fallbackToDestructiveMigration(true)
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}
