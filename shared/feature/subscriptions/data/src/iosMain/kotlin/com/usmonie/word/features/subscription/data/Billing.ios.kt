package com.usmonie.word.features.subscription.data

import com.usmonie.word.features.subscription.domain.models.Subscription
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus

actual class Billing {

    private val subscribers: MutableList<(SubscriptionStatus) -> Unit> = mutableListOf()


    actual fun subscribeSubscriptionState(onSubscriptionChanged: (SubscriptionStatus) -> Unit) {
        onSubscriptionChanged(SubscriptionStatus.None)
        subscribers.add(onSubscriptionChanged)
    }

    actual fun unsubscribeSubscriptionState(onSubscriptionChanged: (SubscriptionStatus) -> Unit) {
        subscribers.remove(onSubscriptionChanged)
    }

    actual suspend fun getSubscriptions(): List<Subscription> {
        return emptyList()
    }

    actual suspend fun subscribe(subscription: Subscription) {
    }
}
