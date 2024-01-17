package com.usmonie.word.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
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

@Composable
fun BookmarkButton(
    onBookmark: () -> Unit,
    bookmarked: Boolean,
    tint: Color = MaterialTheme.colorScheme.secondary,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable(
                onClick = onBookmark,
                enabled = true,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (bookmarked) {
            BookmarkedIcon(tint)
        } else {
            BookmarkIcon(tint)
        }
    }
}

@Composable
private fun BookmarkedIcon(tint: Color) {
    Icon(
        Icons.Filled.Bookmark,
        contentDescription = "update favourite state. current state is in favourite: true",
        modifier = Modifier.size(24.dp),
        tint = tint
    )
}

@Composable
private fun BookmarkIcon(tint: Color) {
    Icon(
        Icons.Outlined.BookmarkBorder,
        contentDescription = "update favourite state. current state is in favourite: false",
        modifier = Modifier.size(24.dp),
        tint = tint
    )
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
            Icons.Default.Leaderboard,
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