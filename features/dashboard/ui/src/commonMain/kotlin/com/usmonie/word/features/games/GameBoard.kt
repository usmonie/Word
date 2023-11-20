package com.usmonie.word.features.games

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.usmonie.word.features.ui.TopBackButtonBar

@Composable
fun GameBoard(onBackClickListener: () -> Unit, content: @Composable (insets: PaddingValues) -> Unit) {
    Scaffold(
        topBar = { TopBackButtonBar(onBackClickListener, true) },
        content = content
    )
}