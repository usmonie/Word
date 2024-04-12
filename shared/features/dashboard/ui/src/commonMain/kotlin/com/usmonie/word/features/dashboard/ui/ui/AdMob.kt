package com.usmonie.word.features.dashboard.ui.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCase

@Immutable
class AdMob(
    private val banner: @Composable (modifier: Modifier) -> Unit,
    private val rewardedInterstitial: @Composable (onAddDismissed: () -> Unit, onRewardGranted: (Int) -> Unit) -> Unit,
    private val interstitial: @Composable () -> Unit,
    private val subscriptionUseCase: SubscriptionStatusUseCase,
    val adMobState: AdMobState
) {
    @Composable
    fun Banner(modifier: Modifier = Modifier) {
        val subscriptionState by subscriptionUseCase(Unit).collectAsState(SubscriptionStatus.PURCHASED)
        if (subscriptionState != SubscriptionStatus.PURCHASED && adMobState.isBannerReady) {
            banner(modifier)
        }
    }

    @Composable
    fun RewardedInterstitial(onAddDismissed: () -> Unit, onRewardGranted: (Int) -> Unit) {
        val subscriptionState by subscriptionUseCase(Unit).collectAsState(SubscriptionStatus.PURCHASED)
        if (subscriptionState != SubscriptionStatus.PURCHASED && adMobState.isRewardReady) {
            rewardedInterstitial(onAddDismissed, onRewardGranted)
        }
    }

    @Composable
    fun Interstitial() {
        val subscriptionState by subscriptionUseCase(Unit).collectAsState(SubscriptionStatus.PURCHASED)
        if (subscriptionState != SubscriptionStatus.PURCHASED && adMobState.isInterstitialReady) {
            interstitial()
        }
    }
}

@Stable
data class AdMobState(
    var isInterstitialReady: Boolean = false,
    var isRewardReady: Boolean = false,
    var isBannerReady: Boolean = false
)
