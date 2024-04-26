package com.usmonie.word.features.dashboard.ui.games

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.usmonie.word.features.dashboard.ui.ui.TopBackButtonBar

@Composable
fun GameBoard(
    onBackClickListener: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable (RowScope.() -> Unit),
    content: @Composable (insets: PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBackButtonBar(onBackClickListener, true, actions) },
        content = content,
    )
}