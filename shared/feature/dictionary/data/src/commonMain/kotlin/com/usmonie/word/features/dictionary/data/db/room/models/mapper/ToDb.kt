package com.usmonie.word.features.dictionary.data.db.room.models.mapper

import com.usmonie.word.features.dictionary.data.api.models.WordDto
import com.usmonie.word.features.dictionary.data.db.room.models.WordDb

internal fun WordDto.toDb(): WordDb {
    return WordDb(
        word,
        langCode,
        lang,
        originalTitle,
        pos,
        source,
        etymologyNumber,
        etymologyText,
        descendants,
        forms,
        translations,
        senses,
        sounds,
        categories,
        etymologyTemplates,
        headTemplates,
        inflectionTemplates,
        topics,
        wikidata,
        wikipedia,
        abbreviations,
        altOf,
        antonyms,
        coordinateTerms,
        derived,
        formOf,
        holonyms,
        hypernyms,
        hyphenation,
        hyponyms,
        instances,
        meronyms,
        proverbs,
        related,
        synonyms,
        troponyms
    )
}
