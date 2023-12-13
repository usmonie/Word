package com.usmonie.word.features.new.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun WordCardButtons(
    onLearnPressed: () -> Unit,
    onBookmark: () -> Unit,
    bookmarked: Boolean,
    learningEnabled: Boolean = false,
    modifier: Modifier = Modifier
) {
    // TODO: FIX PADDINGS FOR BookmarkButton
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Spacer(Modifier.width(18.dp))
        if (learningEnabled) {
            LearnButton(onLearnPressed)
            Spacer(Modifier.width(24.dp))
        }
        BookmarkButton(onBookmark, bookmarked, modifier = Modifier)
    }
}
@Composable
fun WordCardButtons(
    onLearnPressed: () -> Unit,
    onBookmark: () -> Unit,
    onUpdate: () -> Unit,
    bookmarked: Boolean,
    learningEnabled: Boolean = false,
    modifier: Modifier = Modifier
) {
    // TODO: FIX PADDINGS FOR BookmarkButton
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Spacer(Modifier.width(18.dp))
        if (learningEnabled) {
            LearnButton(onLearnPressed)
        }
        BookmarkButton(onBookmark, bookmarked)
//        Spacer(Modifier.width(24.dp))
        UpdateButton(onUpdate)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BookmarkButton(
    onBookmark: () -> Unit,
    bookmarked: Boolean,
    tint: Color = MaterialTheme.colorScheme.secondary,
    modifier: Modifier = Modifier
) {
    IconButton(onBookmark, modifier = modifier.size(24.dp)) {
        Icon(
            painterResource("drawable/" + if (bookmarked) "ic_bookmark_filled.xml" else "ic_bookmark.xml"),
            contentDescription = "update favourite state. current state is in favourite: $bookmarked",
            modifier = Modifier.size(24.dp),
            tint = tint
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LearnButton(
    onLearn: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.secondary,
    modifier: Modifier = Modifier
) {
    IconButton(onLearn, modifier = modifier) {
        Icon(
            painterResource("drawable/" + "ic_bookmark_filled.xml"),
            contentDescription = "start learn process",
            modifier = Modifier.size(24.dp),
            tint = tint
        )
    }
}

@Composable
fun UpdateButton(
    onUpdate: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.secondary,
    modifier: Modifier = Modifier
) {
    IconButton(onUpdate, modifier = modifier) {
        Icon(
            Icons.Default.Refresh,
            contentDescription = "update word card",
            modifier = Modifier.size(24.dp),
            tint = tint
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ShareButton(
    onShare: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.secondary,
) {
    IconButton(onShare, modifier = modifier) {
        Icon(
            painterResource("drawable/ic_upload.xml"),
            contentDescription = "share button",
            modifier = Modifier.size(24.dp),
            tint = tint
        )
    }
}