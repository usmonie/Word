package com.usmonie.word.features.dictionary.data.db.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

internal class SoundDb() : RealmObject {

    var _id: ObjectId = BsonObjectId()
    var audio: String? = null
    var audioIpa: String? = null
    var enpr: String? = null
    var form: String? = null
    var homophone: String? = null
    var ipa: String? = null
    var mp3Url: String? = null
    var note: String? = null
    var oggUrl: String? = null
    var other: String? = null
    var rhymes: String? = null
    var tags: RealmList<String> = realmListOf()
    var text: String? = null
    var topics: RealmList<String> = realmListOf()
    var zhPron: String? = null

    @PrimaryKey
    var primaryKey: String = primary()

    constructor(
        audio: String?,
        audioIpa: String?,
        enpr: String?,
        form: String?,
        homophone: String?,
        ipa: String?,
        mp3Url: String?,
        note: String?,
        oggUrl: String?,
        other: String?,
        rhymes: String?,
        tags: RealmList<String>,
        text: String?,
        topics: RealmList<String>,
        zhPron: String?
    ) : this() {
        this.audio = audio
        this.audioIpa = audioIpa
        this.enpr = enpr
        this.form = form
        this.homophone = homophone
        this.ipa = ipa
        this.mp3Url = mp3Url
        this.note = note
        this.oggUrl = oggUrl
        this.other = other
        this.rhymes = rhymes
        this.tags = tags
        this.text = text
        this.topics = topics
        this.zhPron = zhPron

        primaryKey = primary()
    }

    fun primary(): String {
        return audio + audioIpa + enpr +
            form + homophone + ipa +
            mp3Url + note + oggUrl + other +
            rhymes + text + zhPron + tags.toString() + topics
    }
}
