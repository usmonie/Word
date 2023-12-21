package com.usmonie.word.features.new.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.new.models.WordCombinedUi
import com.usmonie.word.features.new.models.WordUi
import com.usmonie.word.features.ui.BaseCard
import com.usmonie.word.features.ui.WordLargeResizableTitle
import wtf.speech.core.ui.ContentState

@Composable
fun RandomWordCard(
    onCardClick: (WordCombinedUi) -> Unit,
    onBookmarkClick: (WordCombinedUi) -> Unit,
    onLearnClick: (WordCombinedUi) -> Unit,
    onUpdate: () -> Unit,
    wordState: ContentState<Pair<WordUi, WordCombinedUi>>,
    modifier: Modifier = Modifier
) {
    when (wordState) {
        is ContentState.Error<*, *> -> Unit
        is ContentState.Loading -> Unit
        is ContentState.Success -> {
            val word = wordState.data
            BaseCard(
                { onCardClick(word.second) },
                elevation = 2.dp,
                modifier = modifier
            ) {
                Spacer(Modifier.height(32.dp))
                WordLargeResizableTitle(
                    word.first.word,
                    Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                )
                Spacer(Modifier.height(16.dp))
                word.first.senses.getOrNull(0)
                    ?.let { text -> Sense(text, Modifier.padding(horizontal = 20.dp)) }
                Spacer(Modifier.height(16.dp))
                WordCardButtons(
                    { onLearnClick(word.second) },
                    { onBookmarkClick(word.second) },
                    onUpdate,
                    word.second.isFavorite,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

