
import android.content.Context
import com.amplitude.android.Amplitude
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import org.lighthousegames.logging.Log
import wtf.word.core.domain.Analytics
import wtf.word.core.domain.models.AnalyticsEvent

actual class DefaultLogger(
    private val context: Context,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val amplitude: Amplitude
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