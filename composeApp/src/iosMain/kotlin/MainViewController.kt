import androidx.compose.ui.window.ComposeUIViewController
import com.liftric.kvault.KVault
import com.usmonie.word.features.dashboard.data.db.DriverFactory
import com.usmonie.word.features.dashboard.data.repository.UserRepositoryImpl

fun MainViewController() = ComposeUIViewController {
    App(
        com.usmonie.word.features.dashboard.data.db.DriverFactory(),
        UserRepositoryImpl(KVault())
    )
}
