package com.usmonie.word.features.dashboard.ui.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import com.usmonie.word.features.dashboard.ui.models.WordCombinedUi
import com.usmonie.word.features.dashboard.ui.ui.QuoteCard

@Composable
fun GameWonDialog(
    onDismissRequest: () -> Unit,
    onNextPhraseClick: () -> Unit,
    nextTitle: String,
    wonTitle: @Composable ColumnScope.() -> Unit,
) {
    val dialogProperties = remember {
        DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    }
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = dialogProperties
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
        }
    }
}

@Composable
fun EnigmaGameWon(
    onDismissRequest: () -> Unit,
    onNextPhraseClick: () -> Unit,
    nextTitle: String,
    quote: String,
    ref: String?,
) {
    val text = remember { buildAnnotatedString { append(quote) } }
    GameWonDialog(onDismissRequest, onNextPhraseClick, nextTitle) {
        Text(
            "You deciphered the quote",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        )

        Spacer(Modifier.height(4.dp))

        QuoteCard(
            text,
            ref,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        )
        Spacer(Modifier.height(4.dp))
    }
}

@Composable
fun HangmanGameWon(
    onDismissRequest: () -> Unit,
    onNextPhraseClick: () -> Unit,
    nextTitle: String,
    word: WordCombinedUi,
) {
    GameWonDialog(onDismissRequest, onNextPhraseClick, nextTitle) {
        Text(
            "You guessed the word",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        )
        Text(
            word.word,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        )
    }
}