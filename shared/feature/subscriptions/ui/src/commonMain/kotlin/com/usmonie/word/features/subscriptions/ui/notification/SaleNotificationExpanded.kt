package com.usmonie.word.features.subscriptions.ui.notification

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.usmonie.core.kit.composables.base.cards.BaseCard
import com.usmonie.word.features.subscription.domain.models.Currency
import com.usmonie.word.features.subscription.domain.models.Subscription
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.subscriptions.ui.generated.resources.Res
import word.shared.feature.subscriptions.ui.generated.resources.subscriptions_best_offer_title
import word.shared.feature.subscriptions.ui.generated.resources.subscriptions_discount_title
import word.shared.feature.subscriptions.ui.generated.resources.subscriptions_duration_half_year
import word.shared.feature.subscriptions.ui.generated.resources.subscriptions_duration_month
import word.shared.feature.subscriptions.ui.generated.resources.subscriptions_duration_year
import word.shared.feature.subscriptions.ui.generated.resources.subscriptions_limited_offer_title
import word.shared.feature.subscriptions.ui.generated.resources.subscriptions_price_daily
import word.shared.feature.subscriptions.ui.generated.resources.subscriptions_price_per
import word.shared.feature.subscriptions.ui.generated.resources.subscriptions_price_per_daily
import word.shared.feature.subscriptions.ui.generated.resources.subscriptions_restore_purchases_button
import word.shared.feature.subscriptions.ui.generated.resources.subscriptions_subscription_benefits
import word.shared.feature.subscriptions.ui.generated.resources.subscriptions_try_free_button

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SaleNotificationExpanded(
    onSubscribeClick: (Subscription) -> Unit,
    onRestoreClick: () -> Unit,
    onCollapseClick: () -> Unit,
    leftTime: () -> String,
    modifier: Modifier = Modifier,
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

                    if (-threshold.toPx() > amount) {
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
            stringResource(Res.string.subscriptions_limited_offer_title),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Text(
            leftTime(),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Text(
            stringResource(Res.string.subscriptions_subscription_benefits),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        val pages = remember {
            listOf(
                Subscription.HalfYear("", 9.99f, "", Currency("$"), false),
                Subscription.Year("", 14.99f, "", Currency("$"), false),
                Subscription.Monthly("", 1.99f, "", Currency("$"), false),
            )
        }

        val pagerState = rememberPagerState(initialPage = 1) { pages.size }
        HorizontalPager(
            pagerState,
            contentPadding = PaddingValues(horizontal = 52.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            val subscription = pages[it]
            val scale by animateFloatAsState(if (it == pagerState.currentPage) 1f else 0.95f)

            SaleCard(
                subscription,
                badgeText = when (subscription) {
                    is Subscription.HalfYear -> stringResource(Res.string.subscriptions_discount_title)
                    is Subscription.Year -> stringResource(Res.string.subscriptions_best_offer_title)
                    is Subscription.Monthly -> null
                },
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
            )
        }

        Button(
            { onSubscribeClick(pages[pagerState.currentPage]) },
            modifier = Modifier.height(52.dp)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                contentColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                stringResource(Res.string.subscriptions_try_free_button),
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
                stringResource(Res.string.subscriptions_restore_purchases_button),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
fun SaleCard(
    subscription: Subscription,
    modifier: Modifier = Modifier,
    badgeText: String? = null
) {
    Box(modifier) {
        if (badgeText != null) {
            Badge(Modifier.align(Alignment.TopCenter).zIndex(1f)) {
                Text(
                    badgeText,
                    style = MaterialTheme.typography.titleMedium,

                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }
        }

        BaseCard(
            modifier.padding(top = if (badgeText != null) 24.dp else 0.dp),
        ) {
            Spacer(Modifier.height(24.dp))
            val priceAmountStyle = MaterialTheme.typography.displayMedium
            val durationStyle = MaterialTheme.typography.bodyLarge

            val monthDuration = stringResource(Res.string.subscriptions_duration_month)
            val halfYearDuration = stringResource(Res.string.subscriptions_duration_half_year)
            val yearDuration = stringResource(Res.string.subscriptions_duration_year)
            val priceDaily = stringResource(
                Res.string.subscriptions_price_daily,
                subscription.currency.currency,
                subscription.pricePerDay
            )
            val perDaily = stringResource(Res.string.subscriptions_price_per_daily)
            val pricePerPeriod = stringResource(
                Res.string.subscriptions_price_per,
                subscription.currency.currency,
                subscription.price,
                when (subscription) {
                    is Subscription.HalfYear -> halfYearDuration
                    is Subscription.Monthly -> monthDuration
                    is Subscription.Year -> yearDuration
                }
            )
            val price: AnnotatedString = remember(subscription) {
                buildAnnotatedString {
                    withStyle(priceAmountStyle.toSpanStyle()) {
                        append(priceDaily)
                    }
                    withStyle(durationStyle.toSpanStyle()) {
                        append(perDaily)
                    }
                }
            }

            Text(
                price,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
            )
            Spacer(Modifier.height(12.dp))

            Text(
                pricePerPeriod,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}
