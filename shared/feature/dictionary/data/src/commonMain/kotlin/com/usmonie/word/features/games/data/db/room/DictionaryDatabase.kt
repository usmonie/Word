package com.usmonie.word.features.games.data.db.room

import androidx.room.BuiltInTypeConverters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.usmonie.word.features.games.data.db.room.dao.FavoritesDao
import com.usmonie.word.features.games.data.db.room.dao.WordDao
import com.usmonie.word.features.games.data.db.room.models.SearchHistoryDb
import com.usmonie.word.features.games.data.db.room.models.WordDb
import com.usmonie.word.features.games.data.db.room.models.WordFavorite

@Database(
    entities = [WordDb::class, WordFavorite::class, SearchHistoryDb::class],
    version = 9,
    exportSchema = false
)
@TypeConverters(
    StringConverters::class,
    StringsConverters::class,
    RelatedConverters::class,
    CategoryConverters::class,
    DescendantConverters::class,
    EtymologyTemplateConverters::class,
    HeadTemplateConverters::class,
    InflectionTemplateConverters::class,
    FormConverters::class,
    SenseConverters::class,
    SoundConverters::class,
    TranslationsConverters::class,
    WordsConverters::class,
    builtInTypeConverters = BuiltInTypeConverters()
)
abstract class DictionaryDatabase : RoomDatabase() {
    internal abstract fun favoritesDao(): FavoritesDao
    internal abstract fun wordDao(): WordDao
}
