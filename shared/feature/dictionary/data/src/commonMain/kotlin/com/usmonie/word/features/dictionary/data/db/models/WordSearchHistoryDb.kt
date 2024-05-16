package com.usmonie.word.features.dictionary.data.db.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.Clock

class WordSearchHistoryDb() : RealmObject {

    @PrimaryKey
    var word: String = ""

    var date: Long = Clock.System.now().epochSeconds

    constructor(word: String) : this() {
        this.word = word
    }
}
