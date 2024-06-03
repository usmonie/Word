package com.usmonie.word

import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus

data class MainState(
    val subscriptionStatus: SubscriptionStatus,

)
