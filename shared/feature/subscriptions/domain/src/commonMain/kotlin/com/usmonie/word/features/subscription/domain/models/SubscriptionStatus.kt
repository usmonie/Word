package com.usmonie.word.features.subscription.domain.models

import kotlinx.datetime.LocalDateTime
import kotlin.math.pow
import kotlin.math.round

sealed class SubscriptionStatus {
    data class Purchased(val subscription: Subscription) : SubscriptionStatus()
    data class Refunded(val subscription: Subscription) : SubscriptionStatus()
    data class Canceled(val subscription: Subscription) : SubscriptionStatus()
    data class Expired(val subscription: Subscription) : SubscriptionStatus()
    object None : SubscriptionStatus()
}

data class Sale(
    val fromDate: LocalDateTime,
    val saleDuration: Int,
) : SubscriptionStatus()

sealed class Subscription {

    abstract val id: String
    abstract val price: Float
    abstract val formattedPrice: String
    abstract val currency: Currency
    abstract val isDeveloperSupport: Boolean
    abstract val days: Int

    val pricePerDay
        get() = (price / days).round(2)

    data class Monthly(
        override val id: String,
        override val price: Float,
        override val formattedPrice: String,
        override val currency: Currency,
        override val isDeveloperSupport: Boolean = false,
    ) : Subscription() {
        override val days: Int = 30
    }

    data class HalfYear(
        override val id: String,
        override val price: Float,
        override val formattedPrice: String,
        override val currency: Currency,
        override val isDeveloperSupport: Boolean = false
    ) : Subscription() {
        override val days: Int = 182
    }

    data class Year(
        override val id: String,
        override val price: Float,
        override val formattedPrice: String,
        override val currency: Currency,
        override val isDeveloperSupport: Boolean = false
    ) : Subscription() {
        override val days: Int = 365
    }
}

data class Currency(val currency: String)

fun Float.round(decimals: Int): Float {
    val multiplier = 10f.pow(decimals)
    return round(this * multiplier) / multiplier
}
