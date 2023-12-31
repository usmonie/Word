
import com.google.firebase.BuildConfig
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import wtf.word.core.domain.Analytics
import wtf.word.core.domain.models.AnalyticsEvent

actual class DefaultLogger(private val firebaseAnalytics: FirebaseAnalytics) : Analytics() {
    init {
        firebaseAnalytics.resetAnalyticsData()
    }

    override fun log(analyticsEvent: AnalyticsEvent) {
        if (BuildConfig.DEBUG) {
            firebaseAnalytics.logEvent(analyticsEvent.key) {
                param(analyticsEvent.key, analyticsEvent.data.toString())
            }
        }
    }
}