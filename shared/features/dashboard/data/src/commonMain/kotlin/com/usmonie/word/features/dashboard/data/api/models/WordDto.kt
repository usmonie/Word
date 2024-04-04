package com.usmonie.word.features.dashboard.data.api.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class WordDto(
    @SerialName("word")
    val word: String,
    @SerialName("lang")
    val lang: String,
    @SerialName("lang_code")
    val langCode: String,
    @SerialName("pos")
    val pos: String,
    @SerialName("etymology_number")
    val etymologyNumber: Int? = null,
    @SerialName("etymology_text")
    val etymologyText: String? = null,
    @SerialName("source")
    val source: String? = null,
    @SerialName("abbreviations")
    val abbreviations: List<RelatedDto> = listOf(),
    @SerialName("alt_of")
    val altOf: List<RelatedDto> = listOf(),
    @SerialName("antonyms")
    val antonyms: List<RelatedDto> = listOf(),
    @SerialName("categories")
    val categories: List<CategoryDto> = listOf(),
    @SerialName("coordinate_terms")
    val coordinateTerms: List<RelatedDto> = listOf(),
    @SerialName("derived")
    val derived: List<RelatedDto> = listOf(),
    @SerialName("descendants")
    val descendants: List<DescendantDto> = listOf(),
    @SerialName("etymology_templates")
    val etymologyTemplates: List<EtymologyTemplateDto> = listOf(),
    @SerialName("form_of")
    val formOf: List<RelatedDto> = listOf(),
    @SerialName("forms")
    val forms: List<FormDto> = listOf(),
    @SerialName("head_templates")
    val headTemplates: List<HeadTemplateDto> = listOf(),
    @SerialName("holonyms")
    val holonyms: List<RelatedDto> = listOf(),
    @SerialName("hypernyms")
    val hypernyms: List<RelatedDto> = listOf(),
    @SerialName("hyphenation")
    val hyphenation: List<String> = listOf(),
    @SerialName("hyponyms")
    val hyponyms: List<RelatedDto> = listOf(),
    @SerialName("inflection_templates")
    val inflectionTemplates: List<InflectionTemplateDto> = listOf(),
    @SerialName("instances")
    val instances: List<RelatedDto> = listOf(),
    @SerialName("meronyms")
    val meronyms: List<RelatedDto> = listOf(),
    @SerialName("original_title")
    val originalTitle: String? = null,
    @SerialName("proverbs")
    val proverbs: List<RelatedDto> = listOf(),
    @SerialName("related")
    val related: List<RelatedDto> = listOf(),
    @SerialName("senses")
    val senses: List<SenseDto> = listOf(),
    @SerialName("sounds")
    val sounds: List<SoundDto> = listOf(),
    @SerialName("synonyms")
    val synonyms: List<RelatedDto> = listOf(),
    @SerialName("topics")
    val topics: List<String> = listOf(),
    @SerialName("translations")
    val translations: List<TranslationDto> = listOf(),
    @SerialName("troponyms")
    val troponyms: List<RelatedDto> = listOf(),
    @SerialName("wikidata")
    val wikidata: List<String> = listOf(),
    @SerialName("wikipedia")
    val wikipedia: List<String> = listOf(),
)