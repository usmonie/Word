package com.usmonie.word.features.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBackButtonBar(onBackClickListener: () -> Unit, showItem: Boolean) {
    AnimatedVisibility(showItem) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
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