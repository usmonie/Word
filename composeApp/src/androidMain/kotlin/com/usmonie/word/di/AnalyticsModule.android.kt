package com.usmonie.word.di

import com.amplitude.android.Amplitude
import com.amplitude.android.Configuration
import com.amplitude.android.DefaultTrackingOptions
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import com.usmonie.core.domain.AppConfig
import com.usmonie.word.core.analytics.Analytics
import com.usmonie.word.core.analytics.models.AnalyticsEvent
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import org.lighthousegames.logging.Log

val analyticsModule: Module = module {
    single<Analytics> {
        val appConfig: AppConfig = get()
        val amplitude = Amplitude(
            Configuration(
                appConfig.amplitudeKey,
                androidContext(),
                defaultTracking = DefaultTrackingOptions.ALL,
            )
        )
        val firebaseAnalytics = Firebase.analytics
        Analytics { event: AnalyticsEvent ->
            Log.i("WORD_APP_EVENT", event.toString())
            if (appConfig is AppConfig.Release) {
                firebaseAnalytics.logEvent(event.key) {
                    param(event.key, event.data.toString())
                }
                amplitude.track(event.key, eventProperties = mapOf(event.toPair()))
            }
        }
    }
}
