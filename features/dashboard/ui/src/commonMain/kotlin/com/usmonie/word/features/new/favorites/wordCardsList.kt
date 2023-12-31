package com.usmonie.word.features.new.favorites

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import com.usmonie.word.features.new.components.SearchWordCard
import com.usmonie.word.features.new.models.WordCombinedUi


fun LazyListScope.wordsCardsList(
    onOpenWordPressed: (WordCombinedUi) -> Unit,
    onUpdateFavouriteWordPressed: (WordCombinedUi) -> Unit,
    onShareWordPressed: (WordCombinedUi) -> Unit,
//    onSynonymPressed: (SynonymUi) -> Unit,
    words: List<WordCombinedUi>,
    cardModifier: Modifier = Modifier
) {
    items(words, key = { word -> word.word }) { word ->
        SearchWordCard(
            onOpenWordPressed,
            onUpdateFavouriteWordPressed,
            onShareWordPressed,
            {},
//            onSynonymPressed,
            wordCombined = word,
            modifier = cardModifier
        )
    }
}