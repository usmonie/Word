package com.usmonie.word.features.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopBackButtonBar(onBackClickListener: () -> Unit, showItem: Boolean) {
    AnimatedVisibility(showItem) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        ) {
            Spacer(Modifier.height(48.dp))
            IconButton(onBackClickListener) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = Icons.Default.ArrowBack.name,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun TopBackButtonBar(
    onBackClickListener: () -> Unit,
    showItem: Boolean,
    actions: @Composable RowScope.() -> Unit
) {
    AnimatedVisibility(showItem) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        ) {
            Spacer(Modifier.height(48.dp))

            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onBackClickListener) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = Icons.Default.ArrowBack.name,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                Row(
                    Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    actions()
                }
            }
        }
    }
}