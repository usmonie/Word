package com.usmonie.word.features.learning.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import wtf.word.core.design.themes.Resources

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LifebuoysItem(currentLivesCount: Int, maxLives: Int, modifier: Modifier = Modifier) {
    val enabledLifebuoyColor = MaterialTheme.colorScheme.tertiary
    val disabledLifebuoyColor = MaterialTheme.colorScheme.onSurfaceVariant
    val enabledLifebuoySize = 24.dp
    val disabledLifebuoySize = 20.dp
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        (1..maxLives).forEach { i ->
            val tint by
                animateColorAsState(if (currentLivesCount >= i) enabledLifebuoyColor else disabledLifebuoyColor)

            val size by animateDpAsState(
                targetValue = if (currentLivesCount >= i) enabledLifebuoySize else disabledLifebuoySize
            )

            Icon(
                painterResource(Resources.ic_lifebuoy),
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(size)
            )
        }
    }
}