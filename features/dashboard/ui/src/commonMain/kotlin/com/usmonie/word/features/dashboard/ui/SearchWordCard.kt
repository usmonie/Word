package com.usmonie.word.features.dashboard.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.ui.models.SoundUi
import com.usmonie.word.features.dashboard.ui.models.WordCombinedUi
import com.usmonie.word.features.dashboard.ui.ui.WordButtons
import com.usmonie.word.features.dashboard.ui.ui.WordMediumResizableTitle
import wtf.speech.core.ui.BaseCard
import wtf.word.core.domain.tools.fastForEach
import wtf.word.core.domain.tools.fastForEachIndexed

@Composable
fun SearchWordCard(
    onCardClick: (WordCombinedUi) -> Unit,
    onLearnClick: (WordCombinedUi) -> Unit,
    onBookmarkClick: (WordCombinedUi) -> Unit,
    onPlayClick: (SoundUi) -> Unit,
    getWordCombined: () -> WordCombinedUi,
    modifier: Modifier = Modifier
) {
    val wordCombined = getWordCombined()
    val (selectedEtymologyTabIndex, onSelectedTab) = remember(wordCombined.word) { mutableStateOf(0) }
    val selectedEtymology = wordCombined.wordEtymology[selectedEtymologyTabIndex]
    val (selectedPosIndex, onSelectedPos) = remember(
        wordCombined.word,
        selectedEtymologyTabIndex
    ) { mutableStateOf(0) }

    val selectedPos = remember(selectedPosIndex, wordCombined.word) {
        selectedEtymology.words[selectedPosIndex]
    }

    BaseCard(onClick = { onCardClick(wordCombined) }, modifier = modifier) {
        Spacer(Modifier.height(24.dp))
        WordMediumResizableTitle(
            wordCombined.word,
            Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        )

        WordButtons(
            { onLearnClick(wordCombined) },
            { onBookmarkClick(wordCombined) },
            {},
            { wordCombined.isFavorite },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        )

        if (wordCombined.wordEtymology.size > 1) {
            EtymologyTitle()
            Spacer(Modifier.height(8.dp))
            ScrollableTabRow(
                selectedEtymologyTabIndex,
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ) {
                wordCombined.wordEtymology.fastForEachIndexed { index, _ ->
                    Tab(
                        selected = selectedEtymologyTabIndex == index,
                        onClick = { onSelectedTab(index) },
                    ) {
                        Text(
                            "Root ${index + 1}",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 10.dp, bottom = 12.dp)
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }

        if (!selectedEtymology.etymologyText.isNullOrEmpty()) {
            Text(
                selectedEtymology.etymologyText,
                maxLines = 3,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 24.dp),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(16.dp))
        }

        Pronunciations({ selectedPos }, {})
        Spacer(Modifier.height(16.dp))

        if (selectedEtymology.words.size > 1) {
            Text(
                "Part Of Speech",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(Modifier.height(8.dp))
            ScrollableTabRow(
                selectedPosIndex,
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                selectedEtymology.words.fastForEachIndexed { index, word ->
                    Tab(
                        selected = selectedPosIndex == index,
                        onClick = { onSelectedPos(index) },
                    ) {
                        Text(
                            word.pos,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 10.dp, bottom = 12.dp)
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }

        Text(
            "Senses",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        selectedPos.senses.take(2).fastForEach { sense ->
            Sense(sense.gloss, Modifier.padding(horizontal = 24.dp))
            Spacer(Modifier.height(8.dp))
        }
        Spacer(Modifier.height(24.dp))
    }
}