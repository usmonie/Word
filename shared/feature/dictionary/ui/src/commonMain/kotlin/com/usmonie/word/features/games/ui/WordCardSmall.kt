package com.usmonie.word.features.games.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.usmonie.core.kit.composables.base.text.AutoSizeText
import com.usmonie.word.features.games.ui.models.WordCombinedUi

@Composable
fun WordCardSmall(
    onClick: (WordCombinedUi) -> Unit,
    wordCombinedUi: WordCombinedUi,
    modifier: Modifier
) {
    Surface(
        { onClick(wordCombinedUi) },
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            AutoSizeText(
                wordCombinedUi.word,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                wordCombinedUi.wordEtymology.first().words.first().senses.first().gloss,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Justify,
                maxLines = 3,
                minLines = 3
            )
        }
    }
}