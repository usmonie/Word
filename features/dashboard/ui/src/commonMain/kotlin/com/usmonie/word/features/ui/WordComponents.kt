package com.usmonie.word.features.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun WordLargeTitle(word: String, modifier: Modifier = Modifier) {
    val defaultTextStyle = MaterialTheme.typography.displayMedium
    var readyToDraw by remember(word) { mutableStateOf(false) }
    var defaultTextSize by remember { mutableStateOf(defaultTextStyle.fontSize) }

    Text(
        word,
        style = MaterialTheme.typography.displayMedium,
        textAlign = TextAlign.Center,
        fontSize = defaultTextSize,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier.drawWithContent { if (readyToDraw) drawContent() },
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = {
            if (it.hasVisualOverflow) defaultTextSize *= 0.8f
            else readyToDraw = true
        }
    )
}

@Composable
fun WordMediumTitle(word: String, modifier: Modifier = Modifier) {
    val textStyle = MaterialTheme.typography.headlineMedium

    Text(
        word,
        style = textStyle,
        textAlign = TextAlign.Start,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = {
            if (it.didOverflowWidth) textStyle.copy(fontSize = textStyle.fontSize * 0.9f)
        }
    )
}

@Composable
fun WordSmallTitle(word: String, modifier: Modifier = Modifier) {
    Text(
        word,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Start,
        modifier = modifier
    )
}

@Composable
fun PartOfSpeech(partOfSpeech: String, modifier: Modifier = Modifier) {
    Text(
        partOfSpeech,
        style = MaterialTheme.typography.labelSmall,
        modifier = modifier
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AddToFavouriteButton(
    onAddFavourite: () -> Unit,
    isFavourite: Boolean,
    tint: Color = MaterialTheme.colorScheme.secondary,
    modifier: Modifier = Modifier
) {
    IconButton(onAddFavourite, modifier = modifier) {
        Icon(
            painterResource("drawable/" + if (isFavourite) "ic_bookmark_filled.xml" else "ic_bookmark.xml"),
            contentDescription = "update favourite state. current state is in favourite: $isFavourite",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordCard(
    onClick: () -> Unit,
    elevation: Dp,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    ElevatedCard(
        onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = elevation,
            pressedElevation = (-12).dp
        ),
        enabled = enabled,
        content = content
    )
}
