package com.usmonie.word.features.games.ui.kit

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.usmonie.core.kit.icons.Service
import com.usmonie.core.kit.icons.service.IcLifebuoy

@Composable
fun LivesAmount(lives: Int, maxLives: Int, modifier: Modifier) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val primaryColor = MaterialTheme.colorScheme.primary
        val secondaryColor = MaterialTheme.colorScheme.secondary
        repeat(maxLives) { position ->
            val scale by animateFloatAsState(if (position < lives) 1f else SPENT_LIFE_ICON_SIZE)
            Icon(
                Service.IcLifebuoy,
                contentDescription = null,
                tint = if (position < lives) primaryColor else secondaryColor,
                modifier = Modifier.size(24.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
            )
        }
    }
}

const val SPENT_LIFE_ICON_SIZE = 0.8F