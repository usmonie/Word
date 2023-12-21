package com.usmonie.word.features.subscription.data

import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus

expect class Billing {

    fun subscribeSubscriptionState(onSubscriptionChanged: (SubscriptionStatus) -> Unit)
    fun unsubscribeSubscriptionState(onSubscriptionChanged: (SubscriptionStatus) -> Unit)
}