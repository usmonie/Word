package com.usmonie.word.features.games.ui.kit

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.core.domain.tools.fastForEach
import com.usmonie.word.features.games.ui.hangman.GuessedLetters
import com.usmonie.word.features.games.ui.hangman.alphabet
import kotlinx.coroutines.delay

//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun QwertyKeyboard(onLetterClick: (Char) -> Unit, guessedLetters: GuessedLetters, modifier: Modifier = Modifier) {
//
//	val configuration = LocalWindowInfo.current
//	val screenWidth = with(LocalDensity.current) {
//		configuration.containerSize.width.toDp()
//	}
//	val keyboardWidth = screenWidth - 16.dp  // Учитываем горизонтальные отступы
//
//	val rows = listOf(
//		"QWERTYUIOP",
//		"ASDFGHJKL",
//		"ZXCVBNM"
//	)
//
//	val maxRowLength = rows.maxOf { it.length }
//	val buttonSize = (keyboardWidth / maxRowLength) - 4.dp  // Учитываем промежутки между кнопками
//
//	Column(
//		modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp),
//		verticalArrangement = Arrangement.spacedBy(4.dp)
//	) {
//		rows.forEach { row ->
//			Row(
//				horizontalArrangement = Arrangement.SpaceBetween,
//				modifier = Modifier.fillMaxWidth()
//			) {
//				row.forEach { letter ->
//					val wasGuessed = remember(guessedLetters, letter) {
//						letter.lowercaseChar() in guessedLetters.letters
//					}
//					KeyboardButton(
//						onLetterClick = onLetterClick,
//						letter = letter,
//						modifier = Modifier.width(buttonSize).height(buttonSize * 1.5f),
//						wasGuessed = wasGuessed
//					)
//				}
//			}
//		}
//	}
//}
//

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Keyboard(onLetterClick: (Char) -> Unit, guessedLetters: GuessedLetters, modifier: Modifier) {
	FlowRow(
		modifier.padding(vertical = 8.dp, horizontal = 8.dp),
		horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
		verticalArrangement = Arrangement.SpaceEvenly,
		maxItemsInEachRow = 10
	) {
		val density = LocalDensity.current
		val size by remember(density) { derivedStateOf { 108.dp / density.density } }
		val letterModifier = Modifier.weight(1f, false)

		alphabet.fastForEach { letter ->
			val wasGuessed = remember(
				guessedLetters,
				letter
			) { letter.lowercaseChar() in guessedLetters.letters }
			KeyboardButton(onLetterClick, letter, letterModifier, wasGuessed)
		}
	}
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun KeyboardButton(
	onLetterClick: (Char) -> Unit,
	letter: Char,
	modifier: Modifier,
	wasGuessed: Boolean
) {
	val hapticFeedback = LocalHapticFeedback.current
	var clicked by remember(letter) { mutableStateOf(false) }
	val scale by animateFloatAsState(if (clicked) 1.2f else 1f)

	LaunchedEffect(clicked) {
		if (clicked) {
			delay(200L)
			clicked = false
		}
	}

	ElevatedButton(
		onClick = {
			clicked = true
			hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
			onLetterClick(letter)
		},
		enabled = !wasGuessed,
		modifier = modifier.graphicsLayer {
			scaleX = scale
			scaleY = scale
		},
		shape = RoundedCornerShape(2.dp),
		contentPadding = PaddingValues(horizontal = 0.dp, vertical = 4.dp)
	) {
		Text(
			letter.toString(),
			style = MaterialTheme.typography.titleMedium,
			textAlign = TextAlign.Center
		)
	}
}

