@file:Suppress("FunctionName")

import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.uikit.ComposeUIViewControllerDelegate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import com.liftric.kvault.KVault
import com.usmonie.word.features.dashboard.data.api.WordApi
import com.usmonie.word.features.dashboard.data.di.DashboardDataComponent
import com.usmonie.word.features.dashboard.data.repository.UserRepositoryImpl
import com.usmonie.word.features.dashboard.domain.usecase.CurrentThemeUseCaseImpl
import com.usmonie.word.features.getDashboardGraph
import com.usmonie.word.features.subscription.data.Billing
import com.usmonie.word.features.subscription.data.getSubscriptionRepository
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCaseImpl
import com.usmonie.word.features.ui.AdMob
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIView
import platform.UIKit.UIViewController
import wtf.speech.compass.core.getRouteManager
import wtf.word.core.design.themes.WordColors
import wtf.word.core.design.themes.typographies.Friendly
import wtf.word.core.design.themes.typographies.WordTypography
import wtf.word.core.domain.Analytics

@OptIn(ExperimentalForeignApi::class)
fun MainViewController(
    onViewDidLoad: () -> Unit,
    bannerUiView: () -> UIView,
    rewardedInterstitialView: () -> Unit,
    nativeAnalytics: Analytics,
): UIViewController {
    val subscriptionRepository = getSubscriptionRepository(Billing())
    val userRepository = UserRepositoryImpl(KVault())
    val subscriptionStatusUseCase = SubscriptionStatusUseCaseImpl(subscriptionRepository)

    val logger = DefaultLogger(nativeAnalytics)
    val admob = AdMob(
        { _, modifier ->
            UIKitView(
                modifier = modifier.heightIn(max = 54.dp),
                factory = bannerUiView
            )
        },
        { rewardedInterstitialView() },
        { _, _ -> },
        subscriptionStatusUseCase
    )

    val theme = CurrentThemeUseCaseImpl(userRepository).invoke(Unit)
    val colors = theme.colorsName?.let { WordColors.valueOf(it) } ?: WordColors.RICH_MAROON
    val typography = theme.fonts?.let { WordTypography.valueOf(it) } ?: Friendly

    val (currentTheme, onCurrentColorsChanged) =  mutableStateOf(colors)
    val (currentFonts, onCurrentFontsChanged) =  mutableStateOf(typography)
    val wordRepository =
         DashboardDataComponent.getWordsRepository(WordApi("http://16.170.6.0"))

    val initialGraph = getDashboardGraph(
        onCurrentColorsChanged,
        onCurrentFontsChanged,
        subscriptionRepository,
        userRepository,
        wordRepository,
        admob,
        logger
    )

    val routeManager = getRouteManager(initialGraph)
    val appConfiguration = AppConfiguration(routeManager, currentTheme, currentFonts)
    val content: @Composable () -> Unit = {
        App(appConfiguration)
    }

    return ComposeUIViewController(
        configure = {
            delegate = object : ComposeUIViewControllerDelegate {
                override fun viewDidAppear(animated: Boolean) {
                    super.viewDidAppear(animated)
                    println("LIFECYCLE: viewDidAppear $this")
                }

                override fun viewDidDisappear(animated: Boolean) {
                    super.viewDidDisappear(animated)
                    println("LIFECYCLE: viewDidDisappear $this")
                }

                override fun viewDidLoad() {
                    super.viewDidLoad()
                    println("LIFECYCLE: viewDidLoad $this")
                }

                override fun viewWillAppear(animated: Boolean) {
                    super.viewWillAppear(animated)
                    println("LIFECYCLE: viewWillAppear $this")
                }

                override fun viewWillDisappear(animated: Boolean) {
                    super.viewWillDisappear(animated)
                    println("LIFECYCLE: viewWillDisappear $this")
                }
            }
        },
        content = content
    )
}
