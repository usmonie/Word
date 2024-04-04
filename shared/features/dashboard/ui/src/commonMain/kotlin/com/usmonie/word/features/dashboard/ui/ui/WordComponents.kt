package com.usmonie.word.features.dashboard.ui.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import wtf.word.core.design.themes.icons.myiconpack.IcUpload
import org.jetbrains.compose.resources.ExperimentalResourceApi
import wtf.word.core.design.themes.icons.MyIconPack

@Composable
fun LargeResizableTitle(word: String, modifier: Modifier = Modifier, color: Color) {
    val defaultTextStyle = MaterialTheme.typography.displayLarge
    var readyToDraw by remember(word) { mutableStateOf(false) }
    var defaultTextSize by remember { mutableStateOf(defaultTextStyle.fontSize) }
    var maxLines by remember(word) { mutableStateOf(1) }

    Text(
        word,
        style = defaultTextStyle,
        textAlign = TextAlign.Center,
        fontSize = defaultTextSize,
        color = color,
        modifier = modifier.drawWithContent { if (readyToDraw) drawContent() },
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = {
            when {
                it.hasVisualOverflow && defaultTextSize > 20.sp -> {
                    defaultTextSize *= 0.8f
                }

                it.hasVisualOverflow && defaultTextSize <= 20.sp -> {
                    maxLines++
                }

                else -> {
                    readyToDraw = true
                }
            }
        }
    )
}

@Composable
fun WordLargeResizableTitle(
    word: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    val defaultTextStyle = MaterialTheme.typography.displayMedium
    var readyToDraw by remember(word) { mutableStateOf(false) }
    var defaultTextSize by remember { mutableStateOf(defaultTextStyle.fontSize) }
    var maxLines by remember(word) { mutableStateOf(1) }

    Text(
        word,
        style = defaultTextStyle,
        textAlign = textAlign,
        fontSize = defaultTextSize,
        color = color,
        modifier = modifier.drawWithContent { if (readyToDraw) drawContent() },
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = {
            when {
                it.hasVisualOverflow && defaultTextSize > 20.sp -> {
                    defaultTextSize *= 0.8f
                }

                it.hasVisualOverflow && defaultTextSize <= 20.sp -> {
                    maxLines++
                }

                else -> {
                    readyToDraw = true
                }
            }
        }
    )
}

@Composable
fun WordMediumResizableTitle(
    word: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    textAlign: TextAlign = TextAlign.Start
) {
    val defaultTextStyle = MaterialTheme.typography.headlineMedium
    var readyToDraw by remember(word) { mutableStateOf(false) }
    var defaultTextSize by remember { mutableStateOf(defaultTextStyle.fontSize) }
    var maxLines by remember(word) { mutableStateOf(1) }

    Text(
        word,
        style = defaultTextStyle,
        textAlign = textAlign,
        fontSize = defaultTextSize,
        color = color,
        modifier = modifier.drawWithContent { if (readyToDraw) drawContent() },
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = {
            when {
                it.hasVisualOverflow && defaultTextSize > 20.sp -> {
                    defaultTextSize *= 0.8f
                }

                it.hasVisualOverflow && defaultTextSize <= 20.sp -> {
                    maxLines++
                }

                else -> {
                    readyToDraw = true
                }
            }
        }
    )
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
            MyIconPack.IcUpload,
            contentDescription = "share button",
            modifier = Modifier.size(24.dp),
            tint = tint
        )
    }
}
