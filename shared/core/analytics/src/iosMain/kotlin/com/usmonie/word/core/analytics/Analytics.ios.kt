package com.usmonie.word.core.analytics

import com.usmonie.word.core.analytics.models.AnalyticsEvent

actual class DefaultAnalytics : Analytics() {
    override fun log(analyticsEvent: AnalyticsEvent) = Unit // TODO: add iOS implementation
}
