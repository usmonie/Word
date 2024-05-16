package com.usmonie.word.features.dictionary.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi

@Composable
fun WordCardLarge(
    onClick: (WordCombinedUi) -> Unit,
    onFavoriteClick: (WordCombinedUi) -> Unit,
    wordCombined: WordCombinedUi,
    modifier: Modifier
) {
    Surface(
        { onClick(wordCombined) },
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            WordTitle(remember(wordCombined) { { wordCombined.word } })

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                FavoriteButton({ wordCombined }, onFavoriteClick)
            }

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                val etymology = remember(wordCombined) {
                    wordCombined.wordEtymology.firstOrNull()
                } ?: return@Surface

                if (etymology.sounds.isNotEmpty()) {
                    Pronunciations(remember { { etymology } })
                }

                Words({ etymology })
            }
        }
    }
}