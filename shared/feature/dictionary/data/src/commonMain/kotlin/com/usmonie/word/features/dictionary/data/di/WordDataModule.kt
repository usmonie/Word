package com.usmonie.word.features.dictionary.data.di

import com.usmonie.word.features.dictionary.domain.repository.WordRepository
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
import com.usmonie.word.features.dictionary.data.repository.WordsRepositoryImpl
import io.realm.kotlin.Configuration
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val wordDataModule = module {
    single { WordApi("http://16.170.6.0") }
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
    single<WordRepository> { WordsRepositoryImpl(Realm.open(get()), get()) }
}
