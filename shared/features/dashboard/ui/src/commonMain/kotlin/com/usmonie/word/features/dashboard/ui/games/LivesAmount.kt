package com.usmonie.word.features.dashboard.ui.games

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            val tint by animateColorAsState(
                if (position < lives) primaryColor else secondaryColor,
                label = "color_$position"
            )
            val size by animateDpAsState(
                if (position < lives) 24.dp else 20.dp,
                label = "size_$position"
            )
            Icon(
                MyIconPack.IcLifebuoy,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(size)
            )
        }
    }
}