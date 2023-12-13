package com.usmonie.word.features.new.games.hangman

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun HangmanImage(incorrectGuesses: Int, modifier: Modifier = Modifier) {
    val hangmanColor = MaterialTheme.colorScheme.onPrimary
    
    Canvas(modifier = modifier) {
        val headRadius = size.width * 0.08f
        val strokeWidth = 8.dp.toPx()
        val horizontalCenter = size.width * 0.5f
        val gallowsStartedPoint = size.height * 0.01f
        val gallowsLowestPoint = size.height * 0.05f + gallowsStartedPoint
        val armHighestPoint = gallowsLowestPoint + (headRadius * 2) + size.height * 0.1f
        val armLowestPoint = gallowsLowestPoint + (headRadius * 2) + size.height * 0.2f
        val bodyLowestPoint = size.height * 0.7f

        drawLine(
            color = hangmanColor,
            start = Offset(x = size.width, y = gallowsStartedPoint),
            end = Offset(x = horizontalCenter - strokeWidth / 2, y = gallowsStartedPoint),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = hangmanColor,
            start = Offset(x = horizontalCenter, y = gallowsStartedPoint),
            end = Offset(x = horizontalCenter, y = gallowsLowestPoint),
            strokeWidth = strokeWidth
        )

        // Draw the hangman based on the number of incorrect guesses
        if (incorrectGuesses > 0) {
            // Draw head
            drawCircle(
                color = hangmanColor,
                radius = headRadius,
                center = Offset(x = horizontalCenter, y = gallowsLowestPoint + headRadius),
                style = Stroke(width = strokeWidth)
            )
        }
        if (incorrectGuesses > 1) {
            // Draw body
            drawLine(
                color = hangmanColor,
                start = Offset(x = horizontalCenter, y = gallowsLowestPoint + headRadius * 2),
                end = Offset(x = horizontalCenter, y = bodyLowestPoint),
                strokeWidth = strokeWidth
            )
        }
        if (incorrectGuesses > 2) {
            // Draw left arm
            drawLine(
                color = hangmanColor,
                start = Offset(x = horizontalCenter, y = armHighestPoint),
                end = Offset(x = size.width * 0.4f, y = armLowestPoint),
                strokeWidth = strokeWidth
            )
        }
        if (incorrectGuesses > 3) {
            // Draw right arm
            drawLine(
                color = hangmanColor,
                start = Offset(x = horizontalCenter, y = armHighestPoint),
                end = Offset(x = size.width * 0.6f, y = armLowestPoint),
                strokeWidth = strokeWidth
            )
        }
        if (incorrectGuesses > 4) {
            // Draw left leg
            drawLine(
                color = hangmanColor,
                start = Offset(x = horizontalCenter, y = bodyLowestPoint),
                end = Offset(x = size.width * 0.4f, y = size.height * 0.9f),
                strokeWidth = strokeWidth
            )
        }
        if (incorrectGuesses > 5) {
            // Draw right leg
            drawLine(
                color = hangmanColor,
                start = Offset(x = horizontalCenter, y = bodyLowestPoint),
                end = Offset(x = size.width * 0.6f, y = size.height * 0.9f),
                strokeWidth = strokeWidth
            )
        }
    }
}