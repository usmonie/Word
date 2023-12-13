package com.usmonie.word.features.dashboard.data.db.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

internal class WordDb() : RealmObject {

    var _id: ObjectId = BsonObjectId()
    @Index
    var word: String = ""
    @Index
    var langCode: String = ""
    var lang: String = ""
    var originalTitle: String? = null
    var pos: String = ""
    var source: String? = null
    var etymologyNumber: Int? = null
    var etymologyText: String? = null
    var abbreviations: RealmList<RelatedDb> = realmListOf()
    var altOf: RealmList<RelatedDb> = realmListOf()
    var antonyms: RealmList<RelatedDb> = realmListOf()
    var categories: RealmList<CategoryDb> = realmListOf()
    var coordinateTerms: RealmList<RelatedDb> = realmListOf()
    var derived: RealmList<RelatedDb> = realmListOf()
    var descendants: RealmList<DescendantDb> = realmListOf()
    var etymologyTemplates: RealmList<EtymologyTemplateDb> = realmListOf()
    var formOf: RealmList<RelatedDb> = realmListOf()
    var forms: RealmList<FormDb> = realmListOf()
    var headTemplates: RealmList<HeadTemplateDb> = realmListOf()
    var holonyms: RealmList<RelatedDb> = realmListOf()
    var hypernyms: RealmList<RelatedDb> = realmListOf()
    var hyphenation: RealmList<String> = realmListOf()
    var hyponyms: RealmList<RelatedDb> = realmListOf()
    var inflectionTemplates: RealmList<InflectionTemplateDb> = realmListOf()
    var instances: RealmList<RelatedDb> = realmListOf()
    var meronyms: RealmList<RelatedDb> = realmListOf()
    var proverbs: RealmList<RelatedDb> = realmListOf()
    var related: RealmList<RelatedDb> = realmListOf()
    var senses: RealmList<SenseDb> = realmListOf()
    var sounds: RealmList<SoundDb> = realmListOf()
    var synonyms: RealmList<RelatedDb> = realmListOf()
    var topics: RealmList<String> = realmListOf()
    var translations: RealmList<TranslationDb> = realmListOf()
    var troponyms: RealmList<RelatedDb> = realmListOf()
    var wikidata: RealmList<String> = realmListOf()
    var wikipedia: RealmList<String> = realmListOf()


    @PrimaryKey
    var primaryKey: String = word + pos + lang + langCode + etymologyNumber + etymologyText

    constructor(
        word: String,
        langCode: String,
        lang: String,
        originalTitle: String?,
        pos: String,
        source: String?,
        etymologyNumber: Int?,
        etymologyText: String?,
        abbreviations: RealmList<RelatedDb>,
        altOf: RealmList<RelatedDb>,
        antonyms: RealmList<RelatedDb>,
        categories: RealmList<CategoryDb>,
        coordinateTerms: RealmList<RelatedDb>,
        derived: RealmList<RelatedDb>,
        descendants: RealmList<DescendantDb>,
        etymologyTemplates: RealmList<EtymologyTemplateDb>,
        formOf: RealmList<RelatedDb>,
        forms: RealmList<FormDb>,
        headTemplates: RealmList<HeadTemplateDb>,
        holonyms: RealmList<RelatedDb>,
        hypernyms: RealmList<RelatedDb>,
        hyphenation: RealmList<String>,
        hyponyms: RealmList<RelatedDb>,
        inflectionTemplates: RealmList<InflectionTemplateDb>,
        instances: RealmList<RelatedDb>,
        meronyms: RealmList<RelatedDb>,
        proverbs: RealmList<RelatedDb>,
        related: RealmList<RelatedDb>,
        senses: RealmList<SenseDb>,
        sounds: RealmList<SoundDb>,
        synonyms: RealmList<RelatedDb>,
        topics: RealmList<String>,
        translations: RealmList<TranslationDb>,
        troponyms: RealmList<RelatedDb>,
        wikidata: RealmList<String>,
        wikipedia: RealmList<String>
    ) : this() {

        this.word = word
        this.langCode = langCode
        this.lang = lang
        this.originalTitle = originalTitle
        this.pos = pos
        this.source = source
        this.etymologyNumber = etymologyNumber
        this.etymologyText = etymologyText
        this.abbreviations = abbreviations
        this.altOf = altOf
        this.antonyms = antonyms
        this.categories = categories
        this.coordinateTerms = coordinateTerms
        this.derived = derived
        this.descendants = descendants
        this.etymologyTemplates = etymologyTemplates
        this.formOf = formOf
        this.forms = forms
        this.headTemplates = headTemplates
        this.holonyms = holonyms
        this.hypernyms = hypernyms
        this.hyphenation = hyphenation
        this.hyponyms = hyponyms
        this.inflectionTemplates = inflectionTemplates
        this.instances = instances
        this.meronyms = meronyms
        this.proverbs = proverbs
        this.related = related
        this.senses = senses
        this.sounds = sounds
        this.synonyms = synonyms
        this.topics = topics
        this.translations = translations
        this.troponyms = troponyms
        this.wikidata = wikidata
        this.wikipedia = wikipedia
        this.primaryKey = word + pos + lang + langCode + etymologyNumber + etymologyText
    }
}