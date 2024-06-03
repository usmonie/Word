@file:OptIn(ExperimentalSerializationApi::class)

package com.usmonie.word.features.dictionary.data.api.models

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SenseDto
@OptIn(ExperimentalSerializationApi::class, ExperimentalSerializationApi::class)
constructor(
    @SerialName("id")
    val id: String? = null,

    @SerialName("taxonomic")
    val taxonomic: String? = null,

    @SerialName("head_nr")
    val headNr: Int? = null,

    @SerialName("alt_of")
    @EncodeDefault
    val altOf: List<RelatedDto> = listOf(),

    @SerialName("antonyms")
    @EncodeDefault
    val antonyms: List<RelatedDto> = listOf(),

    @SerialName("categories")
    @EncodeDefault
    val categories: List<RelatedDto> = listOf(),

    @SerialName("compound_of")
    @EncodeDefault
    val compoundOf: List<RelatedDto> = listOf(),

    @SerialName("coordinate_terms")
    @EncodeDefault
    val coordinateTerms: List<RelatedDto> = listOf(),

    @SerialName("derived")
    @EncodeDefault
    val derived: List<RelatedDto> = listOf(),

    @SerialName("examples")
    @EncodeDefault
    val examples: List<ExampleDto> = listOf(),

    @SerialName("form_of")
    @EncodeDefault
    val formOf: List<RelatedDto> = listOf(),

    @SerialName("glosses")
    @EncodeDefault
    val glosses: List<String> = listOf(),

    @SerialName("holonyms")
    @EncodeDefault
    val holonyms: List<RelatedDto> = listOf(),

    @SerialName("hypernyms")
    @EncodeDefault
    val hypernyms: List<RelatedDto> = listOf(),

    @SerialName("hyponyms")
    @EncodeDefault
    val hyponyms: List<RelatedDto> = listOf(),

    @SerialName("instances")
    @EncodeDefault
    val instances: List<InstanceDto> = listOf(),

    @SerialName("links")
    @EncodeDefault
    val links: List<List<String>> = listOf(),

    @SerialName("meronyms")
    val meronyms: List<RelatedDto> = listOf(),

    @SerialName("qualifier")
    val qualifier: String? = null,

    @SerialName("raw_glosses")
    val rawGlosses: List<String> = listOf(),
    @SerialName("related")
    val related: List<RelatedDto> = listOf(),
    @SerialName("senseid")
    val senseid: List<String> = listOf(),
    @SerialName("synonyms")
    val synonyms: List<RelatedDto> = listOf(),
    @SerialName("tags")
    val tags: List<String> = listOf(),
    @SerialName("topics")
    val topics: List<String> = listOf(),
    @SerialName("translations")
    val translations: List<TranslationDto> = listOf(),
    @SerialName("troponyms")
    val troponyms: List<RelatedDto> = listOf(),
    @SerialName("wikidata")
    val wikidata: List<String> = listOf(),
    @SerialName("wikipedia")
    val wikipedia: List<String> = listOf()
)
