package com.usmonie.word.features.games.ui.kit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.usmonie.core.kit.tools.add

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameBoardTopAppBar(
    onBackClickListener: () -> Unit,
    showItem: Boolean,
    actions: @Composable RowScope.() -> Unit,
    topBarInsets: WindowInsets = TopAppBarDefaults.windowInsets
) {
    AnimatedVisibility(showItem) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(topBarInsets.asPaddingValues().add(top = 4.dp))
        ) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                IconButton(onBackClickListener) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = Icons.AutoMirrored.Default.ArrowBack.name,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                Row(
                    Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    content = actions
                )
            }
        }
    }
}
