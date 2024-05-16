package com.usmonie.word.features.dictionary.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WordTitle(
    word: () -> String,
    modifier: Modifier = Modifier
) {
    Text(word(), style = MaterialTheme.typography.displayMedium, modifier = modifier)
}
