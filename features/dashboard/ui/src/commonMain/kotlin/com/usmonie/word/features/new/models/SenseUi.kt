package com.usmonie.word.features.new.models

import com.usmonie.word.features.dashboard.domain.models.Sense

data class SenseUi(
    val id: String,
    val headNr: Int?,
    val qualifier: String?,
    val taxonomic: String?,
    val altOf: List<RelatedUi> = listOf(),
    val antonyms: List<RelatedUi> = listOf(),
    val categories: List<RelatedUi> = listOf(),
    val compoundOf: List<RelatedUi> = listOf(),
    val coordinateTerms: List<RelatedUi> = listOf(),
    val derived: List<RelatedUi> = listOf(),
    val examples: List<ExampleUi> = listOf(),
    val formOf: List<RelatedUi> = listOf(),
    val children: List<SenseUi> = listOf(),
    val holonyms: List<RelatedUi> = listOf(),
    val hypernyms: List<RelatedUi> = listOf(),
    val hyponyms: List<RelatedUi> = listOf(),
    val instances: List<InstanceUi> = listOf(),
    val glosses: List<String> = listOf(),
    val links: List<List<String>> = listOf(),
    val meronyms: List<RelatedUi> = listOf(),
    val rawGlosses: List<String> = listOf(),
    val related: List<RelatedUi> = listOf(),
    val synonyms: List<RelatedUi> = listOf(),
    val tags: List<String> = listOf(),
    val topics: List<String> = listOf(),
    val translations: List<TranslationUi> = listOf(),
    val troponyms: List<RelatedUi> = listOf(),
    val wikidata: List<String> = listOf(),
    val wikipedia: List<String> = listOf(),
    val sense: Sense
)

data class Gloss(val text: String, val children: List<Gloss>, val examples: List<ExampleUi>)