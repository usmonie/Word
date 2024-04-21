package com.usmonie.word.features.dashboard.ui.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun ReviveLifeDialog(
    onDismissRequest: () -> Unit,
    onAddLifeClick: () -> Unit,
    onNextPhraseClick: () -> Unit,
    isReviveAvailable: () -> Boolean,
    title: String = "You've exhausted all attempts",
    reviveTitle: String = "Revive",
    nextTitle: String
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(24.dp))
        ) {
            Spacer(Modifier.height(24.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
            )

            Button(
                onAddLifeClick,
                Modifier.padding(horizontal = 24.dp),
                enabled = isReviveAvailable()
            ) {
                Box {
                    Text(
                        text = reviveTitle,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 4.dp)
                    )

                    Badge(Modifier.align(Alignment.CenterEnd)) {
                        Text(
                            text = "FREE",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }

            OutlinedButton(onNextPhraseClick, Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = nextTitle,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f).padding(horizontal = 24.dp, vertical = 4.dp)
                )
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}