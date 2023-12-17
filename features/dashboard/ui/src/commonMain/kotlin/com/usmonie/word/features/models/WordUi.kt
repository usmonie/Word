package com.usmonie.word.features.models

import androidx.compose.runtime.Stable

@Stable
data class WordUi(
    val id: Long,
    val word: String,
    val partOfSpeech: String,
    val synonyms: List<SynonymUi>,
    val definitions: List<DefinitionUi>,
    val isFavourite: Boolean = false
)

//fun WordDomain.to(): WordUi = WordUi(
//    id,
//    word,
//    pos,
//    synonyms.fastMap { SynonymUi(it) },
//    definitions.fastMap { DefinitionUi(it) },
//    isFavourite
//)
//
//
//fun WordUi.toDomain(): WordDomain = WordDomain(
//    id,
//    word,
//    partOfSpeech,
//    synonyms.fastMap { it.word },
//    definitions.fastMap { it.text },
//    isFavourite
//)
