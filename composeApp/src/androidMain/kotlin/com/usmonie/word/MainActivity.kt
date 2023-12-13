package com.usmonie.word

import App
import DefaultLogger
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.liftric.kvault.KVault
import com.usmonie.word.features.dashboard.data.repository.UserRepositoryImpl
import com.usmonie.word.features.ui.AdMob
import wtf.speech.core.ui.AdKeys

class MainActivity : ComponentActivity() {
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val driverFactory = DriverFactory(this)
        val userRepository = UserRepositoryImpl(KVault(this@MainActivity))
        val adMob = AdMob(::showInterstitial)
        val logger = DefaultLogger(Firebase.analytics)
        loadInterstitial(this)
        enableEdgeToEdge()
        setContent {
            val view = LocalView.current
            val isDark = isSystemInDarkTheme()


            SideEffect {
                val window = (view.context as Activity).window
                val insets = WindowCompat.getInsetsController(window, view)
                window.statusBarColor = Color.Transparent.toArgb()
                window.navigationBarColor = Color.Transparent.toArgb()
                insets.isAppearanceLightStatusBars = isDark
                insets.isAppearanceLightNavigationBars = isDark
            }
            App(
                userRepository,
                adMob,
                logger
            )
        }
    }

    override fun onDestroy() {
        removeInterstitial()
        super.onDestroy()
    }

    private fun loadInterstitial(context: Context) {
        InterstitialAd.load(context, AdKeys.REWARDED_LIFE_ID, //Change this with your own AdUnitID!
            AdRequest.Builder().build(), object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterstitial(context: Context, onAdDismissed: () -> Unit) {
        val activity = context.findActivity()

        if (mInterstitialAd != null && activity != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    mInterstitialAd = null
                }

                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null

                    loadInterstitial(context)
                    onAdDismissed()
                }
            }
            mInterstitialAd?.show(activity)
        }
    }

    private fun removeInterstitial() {
        mInterstitialAd?.fullScreenContentCallback = null
        mInterstitialAd = null
    }

    private fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
}
