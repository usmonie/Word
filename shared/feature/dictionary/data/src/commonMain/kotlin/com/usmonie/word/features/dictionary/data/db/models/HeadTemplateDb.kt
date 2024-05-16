package com.usmonie.word.features.dictionary.data.db.models

import io.realm.kotlin.ext.realmDictionaryOf
import io.realm.kotlin.types.RealmDictionary
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

internal class HeadTemplateDb() : RealmObject {
    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var args: RealmDictionary<String?> = realmDictionaryOf()
    var expansion: String? = null
    var name: String? = null

    constructor(
        args: RealmDictionary<String?>,
        expansion: String?,
        name: String?,
    ) : this() {
        this.args = args
        this.expansion = expansion
        this.name = name
    }
}
