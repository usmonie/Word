package com.usmonie.word.features.dictionary.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun WordOfTheDayState(
    getWordCombinedState: () -> ContentState<WordCombinedUi>,
    onClick: (WordCombinedUi) -> Unit,
    onFavoriteClicked: (WordCombinedUi) -> Unit,
    modifier: Modifier = Modifier
) {
    val wordCombinedState by derivedStateOf { getWordCombinedState() }
    when (val state = wordCombinedState) {
        is ContentState.Success -> WordOfTheDay(
            { state.data },
            onClick,
            onFavoriteClicked,
            modifier
        )

//        is ContentState.Loading -> LinearProgressIndicator(Modifier.fillMaxWidth().padding(32.dp))
        else -> Unit
    }
}

@Composable
fun WordOfTheDay(
    wordCombined: () -> WordCombinedUi,
    onClick: (WordCombinedUi) -> Unit,
    onFavoriteClicked: (WordCombinedUi) -> Unit,
    modifier: Modifier = Modifier
) {
    val word by derivedStateOf(wordCombined)

    Surface({ onClick(word) }) {
        Column {
            WordOfTheDayTitle()
            WordTitle(remember { { word.word } })

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                FavoriteButton(wordCombined, onFavoriteClicked)
            }

            Column(
                modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val etymology =
                    remember(word) { word.wordEtymology.firstOrNull() } ?: return@Surface

                if (etymology.sounds.isNotEmpty()) {
                    Pronunciations(remember { { etymology } })
                }

                Words { etymology }
            }
        }
    }
}

@Composable
private fun WordOfTheDayTitle(modifier: Modifier = Modifier) {
    Text("Word of the day", style = MaterialTheme.typography.labelMedium, modifier = modifier)
}
