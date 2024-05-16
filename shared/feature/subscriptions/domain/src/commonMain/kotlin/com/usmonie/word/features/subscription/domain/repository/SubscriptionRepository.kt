package com.usmonie.word.features.subscription.domain.repository

import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {
    fun getSubscriptionState(): Flow<SubscriptionStatus>
}
