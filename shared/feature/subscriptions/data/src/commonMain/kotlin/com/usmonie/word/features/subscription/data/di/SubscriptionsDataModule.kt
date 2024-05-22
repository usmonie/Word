package com.usmonie.word.features.subscription.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.usmonie.word.features.subscription.data.SubscriptionRepositoryImpl
import com.usmonie.word.features.subscription.domain.repository.SubscriptionRepository
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module

val subscriptionDataModule = module {
    factory<SubscriptionRepository> { SubscriptionRepositoryImpl(get(), get()) }
}

expect val datastoreModule: Module

expect val billingModule: Module
/**
 * Gets the singleton DataStore instance, creating it if necessary.
 */
fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

internal const val DATA_STORE_FILENAME = "word.preferences_pb"