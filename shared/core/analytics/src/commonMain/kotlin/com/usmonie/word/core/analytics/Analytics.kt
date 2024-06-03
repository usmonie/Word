package com.usmonie.word.core.analytics

import com.usmonie.word.core.analytics.models.AnalyticsEvent

fun interface Analytics {
    fun log(analyticsEvent: AnalyticsEvent)
}

