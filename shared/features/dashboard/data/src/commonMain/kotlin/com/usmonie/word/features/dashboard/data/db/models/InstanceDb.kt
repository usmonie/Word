package com.usmonie.word.features.dashboard.data.db.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

internal class InstanceDb() : RealmObject {

    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var sense: String? = null
    var source: String? = null
    var tags: RealmList<String> = realmListOf()
    var topics: RealmList<String> = realmListOf()
    var word: String? = null

    constructor(
        sense: String?,
        source: String?,
        tags: RealmList<String>,
        topics: RealmList<String>,
        word: String?
    ) : this() {
        this.sense = sense
        this.source = source
        this.tags = tags
        this.topics = topics
        this.word = word
    }
}