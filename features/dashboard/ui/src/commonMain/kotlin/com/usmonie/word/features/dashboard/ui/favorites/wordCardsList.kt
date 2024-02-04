package com.usmonie.word.features.dashboard.ui.favorites

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import com.usmonie.word.features.dashboard.ui.SearchWordCard
import com.usmonie.word.features.dashboard.ui.models.WordCombinedUi


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
            getWordCombined = { word },
            modifier = cardModifier
        )
    }
}