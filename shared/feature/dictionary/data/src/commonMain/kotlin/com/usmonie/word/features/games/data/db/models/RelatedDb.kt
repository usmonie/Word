package com.usmonie.word.features.games.data.db.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId


internal class RelatedDb() : RealmObject {

    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var alt: String? = null
    var english: String? = null
    var qualifier: String? = null
    var roman: String? = null
    @Ignore
    var ruby: RealmList<RealmList<String>> = realmListOf()
    var sense: String? = null
    var source: String? = null
    var tags: RealmList<String> = realmListOf()
    var taxonomic: String? = null
    var topics: RealmList<String> = realmListOf()
    var urls: RealmList<String> = realmListOf()
    var word: String? = null
    var extra: String? = null

    constructor(
        alt: String?,
        english: String?,
        qualifier: String?,
        roman: String?,
        ruby: RealmList<RealmList<String>>,
        sense: String?,
        source: String?,
        tags: RealmList<String>,
        taxonomic: String?,
        topics: RealmList<String>,
        urls: RealmList<String>,
        word: String?,
        extra: String?
    ) : this() {
        this.alt = alt
        this.english = english
        this.qualifier = qualifier
        this.roman = roman
        this.ruby = ruby
        this.sense = sense
        this.source = source
        this.tags = tags
        this.taxonomic = taxonomic
        this.topics = topics
        this.urls = urls
        this.word = word
        this.extra = extra
    }
}
