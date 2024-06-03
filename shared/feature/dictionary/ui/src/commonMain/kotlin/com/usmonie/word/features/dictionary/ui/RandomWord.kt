package com.usmonie.word.features.dictionary.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.compass.viewmodel.ContentState
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.dictionary.ui.generated.resources.Res
import word.shared.feature.dictionary.ui.generated.resources.vocabulary_random_word_title

@Composable
fun RandomWordExpandedState(
    getWordCombinedState: () -> ContentState<WordCombinedUi>,
    onClick: (WordCombinedUi) -> Unit,
    onFavoriteClicked: (WordCombinedUi) -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val wordCombinedState by derivedStateOf { getWordCombinedState() }
    when (val state = wordCombinedState) {
        is ContentState.Success -> RandomWordExpanded(
            { state.data },
            onClick,
            onFavoriteClicked,
            onNextClicked,
            modifier
        )

        is ContentState.Loading -> LinearProgressIndicator(Modifier.fillMaxWidth().padding(32.dp))
        else -> Unit
    }
}

@Composable
fun RandomWordCollapsedState(
    getWordCombinedState: () -> ContentState<WordCombinedUi>,
    onClick: (WordCombinedUi) -> Unit,
    onFavoriteClicked: (WordCombinedUi) -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val wordCombinedState by derivedStateOf { getWordCombinedState() }
    when (val state = wordCombinedState) {
        is ContentState.Success -> RandomWordCollapsed(
            { state.data },
            onClick,
            onFavoriteClicked,
            onNextClicked,
            modifier
        )

        is ContentState.Loading -> LinearProgressIndicator(Modifier.fillMaxWidth().padding(32.dp))
        else -> Unit
    }
}

@Composable
fun RandomWordExpanded(
    wordCombined: () -> WordCombinedUi,
    onClick: (WordCombinedUi) -> Unit,
    onFavoriteClicked: (WordCombinedUi) -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val word by derivedStateOf(wordCombined)
    Surface(
        { onClick(word) },
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
//        color = MaterialTheme.colorScheme.background
    ) {
        Column(Modifier.padding(vertical = 16.dp)) {
            RandomWordTitle(modifierWithPaddings)
            WordTitle({ word.word }, modifierWithPaddings)

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val etymology =
                    remember(word) { word.wordEtymology.firstOrNull() } ?: return@Surface
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FavoriteButton(wordCombined, onFavoriteClicked)
                    NextButton(onNextClicked)
                }

//                if (etymology.sounds.isNotEmpty()) {
//                    Pronunciations(
//                        { etymology },
//                        Modifier.fillMaxWidth().padding(horizontal = 16.dp)
//                    )
//                }

                Words { etymology }
            }
        }
    }
}

@Composable
fun RandomWordCollapsed(
    wordCombined: () -> WordCombinedUi,
    onClick: (WordCombinedUi) -> Unit,
    onFavoriteClicked: (WordCombinedUi) -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val word = wordCombined()

    Surface(
        { onClick(word) },
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
    ) {
        Column(Modifier.padding(vertical = 16.dp)) {
            RandomWordTitle(modifierWithPaddings)
            WordTitle({ word.word }, modifierWithPaddings)

            Column(
                modifierWithPaddings,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val etymology =
                    remember(word) { word.wordEtymology.firstOrNull() } ?: return@Surface

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    FavoriteButton(wordCombined, onFavoriteClicked)
                    NextButton(onNextClicked)
                }

                Words { etymology }
            }
        }
    }
}

@Composable
private fun RandomWordTitle(
    modifier: Modifier = Modifier
) {
    Text(
        stringResource(Res.string.vocabulary_random_word_title),
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier
    )
}
