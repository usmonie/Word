package com.usmonie.word.features.subscriptions.ui.notification

import com.usmonie.compass.viewmodel.ScreenAction
import com.usmonie.compass.viewmodel.ScreenEffect
import com.usmonie.compass.viewmodel.ScreenEvent
import com.usmonie.compass.viewmodel.ScreenState
import com.usmonie.word.features.subscription.domain.models.Subscription

sealed class SubscriptionScreenState : ScreenState {
    class Empty : SubscriptionScreenState()

    data class Sale(
        val remainingTime: String,
        val subscriptions: List<Subscription>,
        val subscriptionAdState: SubscriptionAdState
    ) : SubscriptionScreenState()

    data class Base(
        val subscriptions: List<Subscription>,
        val subscriptionAdState: SubscriptionAdState
    ) : SubscriptionScreenState()
}

sealed class SubscriptionScreenAction : ScreenAction {
    data class Subscribe(val subscription: Subscription) : SubscriptionScreenAction()
    data object Expand : SubscriptionScreenAction()
    data object Collapse : SubscriptionScreenAction()
    data object Minify : SubscriptionScreenAction()
    data object ShowSaleSubscriptions : SubscriptionScreenAction()
    data object ShowSubscriptions : SubscriptionScreenAction()
}

sealed class SubscriptionScreenEvent : ScreenEvent {
    data object Subscribed : SubscriptionScreenEvent()
    data object ShowSaleSubscriptions : SubscriptionScreenEvent()
    data object ShowSubscriptions : SubscriptionScreenEvent()
    data object Expand : SubscriptionScreenEvent()
    data object Collapse : SubscriptionScreenEvent()
    data object Minify : SubscriptionScreenEvent()
}

sealed class SubscriptionScreenEffect : ScreenEffect
