package com.usmonie.word.features.dashboard.ui.models

import com.usmonie.word.features.dashboard.domain.models.Category
import com.usmonie.word.features.dashboard.domain.models.Descendant
import com.usmonie.word.features.dashboard.domain.models.EtymologyTemplate
import com.usmonie.word.features.dashboard.domain.models.Example
import com.usmonie.word.features.dashboard.domain.models.Form
import com.usmonie.word.features.dashboard.domain.models.HeadTemplate
import com.usmonie.word.features.dashboard.domain.models.InflectionTemplate
import com.usmonie.word.features.dashboard.domain.models.Instance
import com.usmonie.word.features.dashboard.domain.models.Related
import com.usmonie.word.features.dashboard.domain.models.Sense
import com.usmonie.word.features.dashboard.domain.models.SenseCombined
import com.usmonie.word.features.dashboard.domain.models.Sound
import com.usmonie.word.features.dashboard.domain.models.Template
import com.usmonie.word.features.dashboard.domain.models.Translation
import com.usmonie.word.features.dashboard.domain.models.Word
import com.usmonie.word.features.dashboard.domain.models.WordCombined
import com.usmonie.word.features.dashboard.domain.models.WordEtymology
import wtf.word.core.domain.tools.fastMap

internal fun Word.toUi() = WordUi(
    id,
    word = word,
    langCode = langCode,
    lang = lang,
    originalTitle = originalTitle,
    pos = pos,
    source = source,
    etymologyNumber = etymologyNumber,
    etymologyText = etymologyText,
    abbreviations = abbreviations.toUi(),
    altOf = altOf.toUi(),
    antonyms = antonyms.toUi(),
    categories = categories.fastMap { it.toUi() },
    coordinateTerms = coordinateTerms.toUi(),
    derived = derived.toUi(),
    descendants = descendants.fastMap { it.toUi() },
    etymologyTemplates = etymologyTemplates.fastMap { it.toUi() },
    formOf = formOf.toUi(),
    forms = forms.fastMap { it.toUi() },
    headTemplates = headTemplates.fastMap { it.toUi() },
    holonyms = holonyms.toUi(),
    hypernyms = hypernyms.toUi(),
    hyphenation = hyphenation,
    hyponyms = hyponyms.toUi(),
    inflectionTemplates = inflectionTemplates.fastMap { it.toUi() },
    instances = instances.toUi(),
    meronyms = meronyms.toUi(),
    proverbs = proverbs.toUi(),
    related = related.toUi(),
    senses = senses.fastMap { it.toUi() },
    sounds = sounds.fastMap { it.toUi() },
    synonyms = synonyms.toUi(),
    topics = topics,
    translations = translations.fastMap { it.toUi() },
    troponyms = troponyms.toUi(),
    wikidata = wikidata,
    wikipedia = wikipedia,
)

internal fun List<Related>.toUi() = map { it.toUi() }
internal fun Category.toUi() =
        CategoryUi(id, kind, langcode, name, orig, parents, source)

internal fun Descendant.toUi() =
    DescendantUi(id, depth, tags, templates.fastMap { it.toUi() }, text)

internal fun Form.toUi() =
    FormUi(
        id,
        form,
        headNr,
        ipa,
        roman,
        source,
        ruby,
        tags,
        topics
    )

internal fun Related.toUi() = RelatedUi(
    id = id,
    alt = alt,
    english = english,
    qualifier = qualifier,
    roman = roman,
    ruby = ruby.fastMap { it },
    sense = sense,
    source = source,
    tags = tags,
    taxonomic = taxonomic,
    topics = topics,
    urls = urls,
    word = word,
    extra = extra
)

internal fun Translation.toUi() = TranslationUi(
    id = id,
    alt = alt,
    code = code,
    english = english,
    lang = lang,
    note = note,
    roman = roman,
    sense = sense,
    tags = tags,
    taxonomic = taxonomic,
    topics = topics,
    word = word,
)

internal fun Sense.toUi() = SenseUi(
    id = id,
    qualifier = qualifier,
    taxonomic = taxonomic,
    headNr = headNr,
    altOf = altOf.toUi(),
    antonyms = antonyms.toUi(),
    categories = categories.toUi(),
    compoundOf = compoundOf.toUi(),
    coordinateTerms = coordinateTerms.toUi(),
    derived = derived.toUi(),
    examples = examples.fastMap { it.toUi() },
    formOf = formOf.toUi(),
    glosses = glosses,
    holonyms = holonyms.toUi(),
    hypernyms = hypernyms.toUi(),
    hyponyms = hyponyms.toUi(),
    instances = instances.fastMap { it.toUi() },
    links = links,
    meronyms = meronyms.toUi(),
    rawGlosses = rawGlosses,
    related = related.toUi(),
    synonyms = synonyms.toUi(),
    tags = tags,
    topics = topics,
    translations = translations.fastMap { it.toUi() },
    troponyms = troponyms.toUi(),
    wikidata = wikidata,
    wikipedia = wikipedia,
)

internal fun SenseCombined.toUi(): SenseCombinedUi = SenseCombinedUi(
    id = id,
    gloss = gloss,
    children = children.fastMap { it.toUi() },
    qualifier = qualifier,
    taxonomic = taxonomic,
    headNr = headNr,
    altOf = altOf.toUi(),
    antonyms = antonyms.toUi(),
    categories = categories.toUi(),
    compoundOf = compoundOf.toUi(),
    coordinateTerms = coordinateTerms.toUi(),
    derived = derived.toUi(),
    examples = examples.fastMap { it.toUi() },
    formOf = formOf.toUi(),
    holonyms = holonyms.toUi(),
    hypernyms = hypernyms.toUi(),
    hyponyms = hyponyms.toUi(),
    instances = instances.fastMap { it.toUi() },
    links = links,
    meronyms = meronyms.toUi(),
    rawGlosses = rawGlosses,
    related = related.toUi(),
    synonyms = synonyms.toUi(),
    tags = tags,
    topics = topics,
    translations = translations.fastMap { it.toUi() },
    troponyms = troponyms.toUi(),
    wikidata = wikidata,
    wikipedia = wikipedia,
)

internal fun EtymologyTemplate.toUi() = EtymologyTemplateUi(
    id, expansion, name
)

internal fun Template.toUi() = TemplateUi(id, expansion, name)

internal fun HeadTemplate.toUi() = HeadTemplateUi(id, expansion, name)

internal fun InflectionTemplate.toUi() = InflectionTemplateUi(id, name)

internal fun Example.toUi() = ExampleUi(
    id = id,
    english = english,
    note = note,
    ref = ref,
    roman = roman,
    ruby = ruby,
    text = text,
    type = type,
)

internal fun Instance.toUi() = InstanceUi(id, sense, source, word, tags, topics)

internal fun Sound.toUi() = SoundUi(
    id = id,
    audio = audio,
    audioIpa = audio,
    enpr = enpr,
    form = form,
    homophone = homophone,
    ipa = ipa,
    mp3Url = mp3Url,
    note = note,
    oggUrl = oggUrl,
    other = other,
    rhymes = rhymes,
    tags = tags,
    text = text,
    topics = topics,
    zhPron = zhPron,
)

internal fun WordCombined.toUi(): WordCombinedUi {
    return WordCombinedUi(word, wordEtymology.fastMap { it.toUi() }, isFavorite,)
}

internal fun WordEtymology.toUi(): WordEtymologyUi {
    return WordEtymologyUi(
        etymologyText,
        etymologyNumber,
        sounds.fastMap { it.toUi() },
        words.fastMap { it.toUi() },
    )
}