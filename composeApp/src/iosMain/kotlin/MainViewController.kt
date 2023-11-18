
import androidx.compose.ui.window.ComposeUIViewController
import com.liftric.kvault.KVault
import com.usmonie.word.features.dashboard.data.db.DriverFactory
import com.usmonie.word.features.dashboard.data.repository.UserRepositoryImpl
import com.usmonie.word.features.ui.AdMob
import wtf.word.core.domain.Analytics

fun MainViewController(adMob: AdMob, nativeAnalytics: Analytics) = ComposeUIViewController {
    App(
        DriverFactory(),
        UserRepositoryImpl(KVault()),
        adMob,
        DefaultLogger(nativeAnalytics)
    )
}
