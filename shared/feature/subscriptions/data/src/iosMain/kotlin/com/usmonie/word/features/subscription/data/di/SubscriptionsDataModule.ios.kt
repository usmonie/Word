package com.usmonie.word.features.subscription.data.di

import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import com.usmonie.word.features.subscription.data.Billing
import org.koin.core.module.dsl.factoryOf

actual val billingModule: Module = module {
    factoryOf(::Billing)
}

@OptIn(ExperimentalForeignApi::class)
actual val datastoreModule: Module = module {
    factory {
        createDataStore(
            producePath = {
                val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                requireNotNull(documentDirectory).path + "/$DATA_STORE_FILENAME"
            }
        )
    }
}