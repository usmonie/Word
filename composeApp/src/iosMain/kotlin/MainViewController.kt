
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import com.usmonie.word.App
import com.usmonie.word.core.analytics.Analytics
import com.usmonie.word.di.appModule
import com.usmonie.word.features.ads.ui.AdsManager
import com.usmonie.word.features.ads.ui.AdsManagerState
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.context.startKoin
import org.koin.dsl.module
import platform.UIKit.UIView
import platform.UIKit.UIViewController

@OptIn(ExperimentalForeignApi::class)
@Suppress("LongParameterList", "FunctionNaming")
fun MainViewController(
    analytics: Analytics,
    bannerAd: () -> UIView,
    showInterstitialAd: () -> Unit,
    showRewardedLifeInterstitialAd: (onAddDismissed: () -> Unit, onRewardGranted: (Int) -> Unit) -> Unit,
    showRewardedHintInterstitialAd: (onAddDismissed: () -> Unit, onRewardGranted: (Int) -> Unit) -> Unit,
    showRewardedNewGameInterstitialAd: (onAddDismissed: () -> Unit, onRewardGranted: (Int) -> Unit) -> Unit,
    getAdsManagerState: () -> AdsManagerState
): UIViewController {
    startKoin {
        modules(
            appModule,
            module {
                single { analytics }
                single {
                    AdsManager(
                        {
                            UIKitView(
                                factory = bannerAd,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                            )
                        },
                        { onAddMissed, onRewardGranted ->
                            showRewardedLifeInterstitialAd(
                                onAddMissed,
                                onRewardGranted
                            )
                        },
                        { onAddMissed, onRewardGranted ->
                            showRewardedHintInterstitialAd(
                                onAddMissed,
                                onRewardGranted
                            )
                        },
                        { onAddMissed, onRewardGranted ->
                            showRewardedNewGameInterstitialAd(
                                onAddMissed,
                                onRewardGranted
                            )
                        },
                        { showInterstitialAd() },
                        get(),
                        getAdsManagerState
                    )
                }
            }
        )
    }

    return ComposeUIViewController {
        App()
    }
}
