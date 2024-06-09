package com.usmonie.word.features.games.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.usmonie.core.kit.composables.base.text.AutoSizeText

@Composable
fun WordTitle(
    word: () -> String,
    modifier: Modifier = Modifier
) {
    AutoSizeText(
        word(),
        maxLines = 2,
        minTextSize = 12.sp,
        style = MaterialTheme.typography.displayMedium,
        modifier = modifier
    )
}
