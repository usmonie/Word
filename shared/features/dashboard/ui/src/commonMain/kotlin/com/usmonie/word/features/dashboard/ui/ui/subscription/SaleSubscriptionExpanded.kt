package com.usmonie.word.features.dashboard.ui.ui.subscription

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import wtf.speech.core.ui.BaseCard
import kotlin.math.pow
import kotlin.math.round

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun SaleSubscriptionExpanded(
    onSubscribeClick: (SubscriptionItem) -> Unit,
    onRestoreClick: () -> Unit,
    onCollapseClick: () -> Unit,
    leftTime: () -> String,
    modifier: Modifier = Modifier
) {
    val threshold = 16.dp

    Column(
        modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .statusBarsPadding()
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, amount ->
                    change.consume()

                    if (threshold.toPx() < amount * -1) {
                        onCollapseClick()
                    }
                }
            },
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconButton(
            onCollapseClick,
            modifier = Modifier.align(CenterHorizontally),
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
        ) {
            Icon(Icons.Rounded.ExpandLess, contentDescription = "close_subscriptions")
        }

        Text(
            "Limited offer only for next",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Text(
            leftTime(),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Text(
            "Ads Free\n" +
                    "Custom Themes\n" +
                    "Offline Access\n" +
                    "Support developer\n" +
                    "50 percent of profits go to charity\n" +
                    "Unlimited lifes",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

//        val pages = remember {
//            listOf()
//        }

        val pagerState = rememberPagerState(initialPage = 1) { 3 }
        HorizontalPager(
            pagerState,
            contentPadding = PaddingValues(horizontal = 52.dp),
            pageSpacing = 24.dp
        ) {
            SaleCard(SubscriptionItem("", "$14.99 per year", 30, 14.99f, "$"), badgeText = "BEST OFFER")
        }

        Button(
            {},
            modifier = Modifier.height(52.dp)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                contentColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                "Try free",
                style = MaterialTheme.typography.titleMedium,
            )
        }

        TextButton(
            onRestoreClick,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp),
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
        ) {
            Text(
                "Restore purchase",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
fun SaleCard(
    subscriptionItem: SubscriptionItem,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    badgeText: String? = null
) {
    Box(modifier) {
        if (badgeText != null) {
            Badge(Modifier.align(androidx.compose.ui.Alignment.TopCenter).zIndex(1f)) {
                Text(badgeText, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
            }
        }

        BaseCard(
            modifier.padding(top = if (badgeText != null) 24.dp else 0.dp),
            containerColor = containerColor
        ) {
            Spacer(Modifier.height(24.dp))
            val priceAmountStyle = MaterialTheme.typography.displayMedium
            val durationStyle = MaterialTheme.typography.bodySmall
            val price: AnnotatedString = remember(subscriptionItem) {
                buildAnnotatedString {
                    withStyle(priceAmountStyle.toSpanStyle()) {
                        append(subscriptionItem.currency)
                        append(round(subscriptionItem.price / subscriptionItem.durationDays).toString())
                    }
                    withStyle(durationStyle.toSpanStyle()) {
                        append("/day")
                        append("\n")
                        append(subscriptionItem.duration)
                    }
                }
            }
            Text(
                price,
                style = MaterialTheme.typography.displayMedium,
                color = contentColor,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
            )
            Spacer(Modifier.height(12.dp))

            Text(
                "1 month for free",
                style = MaterialTheme.typography.bodySmall,
                color = contentColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Immutable
data class SubscriptionItem(
    val id: String,
    val duration: String,
    val durationDays: Int,
    val price: Float,
    val currency: String
)

fun Float.round(decimals: Int): Float {
    val multiplier = 10f.pow(decimals)
    return round(this * multiplier) / multiplier
}