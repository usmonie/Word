package com.usmonie.word.features.subscription.data

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

data class SubscriptionPreferencesState(
    val saleStartedTime: Long,
    val saleDuration: Int
)

internal object SubscriptionPreferences {
    val SUBSCRIPTION_SALE_STARTED = longPreferencesKey("SUBSCRIPTION_SALE_STARTED")
    val SUBSCRIPTION_SALE_DURATION = intPreferencesKey("SUBSCRIPTION_SALE_DURATION")
}
