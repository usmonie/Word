package com.usmonie.word.features.subscription.domain.di

import com.usmonie.word.features.subscription.domain.usecase.SubscribeUseCase
import com.usmonie.word.features.subscription.domain.usecase.SubscribeUseCaseImpl
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCase
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCaseImpl
import org.koin.dsl.module

val subscriptionDomainModule = module {
    factory<SubscriptionStatusUseCase> { SubscriptionStatusUseCaseImpl(get()) }
    factory<SubscribeUseCase> { SubscribeUseCaseImpl(get()) }
}
