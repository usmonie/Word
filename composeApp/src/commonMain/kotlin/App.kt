import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.usmonie.word.features.dashboard.data.db.DriverFactory
import com.usmonie.word.features.dashboard.data.repository.WordRepositoryImpl
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import com.usmonie.word.features.dashboard.domain.usecase.CurrentThemeUseCaseImpl
import com.usmonie.word.features.getDashboardGraph
import com.usmonie.word.features.ui.AdMob
import wtf.speech.compass.core.NavigationHost
import wtf.speech.compass.core.rememberRouteManager
import wtf.word.core.design.themes.WordColors
import wtf.word.core.design.themes.WordTheme
import wtf.word.core.design.themes.WordTypography
import wtf.word.core.domain.Analytics

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App(
    driverFactory: DriverFactory,
    userRepository: UserRepository,
    adMob: AdMob,
    analytics: Analytics
) {

    val (currentColors, currentTypography) = remember {
        val theme = CurrentThemeUseCaseImpl(userRepository).invoke(Unit)
        val colors =
            theme.colorsName?.let { WordColors.valueOf(it) } ?: WordColors.BRITISH_RACING_GREEN
        val typography = theme.fonts?.let { WordTypography.valueOf(it) } ?: WordTypography.Friendly
        Pair(colors, typography)
    }
    val (currentTheme, onCurrentColorsChanged) = remember { mutableStateOf(currentColors) }
    val (currentFonts, onCurrentFontsChanged) = remember { mutableStateOf(currentTypography) }
    val wordRepository = remember { WordRepositoryImpl(driverFactory.createDriver()) }

    val initialGraph = getDashboardGraph(
        onCurrentColorsChanged,
        onCurrentFontsChanged,
        userRepository,
        wordRepository,
        adMob,
        analytics
    )

    val routeManager = rememberRouteManager(initialGraph)
    WordTheme(currentTheme, currentFonts) {
        NavigationHost(routeManager)
    }
}
