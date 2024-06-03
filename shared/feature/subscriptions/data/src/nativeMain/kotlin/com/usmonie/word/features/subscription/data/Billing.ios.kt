package com.usmonie.word.features.subscription.data

import com.usmonie.word.features.subscription.domain.models.Subscription
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus

actual class Billing {

    actual suspend fun getSubscriptions(): List<Subscription> {
        return listOf()
    }

    actual fun subscribeSubscriptionState(onSubscriptionChanged: (SubscriptionStatus) -> Unit) {
        
    }
    actual fun unsubscribeSubscriptionState(onSubscriptionChanged: (SubscriptionStatus) -> Unit) {
        
    }

    actual suspend fun subscribe(subscription: Subscription) {
        
    }
}