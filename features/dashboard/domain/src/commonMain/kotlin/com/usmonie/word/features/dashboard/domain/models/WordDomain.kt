package com.usmonie.word.features.dashboard.domain.models

import kotlinx.serialization.Serializable

data class WordDomain(
    val id: Long,
    val word: String,
    val pos: String,
    val synonyms: List<String> = listOf(),
    val definitions: List<String> = listOf(),
    val isFavourite: Boolean = false
)
@Serializable
data class WordParcelable(
    val word: String,
    val pos: String,
    val synonyms: String? = null,
    val definitions: List<String> = listOf(),
    val isFavourite: Boolean = false
)