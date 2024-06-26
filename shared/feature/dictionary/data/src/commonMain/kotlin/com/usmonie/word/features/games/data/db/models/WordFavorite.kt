package com.usmonie.word.features.games.data.db.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.Clock

class WordFavorite() : RealmObject {

    @PrimaryKey
    var word: String = ""

    var date: Long = Clock.System.now().epochSeconds

    constructor(word: String) : this() {
        this.word = word
    }
}
