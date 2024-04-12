package com.usmonie.word.features.dashboard.ui.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.usmonie.word.features.dashboard.ui.ui.QuoteCard

@Composable
fun GameWonDialog(
    onDismissRequest: () -> Unit,
    onNextPhraseClick: () -> Unit,
    nextTitle: String,
    wonTitle: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                modifier = Modifier.fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surfaceContainer,
                        RoundedCornerShape(24.dp)
                    )
            ) {
                Spacer(Modifier.height(24.dp))
                wonTitle()

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


            OutlinedButton(onNextPhraseClick, Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = nextTitle,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f).padding(horizontal = 24.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun QuoteGameWon(
    onDismissRequest: () -> Unit,
    onNextPhraseClick: () -> Unit,
    nextTitle: String,
    quote: String,
    ref: String?,
) {
    val text = remember { buildAnnotatedString { append(quote) } }
    GameWonDialog(onDismissRequest, onNextPhraseClick, nextTitle) {
        QuoteCard(
            text,
            ref,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 24.dp)
        )
    }
}