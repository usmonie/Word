package com.usmonie.word.features.dashboard.data.db.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


class WordFavorite(): RealmObject {

    @PrimaryKey
    var word: String = ""
    constructor(word: String) : this() {
        this.word = word
    }
}