import androidx.compose.ui.uikit.ComposeUIViewControllerDelegate
import androidx.compose.ui.window.ComposeUIViewController
import com.liftric.kvault.KVault
import com.usmonie.word.features.dashboard.data.db.DriverFactory
import com.usmonie.word.features.dashboard.data.repository.UserRepositoryImpl
import com.usmonie.word.features.ui.AdMob
import platform.UIKit.UIViewController
import wtf.word.core.domain.Analytics

fun MainViewController(
    onViewDidLoad: () -> Unit,
    adMob: AdMob,
    nativeAnalytics: Analytics,
): UIViewController {
    val composeDelegate = object : ComposeUIViewControllerDelegate {
        override fun viewDidLoad() = onViewDidLoad()
    }
    return ComposeUIViewController(configure = {
        delegate = composeDelegate
    }) {
        App(
            DriverFactory(),
            UserRepositoryImpl(KVault()),
            adMob,
            DefaultLogger(nativeAnalytics)
        )
    }
}
