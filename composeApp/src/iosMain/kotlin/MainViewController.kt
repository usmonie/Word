@file:Suppress("FunctionName")

import androidx.compose.foundation.layout.heightIn
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.uikit.ComposeUIViewControllerDelegate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import com.liftric.kvault.KVault
import com.usmonie.word.features.dashboard.data.repository.UserRepositoryImpl
import com.usmonie.word.features.subscription.data.Billing
import com.usmonie.word.features.subscription.data.getSubscriptionRepository
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCaseImpl
import com.usmonie.word.features.ui.AdMob
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIView
import platform.UIKit.UIViewController
import wtf.word.core.domain.Analytics

@OptIn(ExperimentalForeignApi::class)
fun MainViewController(
    onViewDidLoad: () -> Unit,
    bannerUiView: () -> UIView,
    rewardedInterstitialView: () -> Unit,
    nativeAnalytics: Analytics,
): UIViewController {
    val composeDelegate = object : ComposeUIViewControllerDelegate {
        override fun viewDidLoad() = onViewDidLoad()
    }
    val subscriptionRepository = getSubscriptionRepository(Billing())
    return ComposeUIViewController(
        configure = { delegate = composeDelegate },
        content = {
            App(
                UserRepositoryImpl(KVault()),
                subscriptionRepository,
                AdMob(
                    { _, modifier ->
                        UIKitView(
                            modifier = modifier.heightIn(max = 54.dp),
                            factory = bannerUiView
                        )
                    },
                    { rewardedInterstitialView() },
                    { _, _ -> },
                    SubscriptionStatusUseCaseImpl(subscriptionRepository)
                ),
                DefaultLogger(nativeAnalytics)
            )
        }
    )
}
