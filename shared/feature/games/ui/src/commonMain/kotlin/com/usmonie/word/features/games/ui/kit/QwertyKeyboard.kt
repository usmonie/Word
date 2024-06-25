package com.usmonie.word.features.games.ui.kit


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.usmonie.word.features.games.ui.hangman.GuessedLetters
import com.usmonie.word.features.games.ui.hangman.qwerty

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun QwertyKeyboard(onLetterClick: (Char) -> Unit, guessedLetters: GuessedLetters, modifier: Modifier = Modifier) {

	val configuration = LocalWindowInfo.current
	val screenWidth = with(LocalDensity.current) {
		configuration.containerSize.width.toDp()
	}
	val keyboardWidth = screenWidth - 6.dp

	val rows = qwerty

	Column(
		modifier = modifier.width(keyboardWidth).padding(horizontal = 3.dp, vertical = 8.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
	) {
		val buttonModifier = Modifier.size(width = (keyboardWidth - (8.dp * 9)) / 10, height = 54.dp)
		rows.forEachIndexed { index, row ->
			Row(
				horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
				modifier = Modifier.fillMaxWidth()
			) {
				// Add left padding for the second and third rows
				if (index > 0) {
					Spacer(modifier = Modifier.width((index * 15).dp))
				}

				row.forEach { letter ->
					val wasGuessed = remember(guessedLetters, letter) {
						letter.lowercaseChar() in guessedLetters.letters
					}
					KeyboardButton(
						onLetterClick = onLetterClick,
						letter = letter,
						modifier = buttonModifier,
						wasGuessed = wasGuessed
					)
				}

				// Add right padding for the second and third rows
				if (index > 0) {
					Spacer(modifier = Modifier.width((index * 15).dp))
				}
			}
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

	Button(
		onClick = {
			hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
			onLetterClick(letter)
		},
		enabled = !wasGuessed,
		modifier = modifier,
		shape = RoundedCornerShape(5.dp),
		contentPadding = PaddingValues(),
		elevation = ButtonDefaults.buttonElevation(
			defaultElevation = 1.dp,
			pressedElevation = 0.dp
		)
	) {
		Text(
			letter.toString(),
			style = MaterialTheme.typography.titleMedium,
			textAlign = TextAlign.Center
		)
	}
}