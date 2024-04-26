package com.usmonie.word.features.dashboard.ui.ui.subscription;

sealed class SubscriptionSaleState() {
    abstract val duration: String

    data class Expanded(override val duration: String) : SubscriptionSaleState()
    data class Ad(override val duration: String) : SubscriptionSaleState()
}
