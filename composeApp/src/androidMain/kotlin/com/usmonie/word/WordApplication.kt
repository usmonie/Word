package com.usmonie.word

import android.app.Application
import com.usmonie.word.di.appModule
import com.usmonie.word.features.dashboard.data.di.dashboardDataModule
import com.usmonie.word.features.dashboard.domain.di.dashboardDomainModule
import com.usmonie.word.features.dashboard.ui.di.dashboardScreensModule
import com.usmonie.word.features.details.ui.di.detailsModule
import com.usmonie.word.features.dictionary.data.di.wordDataModule
import com.usmonie.word.features.dictionary.domain.di.dictionaryDomainUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class WordApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WordApplication)
            androidLogger()
            modules(
                dictionaryDomainUseCase +
                    wordDataModule +
                    dashboardDataModule +
                    dashboardDomainModule +
                    dashboardScreensModule +
                    detailsModule +
                    appModule
            )
        }
    }
}
