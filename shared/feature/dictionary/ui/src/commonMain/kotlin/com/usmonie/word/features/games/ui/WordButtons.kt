package com.usmonie.word.features.games.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.core.kit.composables.base.buttons.TextButton
import com.usmonie.word.features.games.ui.models.WordCombinedUi

@Composable
fun FavoriteButton(
    getWordCombined: () -> WordCombinedUi,
    onClick: (WordCombinedUi) -> Unit,
    modifier: Modifier = Modifier
) {
    val wordCombined by derivedStateOf(getWordCombined)
    TextButton(
        if (wordCombined.isFavorite) "Unfavorite" else "Favorite",
        { onClick(wordCombined) },
        modifier,
        contentPadding = PaddingValues(0.dp)
    )
}

@Composable
fun IconFavoriteButton(
    getWordCombined: () -> WordCombinedUi,
    onClick: (WordCombinedUi) -> Unit,
    modifier: Modifier = Modifier
) {
    val wordCombined by derivedStateOf(getWordCombined)
    IconButton({ onClick(wordCombined) }, modifier = modifier) {
        Icon(
            if (wordCombined.isFavorite) {
                Icons.Default.Star
            } else {
                Icons.Default.StarBorder
            },
            contentDescription = null
        )
    }
}

@Composable
fun NextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        "Next word",
        onClick,
        modifier,
        contentPadding = PaddingValues(0.dp)
    )
}
