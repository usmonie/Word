package com.usmonie.word.features.dictionary.data.db.room.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
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
import kotlinx.serialization.Serializable

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
@Serializable
internal data class WordDb(
    val word: String = "",
    val langCode: String = "",
    val lang: String = "",
    val originalTitle: String? = null,
    val pos: String = "",
    val source: String? = null,
    val etymologyNumber: Int? = null,
    val etymologyText: String? = null,
    val descendants: List<DescendantDto>,
    val forms: List<FormDto>,
    val translations: List<TranslationDto>,
    val senses: List<SenseDto>,
    val sounds: List<SoundDto>,
    val categories: List<CategoryDto>,
    val etymologyTemplates: List<EtymologyTemplateDto>,
    val headTemplates: List<HeadTemplateDto>,
    val inflectionTemplates: List<InflectionTemplateDto>,
    val topics: List<String>,
    val wikidata: List<String>,
    val wikipedia: List<String>,
    val abbreviations: List<RelatedDto>,
    val altOf: List<RelatedDto>,
    val antonyms: List<RelatedDto>,
    val coordinateTerms: List<RelatedDto>,
    val derived: List<RelatedDto>,
    val formOf: List<RelatedDto>,
    val holonyms: List<RelatedDto>,
    val hypernyms: List<RelatedDto>,
    val hyphenation: List<String>,
    val hyponyms: List<RelatedDto>,
    val instances: List<RelatedDto>,
    val meronyms: List<RelatedDto>,
    val proverbs: List<RelatedDto>,
    val related: List<RelatedDto>,
    val synonyms: List<RelatedDto>,
    val troponyms: List<RelatedDto>,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
