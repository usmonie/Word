import androidx.compose.ui.window.ComposeUIViewController
import com.usmonie.word.App
import com.usmonie.word.di.appModule
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    startKoin {
        modules(appModule)
    }
    return ComposeUIViewController {
        App()
    }
}
