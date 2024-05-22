package com.usmonie.core.domain

expect object BillingConfig {
    val available: Boolean
    val monthlySubscriptionId: String
    val halfYearSubscriptionId: String
    val yearlySubscriptionId: String

    val lifeProductId: String
    val tipsProductId: String
}

val subscriptionsIds = listOf(
    BillingConfig.halfYearSubscriptionId,
    BillingConfig.yearlySubscriptionId,
    BillingConfig.monthlySubscriptionId
)
