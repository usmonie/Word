import androidx.compose.ui.window.ComposeUIViewController
import com.usmonie.word.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        App()
    }
}
