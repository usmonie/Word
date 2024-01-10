package com.usmonie.word

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import wtf.speech.core.ui.AppKeys

class WordApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        MobileAds.initialize(this) { }
    }
}

internal class AppOpenAdManager {
    private var appOpenAd: AppOpenAd? = null
    private var isLoadingAd = false
    var isShowingAd = false

    fun showAdIfAvailable(activity: Activity) {
        // If the app open ad is already showing, do not show the ad again.
        if (isShowingAd) {
            Log.d(LOG_TAG, "The app open ad is already showing.")
            return
        }

        // If the app open ad is not available yet, invoke the callback then load the ad.
        if (!isAdAvailable) {
            Log.d(LOG_TAG, "The app open ad is not ready yet.")
//            onShowAdCompleteListener.onShowAdComplete();
            loadAd(activity)
            return
        }

        isShowingAd = true
        appOpenAd?.show(activity)
    }

    /** Request an ad.  */
    private fun loadAd(context: Context) {
        // Do not load ad if there is an unused ad or one is already loading.
        // Do not load ad if there is an unused ad or one is already loading.
        if (isLoadingAd || isAdAvailable) {
            return
        }

        isLoadingAd = true
        val request: AdRequest = AdRequest.Builder().build()
        AppOpenAd.load(
            context,
            AD_UNIT_ID,
            request,
            object : AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    // Called when an app open ad has loaded.
                    Log.d(LOG_TAG, "Ad was loaded.")
                    appOpenAd = ad
                    isLoadingAd = false
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Called when an app open ad has failed to load.
                    Log.d(LOG_TAG, loadAdError.message)
                    isLoadingAd = false
                }
            }
        )

    }

    private val isAdAvailable: Boolean
        /** Check if ad exists and can be shown.  */
        get() = appOpenAd != null

    companion object {
        private const val LOG_TAG = "AppOpenAdManager"
        private const val AD_UNIT_ID = AppKeys.STARTUP_ID
    }
}