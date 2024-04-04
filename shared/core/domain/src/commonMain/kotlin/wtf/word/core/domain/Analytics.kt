package wtf.word.core.domain

import wtf.word.core.domain.models.AnalyticsEvent

abstract class Analytics {
    abstract fun log(analyticsEvent: AnalyticsEvent)
}