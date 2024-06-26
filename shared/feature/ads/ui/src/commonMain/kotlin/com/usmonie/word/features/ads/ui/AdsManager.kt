package com.usmonie.word.features.ads.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.usmonie.core.domain.usecases.invoke
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCase

@Suppress("LongParameterList")
@Immutable
class AdsManager(
    private val banner: @Composable (modifier: Modifier) -> Unit,
    private val rewardedLifeInterstitial: @Composable (
        onAddDismissed: () -> Unit,
        onRewardGranted: (Int) -> Unit
    ) -> Unit,
    private val rewardedHintInterstitial: @Composable (
        onAddDismissed: () -> Unit,
        onRewardGranted: (Int) -> Unit
    ) -> Unit,
    private val rewardedNewGameInterstitial: @Composable (
        onAddDismissed: () -> Unit,
        onRewardGranted: (Int) -> Unit
    ) -> Unit,
    private val interstitial: @Composable () -> Unit,
    private val subscriptionUseCase: SubscriptionStatusUseCase,
    private val getAdState: () -> AdsManagerState
) {

    @Composable
    fun Banner(modifier: Modifier = Modifier) {
        val state = getAdMobState()
        val subscriptionState by subscriptionUseCase().collectAsState(null)
        if (subscriptionState != null && subscriptionState !is SubscriptionStatus.Purchased && state.isBannerReady) {
            banner(modifier)
        }
    }

    @Composable
    fun RewardedLifeInterstitial(onAddDismissed: () -> Unit, onRewardGranted: (Int) -> Unit) {
        val state = getAdMobState()
        val subscriptionState by subscriptionUseCase().collectAsState(null)
        if (subscriptionState != null &&
            subscriptionState !is SubscriptionStatus.Purchased &&
            state.isRewardLifeReady
        ) {
            rewardedLifeInterstitial(onAddDismissed, onRewardGranted)
        }
    }

    @Composable
    fun RewardedHintInterstitial(onAddDismissed: () -> Unit, onRewardGranted: (Int) -> Unit) {
        val state = getAdMobState()
        val subscriptionState by subscriptionUseCase().collectAsState(null)
        if (subscriptionState != null &&
            subscriptionState !is SubscriptionStatus.Purchased &&
            state.isRewardHintReady
        ) {
            rewardedHintInterstitial(onAddDismissed, onRewardGranted)
        }
    }

    @Composable
    fun RewardedNewGameInterstitial(onAddDismissed: () -> Unit, onRewardGranted: (Int) -> Unit) {
        val state = getAdMobState()
        val subscriptionState by subscriptionUseCase().collectAsState(null)
        if (subscriptionState != null &&
            subscriptionState !is SubscriptionStatus.Purchased &&
            state.isRewardNewGameReady
        ) {
            rewardedNewGameInterstitial(onAddDismissed, onRewardGranted)
        }
    }

    @Composable
    fun Interstitial() {
        val state = getAdMobState()
        val subscriptionState by subscriptionUseCase().collectAsState(null)
        if (subscriptionState != null &&
            subscriptionState !is SubscriptionStatus.Purchased &&
            state.isInterstitialReady
        ) {
            interstitial()
        }
    }

    fun getAdMobState(): AdsManagerState = getAdState()
}

@Stable
data class AdsManagerState(
    var isInterstitialReady: Boolean = false,
    var isRewardLifeReady: Boolean = false,
    var isRewardHintReady: Boolean = false,
    var isRewardNewGameReady: Boolean = false,
    var isBannerReady: Boolean = false
)

val LocalAdsManager = compositionLocalOf<AdsManager> {
    error("No AdMob provided!")
}
