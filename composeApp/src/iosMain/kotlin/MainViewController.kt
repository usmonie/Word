@file:Suppress("FunctionName")

import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import com.liftric.kvault.KVault
import com.usmonie.word.features.DASHBOARD_GRAPH_ID
import com.usmonie.word.features.dashboard.data.api.WordApi
import com.usmonie.word.features.dashboard.data.di.DashboardDataComponent
import com.usmonie.word.features.dashboard.data.repository.UserRepositoryImpl
import com.usmonie.word.features.dashboard.domain.usecase.CurrentThemeUseCaseImpl
import com.usmonie.word.features.getDashboardGraph
import com.usmonie.word.features.onboarding.ui.getWelcomeGraph
import com.usmonie.word.features.subscription.data.Billing
import com.usmonie.word.features.subscription.data.getSubscriptionRepository
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCaseImpl
import com.usmonie.word.features.ui.AdMob
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIView
import platform.UIKit.UIViewController
import wtf.speech.compass.core.RouteManager
import wtf.speech.compass.core.getRouteManager
import wtf.word.core.design.themes.WordColors
import wtf.word.core.design.themes.typographies.ModernChic
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
    var currentTheme by mutableStateOf(theme.colorsName?.let { WordColors.valueOf(it) } ?: WordColors.RICH_MAROON)
    var currentFonts by mutableStateOf(theme.fonts?.let { WordTypography.valueOf(it) } ?: ModernChic)
    val wordRepository =
        DashboardDataComponent.getWordsRepository(WordApi("http://16.170.6.0"))

    val dashboardGraph = getDashboardGraph(
        { currentTheme = it },
        { currentFonts = it },
        subscriptionRepository,
        userRepository,
        wordRepository,
        admob,
        logger
    )

    var routeManager: RouteManager? = null
    val welcomeGraph = getWelcomeGraph({
        routeManager?.switchToGraph(DASHBOARD_GRAPH_ID)
    }, userRepository)

    routeManager = getRouteManager(welcomeGraph ?: dashboardGraph)
    if (welcomeGraph != null) {
        routeManager.registerGraph(dashboardGraph)
    }

    val content: @Composable () -> Unit = {
        var isSubscribed by mutableStateOf(true)

        val subscriptionStatus by subscriptionStatusUseCase(Unit).collectAsState(SubscriptionStatus.PURCHASED)

        LaunchedEffect(subscriptionStatus) {
            isSubscribed = subscriptionStatus == SubscriptionStatus.PURCHASED
        }

        LaunchedEffect(isSubscribed) {
            if (!isSubscribed) {
                if (currentTheme.paid) {
                    currentTheme = WordColors.RICH_MAROON
                }
                currentFonts = ModernChic
            }
        }

        val appConfiguration = AppConfiguration(routeManager, currentTheme, currentFonts)
        App(appConfiguration)
    }

    return ComposeUIViewController(content = content)
}
