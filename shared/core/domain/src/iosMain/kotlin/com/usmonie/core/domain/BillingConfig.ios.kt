package com.usmonie.core.domain

actual object BillingConfig {
    actual val available: Boolean = true
    actual val monthlySubscriptionId: String = "plus_monthly"
    actual val halfYearSubscriptionId: String = "plus_half_year"
    actual val yearlySubscriptionId: String = "plus_yearly"
    actual val lifeProductId: String = "game_life"
    actual val tipsProductId: String = "game_tips"
}
