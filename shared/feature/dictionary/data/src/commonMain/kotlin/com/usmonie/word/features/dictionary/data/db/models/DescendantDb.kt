package com.usmonie.word.features.dictionary.data.db.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

internal class DescendantDb() : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var depth: Int? = null
    var tags: RealmList<String> = realmListOf()
    var templates: RealmList<TemplateDb> = realmListOf()
    var text: String? = null

    constructor(
        depth: Int?,
        tags: RealmList<String>,
        templates: RealmList<TemplateDb>,
        text: String?
    ) : this() {
        this.depth = depth
        this.tags = tags
        this.templates = templates
        this.text = text
    }
}
