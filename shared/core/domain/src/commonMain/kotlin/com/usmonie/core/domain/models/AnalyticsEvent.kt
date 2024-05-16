package com.usmonie.core.domain.models

abstract class AnalyticsEvent(val key: String, val data: EventData) {

    interface EventData {
        override fun toString(): String
    }

    fun toPair(): Pair<String, EventData> {
        return Pair(key, data)
    }
}