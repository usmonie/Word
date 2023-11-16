package com.usmonie.word.features.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.models.WordDomain

@Stable
@Immutable
data class WordUi(
    val id: Long,
    val word: String,
    val partOfSpeech: String,
    val synonyms: List<SynonymUi>,
    val definitions: List<DefinitionUi>,
    val isFavourite: Boolean = false
)

fun WordDomain.to(): WordUi = WordUi(
    id,
    word,
    pos,
    synonyms.map { SynonymUi(it) },
    definitions.map { DefinitionUi(it) },
    isFavourite
)


fun WordUi.toDomain(): WordDomain = WordDomain(
    id,
    word,
    partOfSpeech,
    synonyms.map { it.word },
    definitions.map { it.text },
    isFavourite
)
