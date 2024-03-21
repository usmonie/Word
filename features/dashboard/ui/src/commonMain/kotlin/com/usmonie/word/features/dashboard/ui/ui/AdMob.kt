package com.usmonie.word.features.dashboard.ui.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCase

@Immutable
class AdMob(
    val banner: @Composable (adKey: String, modifier: Modifier) -> Unit,
    val rewardedInterstitial: @Composable (onAddDismissed: () -> Unit) -> Unit,
    val startup: @Composable (adKey: String, modifier: Modifier) -> Unit,
    val subscriptionUseCase: SubscriptionStatusUseCase
) {
    @Composable
    fun Banner(adKey: String, modifier: Modifier = Modifier) {
        val subscriptionState by subscriptionUseCase(Unit).collectAsState(SubscriptionStatus.PURCHASED)
        if (subscriptionState != SubscriptionStatus.PURCHASED) {
            banner(adKey, modifier)
        }
    }

    @Composable
    fun RewardedInterstitial(onAddDismissed: () -> Unit) {
        val subscriptionState by subscriptionUseCase(Unit).collectAsState(SubscriptionStatus.PURCHASED)
        if (subscriptionState != SubscriptionStatus.PURCHASED) {
            rewardedInterstitial(onAddDismissed)
        }
    }

    @Composable
    fun Startup(adKey: String, modifier: Modifier = Modifier) {
        val subscriptionState by subscriptionUseCase(Unit).collectAsState(SubscriptionStatus.PURCHASED)
        if (subscriptionState != SubscriptionStatus.PURCHASED) {
            startup(adKey, modifier)
        }
    }
}