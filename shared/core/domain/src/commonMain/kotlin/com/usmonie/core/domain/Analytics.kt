package com.usmonie.core.domain

import com.usmonie.core.domain.models.AnalyticsEvent

abstract class Analytics {
    abstract fun log(analyticsEvent: AnalyticsEvent)
}