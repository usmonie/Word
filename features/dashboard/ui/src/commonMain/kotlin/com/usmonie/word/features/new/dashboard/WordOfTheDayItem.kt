package com.usmonie.word.features.new.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.new.components.RandomWordCard
import com.usmonie.word.features.new.models.WordCombinedUi
import com.usmonie.word.features.new.models.WordUi
import com.usmonie.word.features.ui.VerticalAnimatedVisibility
import wtf.speech.core.ui.ContentState

@Composable
fun WordOfTheDayMenuItem(
    onWordClick: (WordCombinedUi) -> Unit,
    onAddFavouritePressed: (WordCombinedUi) -> Unit,
    onSharePressed: (WordCombinedUi) -> Unit,
    onUpdatePressed: () -> Unit,
    showWordOfTheDay: Boolean,
    word: ContentState<Pair<WordUi, WordCombinedUi>>
) {
    WordOfTheDay(
        showWordOfTheDay,
        onClick = onWordClick,
        onAddFavouritePressed = onAddFavouritePressed,
        onSharePressed = onSharePressed,
        onUpdatePressed = onUpdatePressed,
        modifier = Modifier,
        word = word
    )
}

@Composable
private fun WordOfTheDay(
    showItem: Boolean,
    onClick: (WordCombinedUi) -> Unit,
    onAddFavouritePressed: (WordCombinedUi) -> Unit,
    onSharePressed: (WordCombinedUi) -> Unit,
    onUpdatePressed: () -> Unit,
    word: ContentState<Pair<WordUi, WordCombinedUi>>,
    modifier: Modifier = Modifier
) {
    VerticalAnimatedVisibility(showItem) {
        Column(modifier) {
            RandomWordCard(
                onClick,
                onAddFavouritePressed,
                onSharePressed,
                onUpdatePressed,
                word,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}