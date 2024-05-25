package com.usmonie.word.features.dictionary.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.usmonie.core.kit.composables.base.text.AutoSizeText

@Composable
fun WordTitle(
    word: () -> String,
    modifier: Modifier = Modifier
) {
    AutoSizeText(
        word(),
        maxLines = 2,
        maxTextSize = MaterialTheme.typography.displayMedium.fontSize,
        style = MaterialTheme.typography.displayMedium,
        modifier = modifier
    )
}
