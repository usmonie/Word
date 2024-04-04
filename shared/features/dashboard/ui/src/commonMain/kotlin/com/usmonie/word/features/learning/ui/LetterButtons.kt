package com.usmonie.word.features.learning.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LetterButtons(onLetterClick: (Char) -> Unit, onBackspace: () -> Unit, modifier: Modifier) {
    val alphabet = remember { ('A'..'Z').toList() }
    LazyVerticalGrid(
        GridCells.Fixed(7),
        modifier = modifier,
        userScrollEnabled = false,
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        items(alphabet) { letter ->
            TextButton(onClick = { onLetterClick(letter) }) {
                Text(
                    letter.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        item(span = { GridItemSpan(2) }) {
            IconButton(onBackspace, modifier = Modifier.fillMaxSize()) {
                Box(contentAlignment = Alignment.CenterEnd, ) {
                    Icon(
                        Icons.Outlined.Backspace,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}