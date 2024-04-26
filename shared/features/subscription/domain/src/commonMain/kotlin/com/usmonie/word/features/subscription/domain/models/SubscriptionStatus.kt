package com.usmonie.word.features.subscription.domain.models

import kotlin.time.Duration

sealed class SubscriptionStatus {
    class Purchased: SubscriptionStatus()
    class Refunded: SubscriptionStatus()
    class Canceled: SubscriptionStatus()
    class Expired: SubscriptionStatus()
    class None: SubscriptionStatus()

    class Sale(): SubscriptionStatus()
}