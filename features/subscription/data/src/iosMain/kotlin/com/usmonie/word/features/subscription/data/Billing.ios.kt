package com.usmonie.word.features.subscription.data

import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus

actual class Billing {

    private val subscribers: MutableList<(SubscriptionStatus) -> Unit> = mutableListOf()

    actual fun subscribeSubscriptionState(onSubscriptionChanged: (SubscriptionStatus) -> Unit) {
        subscribers.add(onSubscriptionChanged)

        onSubscriptionChanged(SubscriptionStatus.NONE)
    }

    actual fun unsubscribeSubscriptionState(onSubscriptionChanged: (SubscriptionStatus) -> Unit) {
        subscribers.remove(onSubscriptionChanged)
    }
}
