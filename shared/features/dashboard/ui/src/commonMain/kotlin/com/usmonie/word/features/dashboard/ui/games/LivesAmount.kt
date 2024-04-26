package com.usmonie.word.features.dashboard.ui.games

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
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
import wtf.word.core.design.themes.icons.MyIconPack
import wtf.word.core.design.themes.icons.myiconpack.IcLifebuoy

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
            val scale by animateFloatAsState(if (position < lives) 1f else 0.8f)
            Icon(
                MyIconPack.IcLifebuoy,
                contentDescription = null,
                tint = if (position < lives) primaryColor else secondaryColor,
                modifier = Modifier.size(24.dp).graphicsLayer {
                    scaleX = scale
                    scaleY = scale

                }
            )
        }
    }
}