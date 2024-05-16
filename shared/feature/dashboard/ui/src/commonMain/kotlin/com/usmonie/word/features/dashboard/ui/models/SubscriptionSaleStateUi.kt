package com.usmonie.word.features.dashboard.ui.models

sealed class SubscriptionSaleStateUi {
    abstract val duration: String

    data class Expanded(override val duration: String) : SubscriptionSaleStateUi()
    data class Ad(override val duration: String) : SubscriptionSaleStateUi()
}
