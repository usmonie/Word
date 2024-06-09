package com.usmonie.word.features.games.data.db.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

internal class CategoryDb() : RealmObject {

    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var kind: String? = null
    var langcode: String? = null
    var name: String? = null
    var orig: String? = null
    var parents: RealmList<String> = realmListOf()
    var source: String? = null

    constructor(
        kind: String?,
        langcode: String?,
        name: String?,
        orig: String?,
        parents: RealmList<String>,
        source: String?
    ) : this() {
        this.kind = kind
        this.langcode = langcode
        this.name = name
        this.orig = orig
        this.parents = parents
        this.source = source
    }
}
