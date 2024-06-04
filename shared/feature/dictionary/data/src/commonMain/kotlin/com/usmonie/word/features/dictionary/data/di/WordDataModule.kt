package com.usmonie.word.features.dictionary.data.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.usmonie.word.features.dictionary.data.api.WordApi
import com.usmonie.word.features.dictionary.data.db.models.CategoryDb
import com.usmonie.word.features.dictionary.data.db.models.DescendantDb
import com.usmonie.word.features.dictionary.data.db.models.EtymologyTemplateDb
import com.usmonie.word.features.dictionary.data.db.models.ExampleDb
import com.usmonie.word.features.dictionary.data.db.models.FormDb
import com.usmonie.word.features.dictionary.data.db.models.HeadTemplateDb
import com.usmonie.word.features.dictionary.data.db.models.InflectionTemplateDb
import com.usmonie.word.features.dictionary.data.db.models.InstanceDb
import com.usmonie.word.features.dictionary.data.db.models.RelatedDb
import com.usmonie.word.features.dictionary.data.db.models.SenseDb
import com.usmonie.word.features.dictionary.data.db.models.SoundDb
import com.usmonie.word.features.dictionary.data.db.models.TemplateDb
import com.usmonie.word.features.dictionary.data.db.models.TranslationDb
import com.usmonie.word.features.dictionary.data.db.models.WordDb
import com.usmonie.word.features.dictionary.data.db.models.WordFavorite
import com.usmonie.word.features.dictionary.data.db.models.WordSearchHistoryDb
import com.usmonie.word.features.dictionary.data.db.room.DictionaryDatabase
import com.usmonie.word.features.dictionary.data.repository.RealmWordsRepositoryImpl
import com.usmonie.word.features.dictionary.data.repository.RoomWordsRepositoryImpl
import com.usmonie.word.features.dictionary.data.usecases.MoveToNewDatabaseUseCaseImpl
import com.usmonie.word.features.dictionary.domain.repository.WordsRepository
import com.usmonie.word.features.dictionary.domain.usecases.MoveToNewDatabaseUseCase
import io.realm.kotlin.Configuration
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.module.dsl.binds
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dictionaryDataModule = module {
    includes(roomModule)
    single { WordApi("http://16.170.6.0") }

    single {
        get<RoomDatabase.Builder<DictionaryDatabase>>()
            .setQueryCoroutineContext(Dispatchers.IO)
            .fallbackToDestructiveMigration(true)
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single<Configuration> {
        RealmConfiguration.Builder(
            setOf(
                CategoryDb::class,
                DescendantDb::class,
                EtymologyTemplateDb::class,
                ExampleDb::class,
                FormDb::class,
                HeadTemplateDb::class,
                InflectionTemplateDb::class,
                InstanceDb::class,
                RelatedDb::class,
                SenseDb::class,
                SoundDb::class,
                TemplateDb::class,
                TranslationDb::class,
                WordDb::class,
                WordFavorite::class,
                WordSearchHistoryDb::class
            )
        )
            .deleteRealmIfMigrationNeeded()
            .build()
    }

    single<MoveToNewDatabaseUseCase> { MoveToNewDatabaseUseCaseImpl(get(), get()) }

    singleOf(::RoomWordsRepositoryImpl) {
        binds(listOf(WordsRepository::class, RoomWordsRepositoryImpl::class))
    }

    single<RealmWordsRepositoryImpl> {
        RealmWordsRepositoryImpl(Realm.open(get()), get())
    }
}

internal expect val roomModule: Module