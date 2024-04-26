package com.usmonie.word.features.subscription.domain.usecase

import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscription.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import wtf.word.core.domain.usecases.FlowUseCase

interface SubscriptionStatusUseCase : FlowUseCase<Unit, SubscriptionStatus>

class SubscriptionStatusUseCaseImpl(
    private val repository: SubscriptionRepository
) : SubscriptionStatusUseCase {

    override fun invoke(input: Unit): Flow<SubscriptionStatus> {
        return repository.getSubscriptionState().distinctUntilChanged()
    }
}
