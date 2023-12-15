package com.usmonie.word.features.new.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.new.models.WordUi
import com.usmonie.word.features.ui.BaseCard
import com.usmonie.word.features.ui.WordLargeResizableTitle
import com.usmonie.word.features.ui.WordMediumResizableTitle
import wtf.word.core.domain.tools.fastForEachIndexed

@Composable
fun DetailsWordCardLarge(
    onAudioPlayClicked: (String) -> Unit,
    onLearnClicked: (WordUi) -> Unit,
    onBookmarkedClicked: (WordUi) -> Unit,
    word: WordUi,
    bookmarked: Boolean,
    modifier: Modifier = Modifier
) {
    BaseCard(
        {},
        elevation = 8.dp,
        modifier = modifier
    ) {
        Spacer(Modifier.height(32.dp))
        WordLargeResizableTitle(word.word, Modifier.fillMaxWidth().padding(horizontal = 20.dp))
        Spacer(Modifier.height(4.dp))
        if (!word.etymologyText.isNullOrBlank()) {
            EtymologyTitle()
            Spacer(Modifier.height(12.dp))
            Text(
                word.etymologyText,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 20.dp),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(16.dp))
        }

        Pronunciations(word, onAudioPlayClicked)
        Spacer(Modifier.height(16.dp))
        WordCardButtons(
            { onLearnClicked(word) },
            { onBookmarkedClicked(word) },
            bookmarked,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
    }
}


@Composable
fun DetailsWordCardMedium(
    onAudioPlayClicked: (String) -> Unit,
    onLearnClicked: (WordUi) -> Unit,
    onBookmarkedClicked: (WordUi) -> Unit,
    word: WordUi,
    modifier: Modifier = Modifier
) {
    BaseCard(
        {},
        elevation = 8.dp,
        modifier = modifier
    ) {
        Spacer(Modifier.height(20.dp))
        WordMediumResizableTitle(word.word, Modifier.fillMaxWidth().padding(horizontal = 20.dp))
        Spacer(Modifier.height(4.dp))
        if (word.etymologyText != null) {
            EtymologyTitle()
            Spacer(Modifier.height(12.dp))
            Text(
                word.etymologyText,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 20.dp),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(16.dp))
        }

        Pronunciations(word, onAudioPlayClicked)

        Spacer(Modifier.height(16.dp))

        WordCardButtons(
            { onLearnClicked(word) },
            { onBookmarkedClicked(word) },
            false,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun Pronunciations(
    word: WordUi,
    onAudioPlayClicked: (String) -> Unit
) {
    if (word.sounds.isNotEmpty()) {
        PronunciationTitle()
        Spacer(Modifier.height(8.dp))
        word.sounds.fastForEachIndexed { i, sound ->
            val phonetic = sound.ipa ?: sound.enpr ?: ""
            if (phonetic.isNotBlank()) {
                PronunciationItem(
                    { sound.audio?.let { onAudioPlayClicked(it) } },
                    sound.tags.joinToString { it },
                    phonetic,
                    sound.audio
                )

                if (i in 0 until word.sounds.size - 1) {
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

