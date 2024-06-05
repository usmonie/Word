package com.usmonie.word.features.games.ui.kit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.core.domain.tools.fastForEach
import com.usmonie.word.features.games.ui.hangman.GuessedLetters
import com.usmonie.word.features.games.ui.hangman.alphabet

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Keyboard(onLetterClick: (Char) -> Unit, guessedLetters: GuessedLetters, modifier: Modifier) {
    FlowRow(
        modifier.padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        val density = LocalDensity.current
        val size by remember(density) { derivedStateOf { 128.dp / density.density } }
        val letterModifier = Modifier.size(size)

        alphabet.fastForEach { letter ->
            val wasGuessed = remember(
                guessedLetters,
                letter
            ) { letter.lowercaseChar() in guessedLetters.letters }
            KeyboardButton(onLetterClick, letter, letterModifier, wasGuessed)
        }
    }
}

@Composable
private fun KeyboardButton(
    onLetterClick: (Char) -> Unit,
    letter: Char,
    modifier: Modifier,
    wasGuessed: Boolean
) {
    val hapticFeedback = LocalHapticFeedback.current
    FilledTonalButton(
        onClick = {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onLetterClick(letter)
        },
        enabled = !wasGuessed,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
    ) {
        Text(
            letter.toString(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}
