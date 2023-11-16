package wtf.word.core.domain.models

data class Word(
    val id: Long,
    val word: String,
    val partOfSpeech: String,
    val synonyms: List<String>,
    val definitions: List<Definition>,
    val isFavourite: Boolean
)
