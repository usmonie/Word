package com.usmonie.word.features.subscriptions.ui

import com.usmonie.word.features.subscription.domain.di.subscriptionDomainModule
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val subscriptionsUiModule = module {
    includes(subscriptionDomainModule)
    factoryOf(::SubscriptionViewModel)
}
