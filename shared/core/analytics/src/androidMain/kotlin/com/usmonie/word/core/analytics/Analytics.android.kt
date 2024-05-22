package com.usmonie.word.core.analytics

import android.content.Context
import com.amplitude.android.Amplitude
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.usmonie.word.core.analytics.models.AnalyticsEvent
import org.lighthousegames.logging.Log

actual class DefaultAnalytics(
    private val context: Context,
    private val amplitude: Amplitude,
    private val firebaseAnalytics: FirebaseAnalytics,
) : Analytics() {
    override fun log(analyticsEvent: AnalyticsEvent) {
        Log.i("WORD_APP_EVENT", analyticsEvent.toString())
        if (!context.packageName.contains("debug")) {
            firebaseAnalytics.logEvent(analyticsEvent.key) {
                param(analyticsEvent.key, analyticsEvent.data.toString())
            }
            amplitude.track(analyticsEvent.key, eventProperties = mapOf(analyticsEvent.toPair()))
        }
    }
}
