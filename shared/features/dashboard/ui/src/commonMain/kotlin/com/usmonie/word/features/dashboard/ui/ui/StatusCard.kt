package com.usmonie.word.features.dashboard.ui.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.ui.models.LearningStatus
import wtf.speech.core.ui.BaseCard

@Composable
fun StatusCard(onClick: () -> Unit, getStatus: () -> LearningStatus, modifier: Modifier) {
    val status = getStatus()
    StatusCard(
        onClick,
        { status.title },
        { status.status.toString() },
        { status.description },
        modifier
    )
}

@Composable
fun StatusCard(
    onClick: () -> Unit,
    title: () -> String,
    status: () -> String,
    description: () -> String,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(
            text = title(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, top = 20.dp)
        )

        Text(
            text = status(),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp)
        )

        Text(
            text = description(),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        )
    }
}

@Composable
fun PrimaryStatusCard(
    onClick: () -> Unit,
    title: () -> String,
    number: () -> String,
    description: () -> String,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        onClick = onClick,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Text(
            text = title(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, top = 20.dp)
        )

        Text(
            text = number(),
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp)
        )

        Text(
            text = description(),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.inversePrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        )
    }
}