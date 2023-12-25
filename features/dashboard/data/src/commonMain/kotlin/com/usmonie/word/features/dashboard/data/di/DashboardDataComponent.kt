package com.usmonie.word.features.dashboard.data.di

import com.usmonie.word.features.dashboard.data.api.WordApi
import com.usmonie.word.features.dashboard.data.db.models.CategoryDb
import com.usmonie.word.features.dashboard.data.db.models.DescendantDb
import com.usmonie.word.features.dashboard.data.db.models.EtymologyTemplateDb
import com.usmonie.word.features.dashboard.data.db.models.ExampleDb
import com.usmonie.word.features.dashboard.data.db.models.FormDb
import com.usmonie.word.features.dashboard.data.db.models.HeadTemplateDb
import com.usmonie.word.features.dashboard.data.db.models.InflectionTemplateDb
import com.usmonie.word.features.dashboard.data.db.models.InstanceDb
import com.usmonie.word.features.dashboard.data.db.models.RelatedDb
import com.usmonie.word.features.dashboard.data.db.models.SenseDb
import com.usmonie.word.features.dashboard.data.db.models.SoundDb
import com.usmonie.word.features.dashboard.data.db.models.TemplateDb
import com.usmonie.word.features.dashboard.data.db.models.TranslationDb
import com.usmonie.word.features.dashboard.data.db.models.WordDb
import com.usmonie.word.features.dashboard.data.db.models.WordFavorite
import com.usmonie.word.features.dashboard.data.db.models.WordSearchHistoryDb
import com.usmonie.word.features.dashboard.data.repository.WordsRepositoryImpl
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object DashboardDataComponent {

    fun getWordsRepository(api: WordApi): WordRepository {
        val realm = RealmConfiguration.Builder(
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

        return WordsRepositoryImpl(Realm.open(realm), api)
    }
}