package com.usmonie.word.features.new.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.new.components.TitleUiComponent
import com.usmonie.word.features.new.models.WordCombinedUi
import com.usmonie.word.features.ui.BaseCard

@Immutable
data class RecentCardsState(val words: List<WordCombinedUi>) {
    val isNotEmpty: Boolean
        get() = words.isNotEmpty()
}

@Composable
fun RecentCards(
    onWordClick: (WordCombinedUi) -> Unit,
    recentCardsState: RecentCardsState
) {
    Column {
        RecentSearchLazyRow(recentCardsState, onWordClick = onWordClick)
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
fun RecentSearchLazyRow(
    recentCardsState: RecentCardsState,
    modifier: Modifier = Modifier,
    onWordClick: (WordCombinedUi) -> Unit
) {
    LazyRow(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        items(recentCardsState.words, key = { it.word }) { wordCombinedUi ->
            WordRecentCard(
                wordCombinedUi,
                onWordClick,
                modifier = Modifier.fillParentMaxWidth(0.75f)
            )
        }
    }
}

@Composable
fun WordRecentCard(
    word: WordCombinedUi,
    onClick: (WordCombinedUi) -> Unit,
    modifier: Modifier = Modifier
) {
    BaseCard({ onClick(word) }, elevation = 2.dp, modifier) {
        Spacer(Modifier.height(8.dp))
        TitleUiComponent(
            word.word,
            Modifier.padding(horizontal = 20.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(8.dp))
    }
}