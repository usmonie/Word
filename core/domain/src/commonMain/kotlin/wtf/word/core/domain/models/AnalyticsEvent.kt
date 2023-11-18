package wtf.word.core.domain.models

abstract class AnalyticsEvent(val key: String, val data: EventData) {

    interface EventData {
        override fun toString(): String
    }
}