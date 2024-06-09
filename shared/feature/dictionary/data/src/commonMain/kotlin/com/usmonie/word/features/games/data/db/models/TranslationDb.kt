package com.usmonie.word.features.games.data.db.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

internal class TranslationDb() : RealmObject {

    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var alt: String? = null
    var code: String? = null
    var english: String? = null
    var lang: String? = null
    var note: String? = null
    var roman: String? = null
    var sense: String? = null
    var tags: RealmList<String> = realmListOf()
    var taxonomic: String? = null
    var topics: RealmList<String> = realmListOf()
    var word: String? = null

    constructor(
        alt: String?,
        code: String?,
        english: String?,
        lang: String?,
        note: String?,
        roman: String?,
        sense: String?,
        tags: RealmList<String>,
        taxonomic: String?,
        topics: RealmList<String>,
        word: String?
    ) : this() {
        this.alt = alt
        this.code = code
        this.english = english
        this.lang = lang
        this.note = note
        this.roman = roman
        this.sense = sense
        this.tags = tags
        this.taxonomic = taxonomic
        this.topics = topics
        this.word = word
    }
}
