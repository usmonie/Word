package com.usmonie.word.features.dictionary.data.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.usmonie.word.features.dictionary.data.api.WordApi
import com.usmonie.word.features.dictionary.data.db.room.DictionaryDatabase
import com.usmonie.word.features.dictionary.data.repository.RoomWordsRepositoryImpl
import com.usmonie.word.features.dictionary.domain.repository.WordRepository
import io.realm.kotlin.Configuration
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module

val dictionaryDataModule = module {
    includes(roomModule)
    single { WordApi("http://16.170.6.0") }
    factory {
        get<RoomDatabase.Builder<DictionaryDatabase>>()
            .setQueryCoroutineContext(Dispatchers.IO)
            .fallbackToDestructiveMigration(true)
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single<Configuration> {
        RealmConfiguration.Builder(
            setOf(
//                CategoryDb::class,
//                DescendantDb::class,
//                EtymologyTemplateDb::class,
//                ExampleDb::class,
//                FormDb::class,
//                HeadTemplateDb::class,
//                InflectionTemplateDb::class,
//                InstanceDb::class,
//                RelatedDb::class,
//                SenseDb::class,
//                SoundDb::class,
//                TemplateDb::class,
//                TranslationDb::class,
//                WordDb::class,
//                WordFavorite::class,
//                WordSearchHistoryDb::class
            )
        )
            .deleteRealmIfMigrationNeeded()
            .build()
    }
    single<WordRepository> {
        RoomWordsRepositoryImpl(get(), get())
    }
}

internal expect val roomModule: Module
