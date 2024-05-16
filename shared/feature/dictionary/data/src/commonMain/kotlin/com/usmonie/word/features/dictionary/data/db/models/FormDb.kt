package com.usmonie.word.features.dictionary.data.db.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

internal class FormDb() : RealmObject {

    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var form: String = ""
    var headNr: Int? = null
    var ipa: String? = null
    var roman: String? = null

    @Ignore
    var ruby: RealmList<RealmList<String>> = realmListOf()
    var source: String? = null
    var tags: RealmList<String> = realmListOf()
    var topics: RealmList<String> = realmListOf()

    constructor(
        form: String,
        headNr: Int?,
        ipa: String?,
        roman: String?,
        ruby: RealmList<RealmList<String>>,
        source: String?,
        tags: RealmList<String>,
        topics: RealmList<String>
    ) : this() {
        this.form = form
        this.headNr = headNr
        this.ipa = ipa
        this.roman = roman
        this.ruby = ruby
        this.source = source
        this.tags = tags
        this.topics = topics
    }
}
