package com.usmonie.word.features.dashboard.ui.ui.subscription

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun SubscriptionColumn(
    getSubscriptionSaleState: () -> SubscriptionSaleState?,
    onSubscriptionExpand: () -> Unit,
    onSubscriptionCollapse: () -> Unit,
    showSubscriptionAd: Boolean,
    content: @Composable () -> Unit
) {
    val threshold = 16.dp

    Column(Modifier
        .background(MaterialTheme.colorScheme.background)
        .pointerInput(Unit) {
            detectVerticalDragGestures { change, amount ->
                change.consume()

                if (threshold.toPx() < amount) {
                    onSubscriptionExpand()
                } else if (threshold.toPx() < amount * -1) {
                    onSubscriptionCollapse()
                }
            }
        }) {
        AnimatedVisibility(showSubscriptionAd) {
            Box(
                Modifier.animateContentSize()
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                when (val subscriptionSaleState = getSubscriptionSaleState()) {
                    is SubscriptionSaleState.Expanded -> {
                        SaleSubscriptionExpanded(
                            {},
                            {},
                            onSubscriptionCollapse,
                            { subscriptionSaleState.duration },
                            Modifier.fillMaxWidth()
                        )
                    }

                    is SubscriptionSaleState.Ad -> {
                        SaleSubscriptionAd(
                            onSubscriptionExpand,
                            { subscriptionSaleState.duration },
                            Modifier.fillMaxWidth()
                        )
                    }

                    null -> Unit
                }
            }
        }

        content()
    }

}