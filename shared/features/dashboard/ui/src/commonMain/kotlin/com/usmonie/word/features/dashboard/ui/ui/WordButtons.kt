package com.usmonie.word.features.dashboard.ui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WordButtons(
    onLearnPressed: () -> Unit,
    onBookmark: () -> Unit,
    onNext: () -> Unit,
    bookmarked: () -> Boolean,
    learningEnabled: Boolean = false,
    nextEnabled: Boolean = false,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
    ) {
        if (learningEnabled) {
            LearnButton(onLearnPressed)
        }
        BookmarkButton(onBookmark, bookmarked)

        if (nextEnabled) {
            NextButton(onNext)
        }
    }
}

@Composable
fun LearnButton(onClick: () -> Unit) {
    BaseTextButton({ "Add to Learn" }, onClick)
}

@Composable
fun NextButton(onClick: () -> Unit) {
    BaseTextButton({ "Next" }, onClick)
}


@Composable
fun BookmarkButton(onClick: () -> Unit, bookmarked: () -> Boolean) {
    BaseTextButton(
        { if (bookmarked()) { "Unfavorite" } else { "Favorite" } },
        onClick
    )
}
