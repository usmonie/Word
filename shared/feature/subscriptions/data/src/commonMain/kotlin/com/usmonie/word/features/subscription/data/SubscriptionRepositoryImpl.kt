package com.usmonie.word.features.subscription.data

import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscription.domain.repository.SubscriptionRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

internal class SubscriptionRepositoryImpl(
    private val billing: Billing
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
}

fun getSubscriptionRepository(
    billing: Billing
): SubscriptionRepository = SubscriptionRepositoryImpl(billing)