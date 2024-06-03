package com.usmonie.word.features.subscription.data.di

import com.usmonie.word.features.subscription.data.Billing
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual val billingModule: Module = module {
    factoryOf(::Billing)
}

@OptIn(ExperimentalForeignApi::class)
actual val datastoreModule: Module = module {
    single {
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
