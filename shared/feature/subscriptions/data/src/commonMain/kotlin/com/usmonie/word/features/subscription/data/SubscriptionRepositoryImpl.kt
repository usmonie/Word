package com.usmonie.word.features.subscription.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.usmonie.word.features.subscription.domain.models.Sale
import com.usmonie.word.features.subscription.domain.models.Subscription
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscription.domain.repository.SubscriptionRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal class SubscriptionRepositoryImpl(
    private val billing: Billing,
    private val dataStore: DataStore<Preferences>
) : SubscriptionRepository {
    override fun getSubscriptionState(): Flow<SubscriptionStatus> = callbackFlow {
        val callback: (SubscriptionStatus) -> Unit = {
            trySend(it)
        }
        billing.subscribeSubscriptionState(callback)

        awaitClose {
            billing.unsubscribeSubscriptionState(callback)
        }
    }

    override suspend fun getSubscriptions(): List<Subscription> {
        return billing.getSubscriptions()
    }

    override suspend fun subscribe(subscription: Subscription) {
        billing.subscribe(subscription)
    }

    override suspend fun getStartedSaleTime(): Flow<Sale> {
        dataStore.edit {
            if (it[SubscriptionPreferences.SUBSCRIPTION_SALE_STARTED] == null) {
                it[SubscriptionPreferences.SUBSCRIPTION_SALE_STARTED] =
                    Clock.System.now().epochSeconds
                it[SubscriptionPreferences.SUBSCRIPTION_SALE_DURATION] = 24
            }
        }

        return dataStore.data
            .map {
                val startedTime = it[SubscriptionPreferences.SUBSCRIPTION_SALE_STARTED]
                    ?: Clock.System.now().epochSeconds

                val time = Instant.fromEpochMilliseconds(startedTime)
                    .toLocalDateTime(TimeZone.currentSystemDefault())

                Sale(time, it[SubscriptionPreferences.SUBSCRIPTION_SALE_DURATION] ?: 24)
            }
    }

    override suspend fun setStartedSaleTime(time: Long) {
        dataStore.updateData {
            it
        }
    }
}
