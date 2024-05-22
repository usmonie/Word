package com.usmonie.word.features.subscription.domain.repository

import com.usmonie.word.features.subscription.domain.models.Sale
import com.usmonie.word.features.subscription.domain.models.Subscription
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {
    fun getSubscriptionState(): Flow<SubscriptionStatus>
    suspend fun getSubscriptions(): List<Subscription>

    suspend fun subscribe(subscription: Subscription)

    suspend fun getStartedSaleTime(): Flow<Sale>

    suspend fun setStartedSaleTime(time: Long)
}
