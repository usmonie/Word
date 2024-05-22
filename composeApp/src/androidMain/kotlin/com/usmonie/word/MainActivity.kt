package com.usmonie.word

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.usmonie.core.kit.design.themes.WordThemes
import com.usmonie.core.kit.design.themes.typographies.Friendly
import com.usmonie.word.di.appModule
import com.usmonie.word.features.dashboard.data.di.dashboardDataModule
import com.usmonie.word.features.dashboard.domain.di.dashboardDomainModule
import com.usmonie.word.features.dashboard.ui.di.dashboardUiModule
import com.usmonie.word.features.details.ui.di.wordDetailsUiModule
import com.usmonie.word.features.dictionary.data.di.wordDataModule
import com.usmonie.word.features.dictionary.domain.di.dictionaryDomainUseCase
import com.usmonie.word.features.favorites.ui.di.favoritesUiModule
import com.usmonie.word.features.subscription.data.di.billingModule
import com.usmonie.word.features.subscription.data.di.datastoreModule
import com.usmonie.word.features.subscription.data.di.subscriptionDataModule
import com.usmonie.word.features.subscription.domain.di.subscriptionDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            androidLogger()
            modules(
                dictionaryDomainUseCase,
                datastoreModule,
                billingModule,
                wordDataModule,
                subscriptionDomainModule,
                subscriptionDataModule,
                dashboardDataModule,
                dashboardDomainModule,
                dashboardUiModule,
                favoritesUiModule,
                wordDetailsUiModule,
                appModule
            )
        }
//        val amplitude = Amplitude(
//            Configuration(
//                apiKey = appKeys.amplitudeKey,
//                context = applicationContext,
//                defaultTracking = DefaultTrackingOptions.ALL,
//            )
//        )

//        val logger = DefaultLogger(applicationContext, Firebase.analytics, amplitude)

        enableEdgeToEdge()
        setContent {
            App(appState = AppState(WordThemes.DEEP_INDIGO, Friendly))
        }
    }
}
