package com.usmonie.word.features.dictionary.data.db.models

import com.usmonie.core.domain.tools.fastMap
import com.usmonie.word.features.dictionary.data.api.models.CategoryDto
import com.usmonie.word.features.dictionary.data.api.models.DescendantDto
import com.usmonie.word.features.dictionary.data.api.models.EtymologyTemplateDto
import com.usmonie.word.features.dictionary.data.api.models.ExampleDto
import com.usmonie.word.features.dictionary.data.api.models.FormDto
import com.usmonie.word.features.dictionary.data.api.models.HeadTemplateDto
import com.usmonie.word.features.dictionary.data.api.models.InflectionTemplateDto
import com.usmonie.word.features.dictionary.data.api.models.InstanceDto
import com.usmonie.word.features.dictionary.data.api.models.RelatedDto
import com.usmonie.word.features.dictionary.data.api.models.SenseDto
import com.usmonie.word.features.dictionary.data.api.models.SoundDto
import com.usmonie.word.features.dictionary.data.api.models.TemplateDto
import com.usmonie.word.features.dictionary.data.api.models.TranslationDto
import com.usmonie.word.features.dictionary.data.api.models.WordDto
import io.realm.kotlin.ext.toRealmDictionary
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList

internal fun WordDto.toDatabase() = WordDb(
    word = word,
    langCode = langCode,
    lang = lang,
    originalTitle = originalTitle,
    pos = pos,
    source = source,
    etymologyNumber = etymologyNumber,
    etymologyText = etymologyText,
    abbreviations = abbreviations.toDatabase(),
    altOf = altOf.toDatabase(),
    antonyms = antonyms.toDatabase(),
    categories = categories.fastMap { it.toDatabase() }.toRealmList(),
    coordinateTerms = coordinateTerms.toDatabase(),
    derived = derived.toDatabase(),
    descendants = descendants.fastMap { it.toDatabase() }.toRealmList(),
    etymologyTemplates = etymologyTemplates.fastMap { it.toDatabase() }.toRealmList(),
    formOf = formOf.toDatabase(),
    forms = forms.fastMap { it.toDatabase() }.toRealmList(),
    headTemplates = headTemplates.fastMap { it.toDatabase() }.toRealmList(),
    holonyms = holonyms.toDatabase(),
    hypernyms = hypernyms.toDatabase(),
    hyphenation = hyphenation.toRealmList(),
    hyponyms = hyponyms.toDatabase(),
    inflectionTemplates = inflectionTemplates.fastMap { it.toDatabase() }.toRealmList(),
    instances = instances.toDatabase(),
    meronyms = meronyms.toDatabase(),
    proverbs = proverbs.toDatabase(),
    related = related.toDatabase(),
    senses = senses.fastMap { it.toDatabase() }.toRealmList(),
    sounds = sounds.fastMap { it.toDatabase() }.toRealmList(),
    synonyms = synonyms.toDatabase(),
    topics = topics.toRealmList(),
    translations = translations.fastMap { it.toDatabase() }.toRealmList(),
    troponyms = troponyms.toDatabase(),
    wikidata = wikidata.toRealmList(),
    wikipedia = wikipedia.toRealmList()
)

internal fun List<RelatedDto>.toDatabase() = map { it.toDatabase() }.toRealmList()
internal fun CategoryDto.toDatabase() =
    CategoryDb(kind, langcode, name, orig, parents.toRealmList(), source)

internal fun DescendantDto.toDatabase() =
    DescendantDb(depth, tags.toRealmList(), templates.fastMap { it.toDatabase() }.toRealmList(), text)

internal fun FormDto.toDatabase() =
    FormDb(
        form,
        headNr,
        ipa,
        roman,
        ruby.toRealm(),
        source,
        tags.toRealmList(),
        topics.toRealmList()
    )

internal fun RelatedDto.toDatabase() = RelatedDb(
    alt = alt,
    english = english,
    qualifier = qualifier,
    roman = roman,
    ruby = ruby.fastMap { it.toRealmList() }.toRealmList(),
    sense = sense,
    source = source,
    tags = tags.toRealmList(),
    taxonomic = taxonomic,
    topics = topics.toRealmList(),
    urls = urls.toRealmList(),
    word = word,
    extra = extra
)

internal fun TranslationDto.toDatabase() = TranslationDb(
    alt = alt,
    code = code,
    english = english,
    lang = lang,
    note = note,
    roman = roman,
    sense = sense,
    tags = tags.toRealmList(),
    taxonomic = taxonomic,
    topics = topics.toRealmList(),
    word = word
)

internal fun SenseDto.toDatabase() = SenseDb(
    id = id,
    qualifier = qualifier,
    taxonomic = taxonomic,
    headNr = headNr,
    altOf = altOf.toDatabase(),
    antonyms = antonyms.toDatabase(),
    categories = categories.toDatabase(),
    compoundOf = compoundOf.toDatabase(),
    coordinateTerms = coordinateTerms.toDatabase(),
    derived = derived.toDatabase(),
    examples = examples.fastMap { it.toDatabase() }.toRealmList(),
    formOf = formOf.toDatabase(),
    glosses = glosses.toRealmList(),
    holonyms = holonyms.toDatabase(),
    hypernyms = hypernyms.toDatabase(),
    hyponyms = hyponyms.toDatabase(),
    instances = instances.fastMap { it.toDatabase() }.toRealmList(),
    links = links.toRealm(),
    meronyms = meronyms.toDatabase(),
    rawGlosses = rawGlosses.toRealmList(),
    related = related.toDatabase(),
    senseid = senseid.toRealmList(),
    synonyms = synonyms.toDatabase(),
    tags = tags.toRealmList(),
    topics = topics.toRealmList(),
    translations = translations.fastMap { it.toDatabase() }.toRealmList(),
    troponyms = troponyms.toDatabase(),
    wikidata = wikidata.toRealmList(),
    wikipedia = wikipedia.toRealmList()
)

internal fun EtymologyTemplateDto.toDatabase() = EtymologyTemplateDb(
    args.toRealmDictionary(),
    expansion,
    name
)

internal fun TemplateDto.toDatabase() = TemplateDb(
    args.toRealmDictionary(),
    expansion,
    name
)

internal fun HeadTemplateDto.toDatabase() = HeadTemplateDb(
    args.toRealmDictionary(),
    expansion,
    name
)

internal fun InflectionTemplateDto.toDatabase() = InflectionTemplateDb(
    args.toRealmDictionary(),
    name
)

internal fun ExampleDto.toDatabase() = ExampleDb(
    english = english,
    note = note,
    ref = ref,
    roman = roman,
    ruby = ruby.toRealm(),
    text = text,
    type = type
)

internal fun InstanceDto.toDatabase() =
    InstanceDb(sense, source, tags.toRealmList(), topics.toRealmList(), word)

internal fun List<List<String>>.toRealm(): RealmList<RealmList<String>> =
    map { it.toRealmList() }.toRealmList()

internal fun SoundDto.toDatabase() = SoundDb(
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
    tags = tags.toRealmList(),
    text = text,
    topics = topics.toRealmList(),
    zhPron = zhPron
)
