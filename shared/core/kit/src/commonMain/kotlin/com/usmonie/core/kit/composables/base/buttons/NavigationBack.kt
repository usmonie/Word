package com.usmonie.core.kit.composables.base.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter

@Composable
fun NavigationBack(showBackButton: () -> Boolean, onClick: () -> Unit) {
    AnimatedVisibility(showBackButton()) {
        IconButton(onClick) {
            NavigationBackIcon()
        }
    }
}

@Composable
fun NavigationBack(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick, modifier) {
        NavigationBackIcon()
    }
}

@Composable
fun NavigationBackIcon() {
    Icon(
        rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowBack),
        contentDescription = Icons.AutoMirrored.Filled.ArrowBack.name,
        tint = MaterialTheme.colorScheme.onBackground
    )
}
