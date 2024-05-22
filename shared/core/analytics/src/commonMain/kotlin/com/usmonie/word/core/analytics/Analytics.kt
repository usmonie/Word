package com.usmonie.word.core.analytics

import com.usmonie.word.core.analytics.models.AnalyticsEvent

abstract class Analytics {
    abstract fun log(analyticsEvent: AnalyticsEvent)
}

expect class DefaultAnalytics : Analytics
