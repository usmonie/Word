package com.usmonie.word.features.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (learningEnabled) {
            LearnButton(onLearnPressed)
            Spacer(Modifier.width(24.dp))
        }
        BookmarkButton(onBookmark, bookmarked)

        if (nextEnabled) {
            Spacer(Modifier.width(24.dp))
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
    BaseTextButton({
        if (bookmarked()) {
            "Remove from Favorites"
        } else {
            "Add to Favorites"
        }
    }, onClick)
}
