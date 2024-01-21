package com.usmonie.word.features.new.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object BaseCardDefaults {
    val shape = RoundedCornerShape(16.dp)
    val elevation = 0.dp
}

@Composable
fun BaseCard(
    modifier: Modifier = Modifier,
    elevation: Dp = BaseCardDefaults.elevation,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable ColumnScope.() -> Unit
) {
    BaseCard(
        onClick = {},
        elevation = elevation,
        enabled = false,
        modifier = modifier,
        containerColor = containerColor,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: Dp = BaseCardDefaults.elevation,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            disabledContainerColor = containerColor
        ),
        shape = BaseCardDefaults.shape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation,
            pressedElevation = elevation
        ),
        enabled = enabled,
        content = content
    )
}