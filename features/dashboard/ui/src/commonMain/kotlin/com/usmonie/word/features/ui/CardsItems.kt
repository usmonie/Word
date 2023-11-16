package com.usmonie.word.features.ui

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import com.usmonie.word.features.models.SynonymUi
import com.usmonie.word.features.models.WordUi

fun LazyListScope.words(
    onOpenWordPressed: (WordUi) -> Unit,
    onUpdateFavouriteWordPressed: (WordUi) -> Unit,
    onShareWordPressed: (WordUi) -> Unit,
    onSynonymPressed: (SynonymUi) -> Unit,
    words: List<WordUi>,
    cardModifier: Modifier = Modifier
) {
    items(words, key = { word -> word.id }) { word ->
        WordCard(
            word,
            onOpenWordPressed,
            onUpdateFavouriteWordPressed,
            onShareWordPressed,
            onSynonymPressed,
            modifier = cardModifier
        )
    }
}