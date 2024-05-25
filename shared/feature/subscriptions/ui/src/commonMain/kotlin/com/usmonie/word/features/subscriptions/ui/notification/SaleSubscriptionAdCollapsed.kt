package com.usmonie.word.features.subscriptions.ui.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SaleSubscriptionAdCollapsed(
    onExpandClick: () -> Unit,
    onMinifiedClick: () -> Unit,
    leftTime: () -> String,
    modifier: Modifier = Modifier
) {
    val expandThreshold = 24.dp
    val minifyThreshold = 8.dp

    Column(
        modifier
            .fillMaxWidth()
            .clickable(onClick = onExpandClick)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, amount ->
                    change.consume()

                    if (expandThreshold.toPx() < amount) {
                        onExpandClick()
                    } else if (-minifyThreshold.toPx() > amount) {
                        onMinifiedClick()
                    }
                }
            },
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Special offer will be available",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Text(
            leftTime(),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Text(
            "Click here to subscribe",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}