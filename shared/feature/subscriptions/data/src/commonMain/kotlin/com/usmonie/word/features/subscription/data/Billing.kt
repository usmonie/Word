package com.usmonie.word.features.subscription.data

import com.usmonie.word.features.subscription.domain.models.Subscription
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus

expect class Billing {

    suspend fun getSubscriptions(): List<Subscription>

    fun subscribeSubscriptionState(onSubscriptionChanged: (SubscriptionStatus) -> Unit)
    fun unsubscribeSubscriptionState(onSubscriptionChanged: (SubscriptionStatus) -> Unit)

    suspend fun subscribe(subscription: Subscription)
}
