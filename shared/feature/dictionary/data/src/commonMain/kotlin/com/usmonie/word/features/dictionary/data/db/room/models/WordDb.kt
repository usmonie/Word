package com.usmonie.word.features.dictionary.data.db.room.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.usmonie.word.features.dictionary.data.api.models.CategoryDto
import com.usmonie.word.features.dictionary.data.api.models.DescendantDto
import com.usmonie.word.features.dictionary.data.api.models.EtymologyTemplateDto
import com.usmonie.word.features.dictionary.data.api.models.FormDto
import com.usmonie.word.features.dictionary.data.api.models.HeadTemplateDto
import com.usmonie.word.features.dictionary.data.api.models.InflectionTemplateDto
import com.usmonie.word.features.dictionary.data.api.models.RelatedDto
import com.usmonie.word.features.dictionary.data.api.models.SenseDto
import com.usmonie.word.features.dictionary.data.api.models.SoundDto
import com.usmonie.word.features.dictionary.data.api.models.TranslationDto
import com.usmonie.word.features.dictionary.data.db.room.CategoryConverters
import com.usmonie.word.features.dictionary.data.db.room.DescendantConverters
import com.usmonie.word.features.dictionary.data.db.room.EtymologyTemplateConverters
import com.usmonie.word.features.dictionary.data.db.room.FormConverters
import com.usmonie.word.features.dictionary.data.db.room.HeadTemplateConverters
import com.usmonie.word.features.dictionary.data.db.room.InflectionTemplateConverters
import com.usmonie.word.features.dictionary.data.db.room.RelatedConverters
import com.usmonie.word.features.dictionary.data.db.room.SenseConverters
import com.usmonie.word.features.dictionary.data.db.room.SoundConverters
import com.usmonie.word.features.dictionary.data.db.room.StringConverters
import com.usmonie.word.features.dictionary.data.db.room.TranslationsConverters

@Entity(
    tableName = "words_table",
    indices = [
        Index(value = ["word"]),
        Index(
            value = ["word", "pos", "lang", "langCode", "etymologyNumber", "etymologyText"],
            unique = true
        )
    ]
)
internal data class WordDb(
    val word: String = "",

    val langCode: String = "",
    val lang: String = "",
    val originalTitle: String? = null,
    val pos: String = "",
    val source: String? = null,
    val etymologyNumber: Int? = null,
    val etymologyText: String? = null,
    @TypeConverters(DescendantConverters::class)
    val descendants: List<DescendantDto>,
    @TypeConverters(FormConverters::class)
    val forms: List<FormDto>,
    @TypeConverters(TranslationsConverters::class)
    val translations: List<TranslationDto>,
    @TypeConverters(SenseConverters::class)
    val senses: List<SenseDto>,
    @TypeConverters(SoundConverters::class)
    val sounds: List<SoundDto>,
    @TypeConverters(CategoryConverters::class)
    val categories: List<CategoryDto>,
    @TypeConverters(EtymologyTemplateConverters::class)
    val etymologyTemplates: List<EtymologyTemplateDto>,
    @TypeConverters(HeadTemplateConverters::class)
    val headTemplates: List<HeadTemplateDto>,
    @TypeConverters(InflectionTemplateConverters::class)
    val inflectionTemplates: List<InflectionTemplateDto>,
    @TypeConverters(StringConverters::class)
    val topics: List<String>,
    @TypeConverters(StringConverters::class)
    val wikidata: List<String>,
    @TypeConverters(StringConverters::class)
    val wikipedia: List<String>,
    @TypeConverters(RelatedConverters::class)
    val abbreviations: List<RelatedDto>,
    @TypeConverters(RelatedConverters::class)
    val altOf: List<RelatedDto>,
    @TypeConverters(RelatedConverters::class)
    val antonyms: List<RelatedDto>,
    @TypeConverters(RelatedConverters::class)
    val coordinateTerms: List<RelatedDto>,
    @TypeConverters(RelatedConverters::class)
    val derived: List<RelatedDto>,
    @TypeConverters(RelatedConverters::class)
    val formOf: List<RelatedDto>,
    @TypeConverters(RelatedConverters::class)
    val holonyms: List<RelatedDto>,
    @TypeConverters(RelatedConverters::class)
    val hypernyms: List<RelatedDto>,
    @TypeConverters(RelatedConverters::class)
    val hyphenation: List<String>,
    @TypeConverters(RelatedConverters::class)
    val hyponyms: List<RelatedDto>,
    @TypeConverters(RelatedConverters::class)
    val instances: List<RelatedDto>,
    @TypeConverters(RelatedConverters::class)
    val meronyms: List<RelatedDto>,
    @TypeConverters(RelatedConverters::class)
    val proverbs: List<RelatedDto>,
    @TypeConverters(RelatedConverters::class)
    val related: List<RelatedDto>,
    @TypeConverters(RelatedConverters::class)
    val synonyms: List<RelatedDto>,
    @TypeConverters(RelatedConverters::class)
    val troponyms: List<RelatedDto>,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
