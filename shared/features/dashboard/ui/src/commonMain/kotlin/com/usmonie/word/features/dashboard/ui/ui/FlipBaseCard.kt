package com.usmonie.word.features.dashboard.ui.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class CardFace(val angle: Float) {
    Front(0f) {
        override val next: CardFace
            get() = Back
    },
    Back(180f) {
        override val next: CardFace
            get() = Front
    };

    abstract val next: CardFace
}

enum class RotationAxis {
    AxisX,
    AxisY,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlipBaseCard(
    getCardFace: () -> CardFace,
    onClick: (CardFace) -> Unit,
    modifier: Modifier = Modifier,
    axis: RotationAxis = RotationAxis.AxisY,
    elevation: Dp = 2.dp,
    back: @Composable ColumnScope.() -> Unit = {},
    front: @Composable ColumnScope.() -> Unit = {},
) {
    val cardFace = getCardFace()
    val rotation = animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing,
        )
    )

    ElevatedCard(
        onClick = { onClick(cardFace) },
        modifier = modifier.graphicsLayer {
            if (axis == RotationAxis.AxisX) {
                rotationX = rotation.value
            } else {
                rotationY = rotation.value
            }
            cameraDistance = 12f * density
        },
        colors = CardDefaults.cardColors(disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = elevation,
            pressedElevation = (-12).dp
        )
    ) {
        if (rotation.value <= 90f) {
            Column(
                Modifier.fillMaxSize()
            ) {
                front()
            }
        } else {
            Column(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        if (axis == RotationAxis.AxisX) {
                            rotationX = 180f
                        } else {
                            rotationY = 180f
                        }
                    },
            ) {
                back()
            }
        }
    }
}