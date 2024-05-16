import androidx.compose.ui.window.ComposeUIViewController
import com.usmonie.word.App
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIViewController

@OptIn(ExperimentalForeignApi::class)
fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        App()
    }
}
