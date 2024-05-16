package com.usmonie.word.features.subscription.domain.models

import kotlinx.datetime.LocalDateTime

sealed class SubscriptionStatus {
    data class Purchased(val subscription: Subscription) : SubscriptionStatus()
    data class Refunded(val subscription: Subscription) : SubscriptionStatus()
    data class Canceled(val subscription: Subscription) : SubscriptionStatus()
    data class Expired(val subscription: Subscription) : SubscriptionStatus()
    object None : SubscriptionStatus()

    data class Sale(val fromDate: LocalDateTime, val hours: Int, val subscription: Subscription) :
        SubscriptionStatus()
}

sealed class Subscription {

    abstract val id: String
    abstract val price: Float
    abstract val currency: Currency
    abstract val isDeveloperSupport: Boolean

    data class Monthly(
        override val id: String,
        override val price: Float,
        override val currency: Currency,
        override val isDeveloperSupport: Boolean = false
    ) : Subscription()

    data class HalfYear(
        override val id: String,
        override val price: Float,
        override val currency: Currency,
        override val isDeveloperSupport: Boolean = false
    ) : Subscription()

    data class Year(
        override val id: String,
        override val price: Float,
        override val currency: Currency,
        override val isDeveloperSupport: Boolean = false
    ) : Subscription()
}

data class Currency(val currency: String)
