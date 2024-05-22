package com.usmonie.word.features.details.ui.notification

import androidx.compose.runtime.Immutable
import com.usmonie.compass.viewmodel.StateViewModel
import com.usmonie.word.features.subscription.domain.models.Currency
import com.usmonie.word.features.subscription.domain.models.Subscription
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscription.domain.usecase.SubscribeUseCase
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCase

@Immutable
class SubscriptionViewModel(
    private val subscribeUseCase: SubscribeUseCase,
    private val subscriptionStatusUseCase: SubscriptionStatusUseCase
) : StateViewModel<
    SubscriptionScreenState,
    SubscriptionScreenAction,
    SubscriptionScreenEvent,
    SubscriptionScreenEffect
    >(
    SubscriptionScreenState.Empty()
) {

    private val saleSubscriptions = listOf(
        Subscription.HalfYear("", 9.99f, "", Currency("$"), false),
        Subscription.Year("", 14.99f, "", Currency("$"), false),
        Subscription.Monthly("", 1.99f, "", Currency("$"), false),
    )

    init {
        viewModelScope.launchSafe {
            subscriptionStatusUseCase(Unit).collect {
                if (it !is SubscriptionStatus.Purchased) {
                    handleAction(SubscriptionScreenAction.ShowSaleSubscriptions)
                }
            }
        }
    }

    override fun SubscriptionScreenState.reduce(event: SubscriptionScreenEvent) = when (event) {
        SubscriptionScreenEvent.Expand -> updateAdState(SubscriptionAdState.EXPANDED)
        SubscriptionScreenEvent.Collapse -> updateAdState(SubscriptionAdState.COLLAPSED)
        SubscriptionScreenEvent.Minify -> updateAdState(SubscriptionAdState.MINIFIED)
        SubscriptionScreenEvent.Subscribed -> SubscriptionScreenState.Empty()
        SubscriptionScreenEvent.ShowSaleSubscriptions -> SubscriptionScreenState.Sale(
            "21:12",
            saleSubscriptions,
            SubscriptionAdState.COLLAPSED
        )

        SubscriptionScreenEvent.ShowSubscriptions -> SubscriptionScreenState.Sale(
            "21:12",
            saleSubscriptions,
            SubscriptionAdState.COLLAPSED
        )
    }

    override suspend fun processAction(action: SubscriptionScreenAction) = when (action) {
        SubscriptionScreenAction.Collapse -> SubscriptionScreenEvent.Collapse
        SubscriptionScreenAction.Expand -> SubscriptionScreenEvent.Expand
        SubscriptionScreenAction.Minify -> SubscriptionScreenEvent.Minify
        is SubscriptionScreenAction.Subscribe -> {
            val result = subscribeUseCase(action.subscription)
//            if (result is SubscriptionStatus.Purchased || result is SubscriptionStatus.Refunded) {
                SubscriptionScreenEvent.Subscribed
//            } else {
//                SubscriptionScreenEvent.ShowSaleSubscriptions
//            }
        }

        SubscriptionScreenAction.ShowSaleSubscriptions -> SubscriptionScreenEvent.ShowSaleSubscriptions
        SubscriptionScreenAction.ShowSubscriptions -> SubscriptionScreenEvent.ShowSubscriptions
    }

    override suspend fun handleEvent(event: SubscriptionScreenEvent) = null
}

private fun SubscriptionScreenState.updateAdState(state: SubscriptionAdState) =
    when (this) {
        is SubscriptionScreenState.Base -> copy(subscriptionAdState = state)
        is SubscriptionScreenState.Sale -> copy(subscriptionAdState = state)
        else -> this
    }
