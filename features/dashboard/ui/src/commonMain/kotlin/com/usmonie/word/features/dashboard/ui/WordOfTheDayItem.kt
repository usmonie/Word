package com.usmonie.word.features.dashboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.models.WordUi
import com.usmonie.word.features.ui.MenuItemText
import com.usmonie.word.features.ui.VerticalAnimatedVisibility
import com.usmonie.word.features.ui.WordOfTheDayCard
import wtf.speech.core.ui.ContentState

@Composable
fun WordOfTheDayMenuItem(
    onWordClick: (WordUi) -> Unit,
    onAddFavouritePressed: (WordUi) -> Unit,
    onSharePressed: (WordUi) -> Unit,
    showWordOfTheDay: Boolean,
    word: ContentState<WordUi>,
    modifier: Modifier = Modifier
) {
    Column {
        MenuItemText("Random Word", modifier)

        WordOfTheDay(
            showWordOfTheDay,
            onClick = onWordClick,
            onAddFavouritePressed = onAddFavouritePressed,
            onSharePressed = onSharePressed,
            modifier = Modifier,
            word = word
        )
    }
}

@Composable
private fun WordOfTheDay(
    showItem: Boolean,
    onClick: (WordUi) -> Unit,
    onAddFavouritePressed: (WordUi) -> Unit,
    onSharePressed: (WordUi) -> Unit,
    word: ContentState<WordUi>,
    modifier: Modifier = Modifier
) {
    VerticalAnimatedVisibility(showItem) {
        Column(modifier) {
            Spacer(Modifier.height(6.dp))
            WordOfTheDayCard(
                onClick,
                onAddFavouritePressed,
                onSharePressed,
                word,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(14.dp))
        }
    }
}