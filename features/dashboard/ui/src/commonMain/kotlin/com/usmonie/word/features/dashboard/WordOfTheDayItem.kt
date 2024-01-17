package com.usmonie.word.features.dashboard

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.WordOfTheDayCard
import com.usmonie.word.features.models.WordCombinedUi
import com.usmonie.word.features.models.WordUi
import com.usmonie.word.features.ui.VerticalAnimatedVisibility
import wtf.speech.core.ui.ContentState

@Composable
fun WordOfTheDayMenuItem(
    onWordClick: (WordCombinedUi) -> Unit,
    onAddFavouritePressed: (WordCombinedUi) -> Unit,
    onSharePressed: (WordCombinedUi) -> Unit,
    showWordOfTheDay: Boolean,
    word: () -> ContentState<Pair<WordUi, WordCombinedUi>>
) {
    WordOfTheDay(
        showWordOfTheDay,
        onClick = onWordClick,
        onAddFavouritePressed = onAddFavouritePressed,
        onSharePressed = onSharePressed,
        word = word(),
        modifier = Modifier.animateContentSize()
    )
}

@Composable
private fun WordOfTheDay(
    showItem: Boolean,
    onClick: (WordCombinedUi) -> Unit,
    onAddFavouritePressed: (WordCombinedUi) -> Unit,
    onSharePressed: (WordCombinedUi) -> Unit,
    word: ContentState<Pair<WordUi, WordCombinedUi>>,
    modifier: Modifier = Modifier
) {
    VerticalAnimatedVisibility(showItem) {
        WordOfTheDayCard(
            onClick,
            onAddFavouritePressed,
            onSharePressed,
            word,
            modifier = modifier.animateContentSize().padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        )
    }
}