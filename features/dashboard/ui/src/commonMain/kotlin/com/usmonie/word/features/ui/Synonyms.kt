package com.usmonie.word.features.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.models.SynonymUi

@Composable
fun Synonyms(onClick: (SynonymUi) -> Unit, synonyms: List<SynonymUi>) {
    Column {
        Text(
            "Synonyms",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(Modifier.height(4.dp))
        LazyRow {
            item { Spacer(Modifier.width(20.dp)) }

            items(synonyms) { synonym ->
                SuggestionChip(onClick, synonym)
                Spacer(Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun SuggestionChip(
    onClick: (SynonymUi) -> Unit,
    synonym: SynonymUi,
    modifier: Modifier = Modifier
) {
    SuggestionChip(
        modifier = modifier,
        onClick = { onClick(synonym) },
        label = { Text(synonym.word, style = MaterialTheme.typography.labelSmall) }
    )
}