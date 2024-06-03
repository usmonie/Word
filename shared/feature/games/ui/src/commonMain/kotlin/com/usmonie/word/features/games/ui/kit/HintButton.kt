package com.usmonie.word.features.games.ui.kit

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UseHintButton(onClick: () -> Unit, hintsCount: Int) {
    Box {
        val interactionSource = remember { MutableInteractionSource() }
        IconButton(onClick, interactionSource = interactionSource) {
            val pressed by interactionSource.collectIsPressedAsState()
            val size by animateDpAsState(if (pressed) 20.dp else 24.dp)
            Icon(
                Icons.Rounded.Lightbulb,
                contentDescription = "Use hint",
                modifier = Modifier.size(size)
            )
        }
        if (hintsCount > 0) {
            Badge(modifier = Modifier.align(Alignment.TopEnd)) {
                val count by animateIntAsState(hintsCount)
                Text(
                    text = if (count > 99) "99" else count.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}