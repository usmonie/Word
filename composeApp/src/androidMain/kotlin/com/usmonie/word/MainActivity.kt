package com.usmonie.word

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.usmonie.core.domain.AppConfig
import com.usmonie.word.di.analyticsModule
import com.usmonie.word.di.appModule
import com.usmonie.word.di.mainModule
import com.usmonie.word.features.ads.ui.AdsManager
import com.usmonie.word.features.ads.ui.AdsManagerState
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.koinInject
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

private const val MAIN_ACTIVITY_TAG = "MAIN_ACTIVITY"

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()

    private var subscribed = false
    private var rewardedLifeAd: RewardedAd? = null
    private var rewardedHintAd: RewardedAd? = null
    private var rewardedNewGameAd: RewardedAd? = null
    private var interstitialAd: InterstitialAd? = null

    private val appConfig: AppConfig by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            androidLogger()
            modules(
                appModule,
                mainModule,
                analyticsModule,
                module {
                    single {
                        AdsManager(
                            { AdmobBanner(it) },
                            { _, onRewardGranted ->
                                showRewardedLifeAd { onRewardGranted(it.amount) }
                            },
                            { _, onRewardGranted ->
                                showRewardedHintAd {
                                    onRewardGranted(it.amount) }
                            },
                            { _, onRewardGranted ->
                                showRewardedNewGameAd { onRewardGranted(it.amount) }
                            },
                            { showInterstitialAd() },
                            get(),
                            {
                                AdsManagerState(
                                    interstitialAd != null,
                                    rewardedLifeAd != null,
                                    rewardedHintAd != null,
                                    rewardedNewGameAd != null,
                                    true
                                )
                            }
                        )
                    }
                }
            )
        }

        if (!subscribed) {
            loadAds()
        }

        setContent {
            val state by viewModel.state.collectAsState()

            LaunchedEffect(state) {
                subscribed = state.subscriptionStatus is SubscriptionStatus.Purchased
            }

            App()
        }
    }

    override fun onStart() {
        super.onStart()
        if (BuildConfig.DEBUG) {
            Log.d(MAIN_ACTIVITY_TAG, "onStart")
        }
        showInterstitialAd()
    }

    private fun loadAds() {
        loadRewardedAd(appConfig.rewardedLifeId) { rewardedLifeAd = it }
        loadRewardedAd(appConfig.rewardedHintId) { rewardedHintAd = it }
        loadRewardedAd(appConfig.rewardedNewGameId) { rewardedNewGameAd = it }
        loadInterstitialAd()
    }

    private fun loadRewardedAd(rewardedAdId: String, onUpdateAd: (RewardedAd?) -> Unit) {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            this,
            rewardedAdId,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(MAIN_ACTIVITY_TAG, adError.toString())
                    onUpdateAd(null)
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d(MAIN_ACTIVITY_TAG, "Ad was loaded.")
                    onUpdateAd(ad)
                }
            }
        )
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            appConfig.interstitialId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e(MAIN_ACTIVITY_TAG, adError.message)
                    this@MainActivity.interstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    if (BuildConfig.DEBUG) {
                        Log.d(MAIN_ACTIVITY_TAG, "ad loaded")
                    }

                    this@MainActivity.interstitialAd = interstitialAd
                }
            }
        )
    }

    private fun showInterstitialAd() {
        if (!subscribed) {
            interstitialAd?.show(this)
            loadInterstitialAd()
        }
    }

    private fun showRewardedLifeAd(onUserEarnedRewardListener: OnUserEarnedRewardListener) {
        rewardedLifeAd?.show(this, onUserEarnedRewardListener)
        loadRewardedAd(appConfig.rewardedLifeId) { rewardedLifeAd = it }
    }

    private fun showRewardedHintAd(onUserEarnedRewardListener: OnUserEarnedRewardListener) {
        rewardedLifeAd?.show(this, onUserEarnedRewardListener)
        loadRewardedAd(appConfig.rewardedHintId) { rewardedHintAd = it }
    }

    private fun showRewardedNewGameAd(onUserEarnedRewardListener: OnUserEarnedRewardListener) {
        rewardedLifeAd?.show(this, onUserEarnedRewardListener)
        loadRewardedAd(appConfig.rewardedNewGameId) { rewardedNewGameAd = it }
    }
}

@Composable
fun AdmobBanner(modifier: Modifier = Modifier, appConfig: AppConfig = koinInject()) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            // on below line specifying ad view.
            AdView(context).apply {
                // on below line specifying ad size
                // adSize = AdSize.BANNER
                // on below line specifying ad unit id
                // currently added a test ad unit id.
                setAdSize(AdSize.BANNER)
                adUnitId = appConfig.bannerId
                // calling load ad to load our ad.
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}
