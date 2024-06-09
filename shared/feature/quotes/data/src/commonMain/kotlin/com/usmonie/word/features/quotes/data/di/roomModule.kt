package com.usmonie.word.features.quotes.data.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.usmonie.word.features.games.domain.repositories.EnigmaRepository
import com.usmonie.word.features.quotes.data.QuotesDatabase
import com.usmonie.word.features.quotes.data.repositories.EnigmaRepositoryImpl
import com.usmonie.word.features.quotes.data.repositories.QuotesRepositoryImpl
import com.usmonie.word.features.quotes.data.usecases.ImportQuotesUseCaseImpl
import com.usmonie.word.features.qutoes.domain.di.quotesDomainModule
import com.usmonie.word.features.qutoes.domain.repositories.QuotesRepository
import com.usmonie.word.features.qutoes.domain.usecases.InitiateQuotesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val quotesDataModule = module {
    includes(quotesDomainModule, roomModule)
    single {
        get<RoomDatabase.Builder<QuotesDatabase>>(named(QuotesDatabase::class.toString()))
            .setQueryCoroutineContext(Dispatchers.IO)
            .fallbackToDestructiveMigration(true)
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    factoryOf(::QuotesRepositoryImpl) {
        bind<QuotesRepository>()
    }

    factoryOf(::ImportQuotesUseCaseImpl) {
        bind<InitiateQuotesUseCase>()
    }

    factoryOf(::EnigmaRepositoryImpl) {
        bind<EnigmaRepository>()
    }
}

internal expect val roomModule: Module
