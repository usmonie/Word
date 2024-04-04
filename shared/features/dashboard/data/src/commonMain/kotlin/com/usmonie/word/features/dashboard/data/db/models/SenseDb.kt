package com.usmonie.word.features.dashboard.data.db.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId


internal class SenseDb() : RealmObject {

    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var id: String? = null
    var qualifier: String? = null
    var taxonomic: String? = null
    var headNr: Int? = null

    var altOf: RealmList<RelatedDb> = realmListOf()
    var antonyms: RealmList<RelatedDb> = realmListOf()
    var categories: RealmList<RelatedDb> = realmListOf()
    var compoundOf: RealmList<RelatedDb> = realmListOf()
    var coordinateTerms: RealmList<RelatedDb> = realmListOf()
    var derived: RealmList<RelatedDb> = realmListOf()
    var examples: RealmList<ExampleDb> = realmListOf()
    var formOf: RealmList<RelatedDb> = realmListOf()
    var glosses: RealmList<String> = realmListOf()
    var holonyms: RealmList<RelatedDb> = realmListOf()
    var hypernyms: RealmList<RelatedDb> = realmListOf()
    var hyponyms: RealmList<RelatedDb> = realmListOf()
    var instances: RealmList<InstanceDb> = realmListOf()
    @Ignore
    var links: RealmList<RealmList<String>> = realmListOf()
    var meronyms: RealmList<RelatedDb> = realmListOf()
    var rawGlosses: RealmList<String> = realmListOf()
    var related: RealmList<RelatedDb> = realmListOf()
    var senseid: RealmList<String> = realmListOf()
    var synonyms: RealmList<RelatedDb> = realmListOf()
    var tags: RealmList<String> = realmListOf()
    var topics: RealmList<String> = realmListOf()
    var translations: RealmList<TranslationDb> = realmListOf()
    var troponyms: RealmList<RelatedDb> = realmListOf()
    var wikidata: RealmList<String> = realmListOf()
    var wikipedia: RealmList<String> = realmListOf()

    constructor(
        id: String?,
        qualifier: String?,
        taxonomic: String?,
        headNr: Int?,
        altOf: RealmList<RelatedDb>,
        antonyms: RealmList<RelatedDb>,
        categories: RealmList<RelatedDb>,
        compoundOf: RealmList<RelatedDb>,
        coordinateTerms: RealmList<RelatedDb>,
        derived: RealmList<RelatedDb>,
        examples: RealmList<ExampleDb>,
        formOf: RealmList<RelatedDb>,
        glosses: RealmList<String>,
        holonyms: RealmList<RelatedDb>,
        hypernyms: RealmList<RelatedDb>,
        hyponyms: RealmList<RelatedDb>,
        instances: RealmList<InstanceDb>,
        links: RealmList<RealmList<String>>,
        meronyms: RealmList<RelatedDb>,
        rawGlosses: RealmList<String>,
        related: RealmList<RelatedDb>,
        senseid: RealmList<String>,
        synonyms: RealmList<RelatedDb>,
        tags: RealmList<String>,
        topics: RealmList<String>,
        translations: RealmList<TranslationDb>,
        troponyms: RealmList<RelatedDb>,
        wikidata: RealmList<String>,
        wikipedia: RealmList<String>
    ) : this() {

        this.id = id
        this.qualifier = qualifier
        this.taxonomic = taxonomic
        this.headNr = headNr
        this.altOf = altOf
        this.antonyms = antonyms
        this.categories = categories
        this.compoundOf = compoundOf
        this.coordinateTerms = coordinateTerms
        this.derived = derived
        this.examples = examples
        this.formOf = formOf
        this.glosses = glosses
        this.holonyms = holonyms
        this.hypernyms = hypernyms
        this.hyponyms = hyponyms
        this.instances = instances
        this.links = links
        this.meronyms = meronyms
        this.rawGlosses = rawGlosses
        this.related = related
        this.senseid = senseid
        this.synonyms = synonyms
        this.tags = tags
        this.topics = topics
        this.translations = translations
        this.troponyms = troponyms
        this.wikidata = wikidata
        this.wikipedia = wikipedia


    }
}