package com.usmonie.word.features.games.data.db.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

internal class ExampleDb constructor(): RealmObject {

    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var english: String? = null
    var note: String? = null
    var ref: String? = null
    var roman: String? = null
    @Ignore
    var ruby: RealmList<RealmList<String>> = realmListOf()
    var text: String? = null
    var type: String? = null
    constructor(
        english: String?,
        note: String?,
        ref: String?,
        roman: String?,
        ruby: RealmList<RealmList<String>>,
        text: String?,
        type: String?
    ) : this() {
        this.english = english
        this.note = note
        this.ref = ref
        this.roman = roman
        this.ruby = ruby
        this.text = text
        this.type = type
    }
}