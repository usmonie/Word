package com.usmonie.word.features.subscription.data.di

import android.app.Activity
import com.usmonie.word.features.subscription.data.Billing
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val billingModule: Module = module {
    factory { Billing(androidContext() as Activity) }
}

actual val datastoreModule: Module = module {
    single {
        createDataStore(
            producePath = { androidContext().filesDir.resolve(DATA_STORE_FILENAME).absolutePath }
        )
    }
}