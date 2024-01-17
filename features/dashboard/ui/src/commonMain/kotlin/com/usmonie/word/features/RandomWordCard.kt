package com.usmonie.word.features

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.models.WordCombinedUi
import com.usmonie.word.features.models.WordUi
import com.usmonie.word.features.ui.BaseCard
import com.usmonie.word.features.ui.WordMediumResizableTitle
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
    val word = wordState.item
    BaseCard(
        { word?.let { onCardClick(it.second) } },
        enabled = word != null,
        modifier = modifier
    ) {
        when (wordState) {
            is ContentState.Error<*, *> -> {
                Text("ERROR")
            }

            is ContentState.Loading -> RandomWordLoading()

            is ContentState.Success -> {
                RandomWordSuccess(
                    wordState.data,
                    modifier,
                    onLearnClick,
                    onBookmarkClick,
                    onUpdate
                )
            }
        }
    }

}

private val fillMaxWidth = Modifier.fillMaxWidth()
private val cardOuterVerticalPaddingSpacerModifier = Modifier.height(20.dp)
private val cardInnerVerticalPaddingSpacerModifier = Modifier.height(8.dp)
private val itemHorizontalPaddingModifier = fillMaxWidth.padding(horizontal = 20.dp)
private val itemVerticalPaddingModifier = fillMaxWidth.padding(vertical = 20.dp)
private val progressBarLoadingModifier = Modifier.size(32.dp)

@Composable
private fun RandomWordSuccess(
    word: Pair<WordUi, WordCombinedUi>,
    modifier: Modifier,
    onLearnClick: (WordCombinedUi) -> Unit,
    onBookmarkClick: (WordCombinedUi) -> Unit,
    onUpdate: () -> Unit
) {
    Spacer(cardOuterVerticalPaddingSpacerModifier)
    WordMediumResizableTitle(
        word.first.word,
        itemHorizontalPaddingModifier,
        MaterialTheme.colorScheme.onSurface
    )
    Spacer(cardInnerVerticalPaddingSpacerModifier)
    word.first.senses.getOrNull(0)
        ?.let { sense ->
            Sense(sense.gloss, itemHorizontalPaddingModifier, false)
        }
    Spacer(cardInnerVerticalPaddingSpacerModifier)
    WordCardButtons(
        { onLearnClick(word.second) },
        { onBookmarkClick(word.second) },
        onUpdate,
        word.second.isFavorite,
        modifier = fillMaxWidth
    )
    Spacer(cardOuterVerticalPaddingSpacerModifier)
}

@Composable
private fun RandomWordLoading() {
    Box(itemVerticalPaddingModifier, Alignment.Center) {
        CircularProgressIndicator(
            progressBarLoadingModifier,
            MaterialTheme.colorScheme.onSurface
        )
    }
}

