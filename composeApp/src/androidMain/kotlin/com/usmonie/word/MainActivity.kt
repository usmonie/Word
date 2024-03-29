package com.usmonie.word

import App
import AppConfiguration
import DefaultLogger
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ReportDrawn
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import com.amplitude.android.Amplitude
import com.amplitude.android.Configuration
import com.amplitude.android.DefaultTrackingOptions
import com.android.billingclient.api.BillingClient
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.liftric.kvault.KVault
import com.usmonie.word.features.dashboard.data.api.WordApi
import com.usmonie.word.features.dashboard.data.di.DashboardDataComponent
import com.usmonie.word.features.dashboard.data.repository.UserRepositoryImpl
import com.usmonie.word.features.dashboard.domain.usecase.CurrentThemeUseCaseImpl
import com.usmonie.word.features.dashboard.ui.DASHBOARD_GRAPH_ID
import com.usmonie.word.features.dashboard.ui.getDashboardGraph
import com.usmonie.word.features.dashboard.ui.ui.AdMob
import com.usmonie.word.features.onboarding.ui.FirebaseAuthenticationUtils
import com.usmonie.word.features.onboarding.ui.getWelcomeGraph
import com.usmonie.word.features.subscription.data.Billing
import com.usmonie.word.features.subscription.data.getSubscriptionRepository
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCaseImpl
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import wtf.speech.compass.core.RouteManager
import wtf.speech.compass.core.rememberRouteManager
import wtf.speech.core.ui.AppKeys
import wtf.word.core.design.themes.WordColors
import wtf.word.core.design.themes.typographies.ModernChic
import wtf.word.core.design.themes.typographies.WordTypography

class MainActivity : ComponentActivity() {
    private var mInterstitialAd: InterstitialAd? = null
    private val appOpenAdManager: AppOpenAdManager by lazy(LazyThreadSafetyMode.NONE) {
        AppOpenAdManager()
    }

    private var isSubscribed by mutableStateOf(true)
    private lateinit var routeManager: RouteManager

    private val signInClient by lazy {
        Identity.getSignInClient(this)
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {

            val user: SignInCredential = signInClient.getSignInCredentialFromIntent(result.data)
            val credential =
                com.google.firebase.auth.GoogleAuthProvider.getCredential(user.googleIdToken, null)

            FirebaseAuthenticationUtils.login(credential)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val amplitude = Amplitude(
            Configuration(
                apiKey = if (packageName.contains("debug")) {
                    AppKeys.AMPLITUDE_DEBUG_KEY
                } else {
                    AppKeys.AMPLITUDE_KEY
                },
                context = applicationContext,
                defaultTracking = DefaultTrackingOptions.ALL,
            )
        )

        val logger = DefaultLogger(applicationContext, Firebase.analytics, amplitude)

        val billing = BillingClient.newBuilder(this)
        val subscriptionRepository = getSubscriptionRepository(Billing(billing))
        val subscriptionStatusUseCase = SubscriptionStatusUseCaseImpl(subscriptionRepository)
        val userRepository = UserRepositoryImpl(
            KVault(this@MainActivity),
            dev.gitlive.firebase.Firebase.firestore,
            dev.gitlive.firebase.Firebase.auth
        )

        val adMob = AdMob(
            { adKey, modifier ->
                AndroidView(
                    modifier = modifier,
                    factory = { context ->
                        AdView(context).apply {
                            adUnitId = adKey
                            setAdSize(AdSize.FULL_BANNER)
                            loadAd(AdRequest.Builder().build())
                        }
                    },
                )
            },
            { onAddDismissed -> showInterstitial(this, onAddDismissed) },
            { _, _ -> },
            subscriptionStatusUseCase
        )
//        GoogleAuthProvider.create(credentials = GoogleAuthCredentials(serverId = AppKeys.SERVER_CLIENT_ID))

        loadInterstitial(this)
        enableEdgeToEdge()
        setContent {
            val view = LocalView.current
            val isDark = isSystemInDarkTheme()
            val subscriptionStatus by subscriptionStatusUseCase(Unit)
                .collectAsState(initial = SubscriptionStatus.PURCHASED)

            LaunchedEffect(subscriptionStatus) {
                isSubscribed = subscriptionStatus == SubscriptionStatus.PURCHASED
            }

            SideEffect {
                val window = (view.context as Activity).window
                val insets = WindowCompat.getInsetsController(window, view)
                window.statusBarColor = Color.Transparent.toArgb()
                window.navigationBarColor = Color.Transparent.toArgb()
                insets.isAppearanceLightStatusBars = isDark
                insets.isAppearanceLightNavigationBars = isDark
            }

            val (currentColors, currentTypography) = rememberTheme(userRepository)
            val (currentTheme, onCurrentColorsChanged) = remember {
                mutableStateOf(currentColors)
            }
            val (currentFonts, onCurrentFontsChanged) = remember {
                mutableStateOf(currentTypography)
            }
            val wordRepository = remember {
                DashboardDataComponent.getWordsRepository(WordApi("http://16.170.6.0"))
            }

            val dashboardGraph = remember(
                onCurrentColorsChanged,
                onCurrentColorsChanged,
                subscriptionRepository,
                userRepository,
                wordRepository,
                adMob,
                logger
            ) {
                getDashboardGraph(
                    onCurrentColorsChanged,
                    onCurrentFontsChanged,
                    subscriptionRepository,
                    userRepository,
                    wordRepository,
                    adMob,
                    logger
                )
            }

            val welcomeGraph = getWelcomeGraph(
                { routeManager.switchToGraph(DASHBOARD_GRAPH_ID) },
                userRepository,
                logger
            )

            routeManager = rememberRouteManager(welcomeGraph ?: dashboardGraph)
            if (welcomeGraph != null) {
                routeManager.registerGraph(dashboardGraph)
            }
            App(AppConfiguration(routeManager, currentTheme, currentFonts))
            ReportDrawn()
        }
    }

    @Composable
    private fun rememberTheme(userRepository: UserRepositoryImpl) =
        remember {
            val theme = CurrentThemeUseCaseImpl(userRepository).invoke(Unit)
            val userSelectedColor = theme.colorsName?.let { WordColors.valueOf(it) }
                ?: WordColors.RICH_MAROON
            val colors = when {
                !isSubscribed && userSelectedColor.paid -> WordColors.RICH_MAROON
                else -> userSelectedColor
            }
            val fonts = theme.fonts
            val typography = if (isSubscribed && fonts != null) {
                WordTypography.valueOf(fonts)
            } else {
                ModernChic
            }

            Pair(colors, typography)
        }

    override fun onStart() {
        super.onStart()
        if (!appOpenAdManager.isShowingAd && !isSubscribed) {
            appOpenAdManager.showAdIfAvailable(this)
        }
    }

    override fun onDestroy() {
        removeInterstitial()
        super.onDestroy()
    }

    private fun loadInterstitial(context: Context) {
        InterstitialAd.load(context, AppKeys.REWARDED_LIFE_ID,
            AdRequest.Builder().build(), object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            }
        )
    }

    private fun showInterstitial(context: Context, onAdDismissed: () -> Unit) {
        val activity = context.findActivity()

        if (mInterstitialAd != null && activity != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    mInterstitialAd = null
                }

                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null

                    loadInterstitial(context)
                    onAdDismissed()
                }
            }
            mInterstitialAd?.show(activity)
        }
    }

    private fun removeInterstitial() {
        mInterstitialAd?.fullScreenContentCallback = null
        mInterstitialAd = null
    }

    private fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
}
