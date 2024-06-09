package com.usmonie.word.features.games.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.usmonie.core.domain.tools.fastForEach
import com.usmonie.word.features.games.ui.models.WordEtymologyUi
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.dictionary.ui.generated.resources.Res
import word.shared.feature.dictionary.ui.generated.resources.vocabulary_word_pronunciation

private val modifierFillMax = Modifier.fillMaxWidth()

@Composable
fun Pronunciations(
    wordEtymologyUi: () -> WordEtymologyUi,
    expanded: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        val etymology = wordEtymologyUi()
        etymology.sounds.take(if (expanded) Int.MAX_VALUE else 2).fastForEach { sound ->
            PronunciationItem(
                {},
                sound.tags,
                sound.transcription ?: return,
                null
            )
        }
    }
}

@Composable
fun Pronunciations(
    wordEtymologyUi: () -> WordEtymologyUi,
    modifier: Modifier = Modifier
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            stringResource(Res.string.vocabulary_word_pronunciation),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        val etymology = wordEtymologyUi()
        etymology.sounds.fastForEach { sound ->
            PronunciationItem(
                {},
                sound.tags,
                sound.transcription ?: return,
                null
            )
        }
    }
}

@Composable
fun PronunciationItem(
    onPlayClick: () -> Unit,
    pronunciation: String,
    phonetic: String,
    audio: String?
) {
    val titleMediumStyle = MaterialTheme.typography.titleMedium.toSpanStyle()
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
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
        modifierFillMax,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val showPlayButton = audio != null
        Text(text)

        if (showPlayButton) {
            IconButton(onPlayClick, modifier = Modifier.size(24.dp)) {
                Icon(
                    rememberVectorPainter(Icons.Default.PlayArrow),
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            }
        }
    }
}
