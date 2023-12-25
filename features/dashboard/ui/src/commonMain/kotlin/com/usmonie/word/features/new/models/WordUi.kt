package com.usmonie.word.features.new.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.models.Word

@Stable
@Immutable
data class WordUi(
    val id: String,
    val source: String?,
    val word: String,
    val lang: String,
    val langCode: String,
    val originalTitle: String?,
    val pos: String,
    val etymologyNumber: Int?,
    val etymologyText: String?,
    val abbreviations: List<RelatedUi> = listOf(),
    val altOf: List<RelatedUi> = listOf(),
    val antonyms: List<RelatedUi> = listOf(),
    val categories: List<CategoryUi> = listOf(),
    val coordinateTerms: List<RelatedUi> = listOf(),
    val derived: List<RelatedUi> = listOf(),
    val descendants: List<DescendantUi> = listOf(),
    val etymologyTemplates: List<EtymologyTemplateUi> = listOf(),
    val formOf: List<RelatedUi> = listOf(),
    val forms: List<FormUi> = listOf(),
    val headTemplates: List<HeadTemplateUi> = listOf(),
    val holonyms: List<RelatedUi> = listOf(),
    val hypernyms: List<RelatedUi> = listOf(),
    val hyphenation: List<String> = listOf(),
    val hyponyms: List<RelatedUi> = listOf(),
    val inflectionTemplates: List<InflectionTemplateUi> = listOf(),
    val instances: List<RelatedUi> = listOf(),
    val meronyms: List<RelatedUi> = listOf(),
    val proverbs: List<RelatedUi> = listOf(),
    val related: List<RelatedUi> = listOf(),
    val senses: List<SenseCombinedUi> = listOf(),
    val sounds: List<SoundUi> = listOf(),
    val synonyms: List<RelatedUi> = listOf(),
    val topics: List<String> = listOf(),
    val translations: List<TranslationUi> = listOf(),
    val troponyms: List<RelatedUi> = listOf(),
    val wikidata: List<String> = listOf(),
    val wikipedia: List<String> = listOf(),
    val wordDomain: Word
)