
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import wtf.speech.compass.core.NavigationHost
import wtf.speech.compass.core.RouteManager
import wtf.word.core.design.themes.WordColors
import wtf.word.core.design.themes.WordTheme
import wtf.word.core.design.themes.typographies.WordTypography

@Stable
data class AppConfiguration(
    val routeManager: RouteManager,
    val currentTheme: WordColors,
    val currentFonts: WordTypography
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App(appConfiguration: AppConfiguration) {
    val routeManager = appConfiguration.routeManager
    BackHandler {
        routeManager.navigateBack()
    }

    WordTheme(appConfiguration.currentTheme, appConfiguration.currentFonts) {
        NavigationHost(routeManager, modifier = Modifier.fillMaxSize())
    }
}

@Composable
expect fun BackHandler(onBack: () -> Unit)
