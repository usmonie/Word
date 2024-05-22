package com.usmonie.word.features.dictionary.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dictionary.ui.models.WordUi

@Composable
fun PartOfSpeech(getWord: () -> WordUi, modifier: Modifier = Modifier) {
    val word by derivedStateOf(getWord)
    Row(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(word.pos, style = MaterialTheme.typography.headlineSmall)
        HorizontalDivider(
            Modifier.weight(1f),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
