package com.usmonie.word.features.subscriptions.ui.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SaleSubscriptionAdMinified(
    onExpandClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .clickable(onClick = onExpandClick)
            .background(MaterialTheme.colorScheme.primaryContainer),
    ) {
        Text(
            "Click here to see offers",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
