package com.usmonie.word.features.subscription.domain.usecase

import com.usmonie.core.domain.usecases.FlowUseCase
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscription.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

interface SubscriptionStatusUseCase : FlowUseCase<Unit, SubscriptionStatus>

class SubscriptionStatusUseCaseImpl(
    private val repository: SubscriptionRepository
) : SubscriptionStatusUseCase {

    override fun invoke(input: Unit): Flow<SubscriptionStatus> {
        return repository.getSubscriptionState().distinctUntilChanged()
    }
}
