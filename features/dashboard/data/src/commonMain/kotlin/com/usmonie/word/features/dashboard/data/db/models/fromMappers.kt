package com.usmonie.word.features.dashboard.data.db.models

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
import io.realm.kotlin.ext.toRealmDictionary
import wtf.word.core.domain.tools.fastMap

internal fun WordDb.toDomain() = Word(
    id = _id.toHexString(),
    word = word,
    langCode = langCode,
    lang = lang,
    originalTitle = originalTitle,
    pos = pos,
    source = source,
    etymologyNumber = etymologyNumber,
    etymologyText = etymologyText,
    abbreviations = abbreviations.toDomain(),
    altOf = altOf.toDomain(),
    antonyms = antonyms.toDomain(),
    categories = categories.fastMap { it.toDomain() },
    coordinateTerms = coordinateTerms.toDomain(),
    derived = derived.toDomain(),
    descendants = descendants.fastMap { it.toDomain() },
    etymologyTemplates = etymologyTemplates.fastMap { it.toDomain() },
    formOf = formOf.toDomain(),
    forms = forms.fastMap { it.toDomain() },
    headTemplates = headTemplates.fastMap { it.toDomain() },
    holonyms = holonyms.toDomain(),
    hypernyms = hypernyms.toDomain(),
    hyphenation = hyphenation,
    hyponyms = hyponyms.toDomain(),
    inflectionTemplates = inflectionTemplates.fastMap { it.toDomain() },
    instances = instances.toDomain(),
    meronyms = meronyms.toDomain(),
    proverbs = proverbs.toDomain(),
    related = related.toDomain(),
    senses = combineSenses(senses.fastMap { it.toDomain() }),
    sounds = sounds.fastMap { it.toDomain() },
    synonyms = synonyms.toDomain(),
    topics = topics,
    translations = translations.fastMap { it.toDomain() },
    troponyms = troponyms.toDomain(),
    wikidata = wikidata,
    wikipedia = wikipedia
)

// A function that takes a list of Sense objects and returns a SenseCombined object
fun combineSenses(senses: List<Sense>): List<SenseCombined> {
    // A helper function that takes a list of Sense objects and a prefix list of glosses
    // and returns a list of SenseCombined objects that match the prefix
    fun groupByPrefix(senses: List<Sense>, prefix: List<String>): List<SenseCombined> {
        // Filter the senses that have the same prefix
        val filtered = senses.filter { it.glosses.take(prefix.size) == prefix }
        // Group the filtered senses by their next gloss after the prefix
        val grouped = filtered.groupBy { it.glosses.getOrNull(prefix.size) }
        // Map each group to a SenseCombined object with the gloss and the id of the first sense in the group
        // and recursively call the helper function with the extended prefix for the children
        return grouped.map { (gloss, group) ->
            gloss ?: return emptyList()

            val root = group
                .find { it.glosses.first() == gloss || it.glosses.last() == gloss } ?: return emptyList()

            root.toCombinedDomain(gloss, groupByPrefix(group, prefix + listOfNotNull(gloss)))
        }
    }
    // Call the helper function with an empty prefix and return the first element of the result
    return groupByPrefix(senses, emptyList())
}

internal fun List<RelatedDb>.toDomain() = map { it.toDomain() }
internal fun CategoryDb.toDomain() =
    Category(_id.toHexString(), kind, langcode, name, orig, parents, source)

internal fun DescendantDb.toDomain() =
    Descendant(_id.toHexString(), depth, tags, templates.fastMap { it.toDomain() }, text)

internal fun FormDb.toDomain() =
    Form(
        _id.toHexString(),
        form,
        headNr,
        ipa,
        roman,
        ruby.toRealm(),
        source,
        tags,
        topics
    )

internal fun RelatedDb.toDomain() = Related(
    _id.toHexString(),
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

internal fun TranslationDb.toDomain() = Translation(
    id = _id.toHexString(),
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
    word = word
)

internal fun SenseDb.toDomain() = Sense(
    id = _id.toHexString(),
    qualifier = qualifier,
    taxonomic = taxonomic,
    headNr = headNr,
    altOf = altOf.toDomain(),
    antonyms = antonyms.toDomain(),
    categories = categories.toDomain(),
    compoundOf = compoundOf.toDomain(),
    coordinateTerms = coordinateTerms.toDomain(),
    derived = derived.toDomain(),
    examples = examples.fastMap { it.toDomain() },
    formOf = formOf.toDomain(),
    glosses = glosses,
    holonyms = holonyms.toDomain(),
    hypernyms = hypernyms.toDomain(),
    hyponyms = hyponyms.toDomain(),
    instances = instances.fastMap { it.toDomain() },
    links = links.toRealm(),
    meronyms = meronyms.toDomain(),
    rawGlosses = rawGlosses,
    related = related.toDomain(),
    synonyms = synonyms.toDomain(),
    tags = tags,
    topics = topics,
    translations = translations.fastMap { it.toDomain() },
    troponyms = troponyms.toDomain(),
    wikidata = wikidata,
    wikipedia = wikipedia
)

internal fun Sense.toCombinedDomain(gloss: String, children: List<SenseCombined>) = SenseCombined(
    id = id,
    gloss = gloss,
    children = children,
    qualifier = qualifier,
    taxonomic = taxonomic,
    headNr = headNr,
    altOf = altOf,
    antonyms = antonyms,
    categories = categories,
    compoundOf = compoundOf,
    coordinateTerms = coordinateTerms,
    derived = derived,
    examples = examples,
    formOf = formOf,
    holonyms = holonyms,
    hypernyms = hypernyms,
    hyponyms = hyponyms,
    instances = instances,
    links = links.toRealm(),
    meronyms = meronyms,
    rawGlosses = rawGlosses,
    related = related,
    synonyms = synonyms,
    tags = tags,
    topics = topics,
    translations = translations,
    troponyms = troponyms,
    wikidata = wikidata,
    wikipedia = wikipedia
)

internal fun EtymologyTemplateDb.toDomain() = EtymologyTemplate(
    _id.toHexString(), args.toMap(), expansion, name
)

internal fun TemplateDb.toDomain() = Template(
    _id.toHexString(), args.toRealmDictionary(), expansion, name
)

internal fun HeadTemplateDb.toDomain() = HeadTemplate(
    _id.toHexString(), args.toRealmDictionary(), expansion, name
)

internal fun InflectionTemplateDb.toDomain() = InflectionTemplate(
    _id.toHexString(), args.toRealmDictionary(), name
)

internal fun ExampleDb.toDomain() = Example(
    id = _id.toHexString(),
    english = english,
    note = note,
    ref = ref,
    roman = roman,
    ruby = ruby.toRealm(),
    text = text,
    type = type
)

internal fun InstanceDb.toDomain() =
    Instance(_id.toHexString(), sense, source, tags, topics, word)

internal fun SoundDb.toDomain() = Sound(
    id = _id.toHexString(),
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
    zhPron = zhPron
)