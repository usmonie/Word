import wtf.word.core.domain.Analytics
import wtf.word.core.domain.models.AnalyticsEvent

actual class DefaultLogger(private val nativeAnalytics: Analytics) : Analytics() {
    override fun log(analyticsEvent: AnalyticsEvent) {
        nativeAnalytics.log(analyticsEvent)
    }
}