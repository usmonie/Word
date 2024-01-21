package com.usmonie.word.features

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp


@Composable
fun PronunciationTitle() {
    TitleUiComponent(
        "Pronunciation",
        Modifier.padding(horizontal = 24.dp),
        color = MaterialTheme.colorScheme.onSurface,
    )
}

@Composable
fun PronunciationItem(
    onPlayClick: () -> Unit,
    pronunciation: String,
    phonetic: String,
    audio: String?
) {
    val titleMediumStyle = MaterialTheme.typography.titleMedium.toSpanStyle()
    val onSurfaceColor = MaterialTheme.colorScheme.onSurfaceVariant
    val labelLargeStyle = MaterialTheme.typography.labelLarge.toSpanStyle()
    val text = remember(pronunciation, phonetic) {
        buildAnnotatedString {
            if (pronunciation.isNotBlank()) {
                withStyle(titleMediumStyle.copy(color = onSurfaceColor)) {
                    append(pronunciation)
                    append(": ")
                }
            }

            withStyle(labelLargeStyle.copy(color = onSurfaceColor)) {
                append(phonetic)
            }
        }
    }

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val showPlayButton = audio != null
        Text(
            text,
            Modifier.padding(start = 24.dp, end = if (showPlayButton) 12.dp else 24.dp).weight(1f)
        )

        if (showPlayButton) {
            IconButton(
                onPlayClick,
                modifier = Modifier.padding(end = 24.dp).size(24.dp)
            ) {
                Icon(
                    Icons.Default.PlayArrow,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = null
                )
            }
        }
    }
}