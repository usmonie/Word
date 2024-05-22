package com.usmonie.word.features.subscription.domain.usecase

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.subscription.domain.models.Subscription
import com.usmonie.word.features.subscription.domain.repository.SubscriptionRepository

interface SubscribeUseCase : CoroutineUseCase<Subscription, Unit>

internal class SubscribeUseCaseImpl(
    private val subscriptionRepository: SubscriptionRepository
) : SubscribeUseCase {
    override suspend fun invoke(input: Subscription) {
        subscriptionRepository.subscribe(input)
    }
}
