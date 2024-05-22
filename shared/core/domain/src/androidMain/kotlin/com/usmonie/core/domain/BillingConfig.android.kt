package com.usmonie.core.domain

actual object BillingConfig {
    actual val available: Boolean = true
    actual val monthlySubscriptionId: String = "plus"
    actual val halfYearSubscriptionId: String = "plus-half-year"
    actual val yearlySubscriptionId: String = "plus-year"
    actual val lifeProductId: String = "game_life"
    actual val tipsProductId: String = "game_tips"
}
