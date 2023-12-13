package com.usmonie.word.features.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.new.models.WordCombinedUi

@Composable
fun WordsLazyRow(
    words: List<WordCombinedUi>,
    modifier: Modifier = Modifier,
    content: @Composable LazyItemScope.(WordCombinedUi) -> Unit
) {
    LazyRow(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        items(words, key = { it.word }) {
            content(it)
        }
    }
}
