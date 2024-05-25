package com.usmonie.word.features.dictionary.ui.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.util.fastFilter
import androidx.compose.ui.util.fastMap

@Immutable
data class WordCombinedUi(
    val word: String,
    val wordEtymology: List<WordEtymologyUi>,
    val isFavorite: Boolean,
)

@Immutable
data class WordEtymologyUi(
    val etymologyText: String?,
    val etymologyNumber: Int?,
    val sounds: List<SoundUi>,
    val words: List<WordUi>,
)

@Immutable
data class WordUi(
    val id: Long,
    val source: String?,
    val word: String,
    val lang: String,
    val langCode: String,
    val originalTitle: String?,
    val pos: String,
    val etymologyNumber: Int?,
    val etymologyText: String?,
    val categories: List<CategoryUi> = listOf(),
    val forms: List<FormUi> = listOf(),
    val descendants: List<DescendantUi> = listOf(),
    val etymologyTemplates: List<EtymologyTemplateUi> = listOf(),
    val inflectionTemplates: List<InflectionTemplateUi> = listOf(),
    val headTemplates: List<HeadTemplateUi> = listOf(),
    val topics: List<String> = listOf(),
    val translations: List<TranslationUi> = listOf(),
    val senses: List<SenseCombinedUi> = listOf(),
    val sounds: List<SoundUi> = listOf(),
    val wikidata: List<String> = listOf(),
    val wikipedia: List<String> = listOf(),

    val abbreviations: List<RelatedUi> = listOf(),
    val altOf: List<RelatedUi> = listOf(),
    val antonyms: List<RelatedUi> = listOf(),
    val coordinateTerms: List<RelatedUi> = listOf(),
    val derived: List<RelatedUi> = listOf(),
    val formOf: List<RelatedUi> = listOf(),
    val holonyms: List<RelatedUi> = listOf(),
    val hypernyms: List<RelatedUi> = listOf(),
    val hyphenation: List<String> = listOf(),
    val hyponyms: List<RelatedUi> = listOf(),
    val instances: List<RelatedUi> = listOf(),
    val meronyms: List<RelatedUi> = listOf(),
    val proverbs: List<RelatedUi> = listOf(),
    val related: List<RelatedUi> = listOf(),
    val synonyms: List<RelatedUi> = listOf(),
    val troponyms: List<RelatedUi> = listOf(),
) {
    val thesaurus: List<Pair<String, List<RelatedUi>>> = listOf(
        "Synonyms" to synonyms,
        "Antonyms" to antonyms,
        "Hypernyms" to hypernyms,
        "Hyponyms" to hyponyms,
        "Meronyms" to meronyms,
        "Forms Of" to formOf,
        "Hyphenations" to synonyms,
        "Abbreviations" to abbreviations,
        "Alts Of" to altOf,
        "Coordinate Terms" to coordinateTerms,
        "Derived" to derived,
        "Holonyms" to holonyms,
        "Instances" to instances,
        "Proverbs" to proverbs,
        "Related" to related,
        "Troponyms" to troponyms,
    ).fastFilter { it.second.isNotEmpty() }

    val thesaurusFlatted = thesaurus.fastMap { thesaurus ->
        Pair(
            thesaurus.first,
            thesaurus.second.asSequence()
                .map { it.word }
                .filterNotNull()
                .joinToString { it }
        )
    }
}
