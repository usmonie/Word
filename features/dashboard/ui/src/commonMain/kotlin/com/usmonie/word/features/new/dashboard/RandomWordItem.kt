package com.usmonie.word.features.new.dashboard

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.new.components.RandomWordCard
import com.usmonie.word.features.new.models.WordCombinedUi
import com.usmonie.word.features.new.models.WordUi
import com.usmonie.word.features.ui.MenuItem
import com.usmonie.word.features.ui.VerticalAnimatedVisibility
import wtf.speech.core.ui.ContentState

@Composable
fun RandomWordMenuItem(
    onMenuItemClick: () -> Unit,
    onWordClick: (WordCombinedUi) -> Unit,
    onAddFavouritePressed: (WordCombinedUi) -> Unit,
    onSharePressed: (WordCombinedUi) -> Unit,
    onUpdatePressed: () -> Unit,
    showRandomWord: Boolean,
    word: ContentState<Pair<WordUi, WordCombinedUi>>
) {
    Column(Modifier.animateContentSize()) {
        MenuItem(
            onMenuItemClick,
            "Random Word",
            Modifier.fillMaxWidth()
        )
        VerticalAnimatedVisibility(showRandomWord) {
            RandomWordCard(
                onWordClick,
                onAddFavouritePressed,
                onSharePressed,
                onUpdatePressed,
                word,
                modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp)
            )
        }
    }
}
