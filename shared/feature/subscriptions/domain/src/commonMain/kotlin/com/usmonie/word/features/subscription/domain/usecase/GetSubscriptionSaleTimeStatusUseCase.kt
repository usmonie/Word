package com.usmonie.word.features.subscription.domain.usecase

import com.usmonie.core.domain.usecases.FlowUseCase
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscription.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

interface GetSubscriptionSaleTimeStatusUseCase : FlowUseCase<Unit, SubscriptionStatus>

internal class GetSubscriptionSaleTimeStatusUseCaseImpl(
    private val repository: SubscriptionRepository
) : GetSubscriptionSaleTimeStatusUseCase {

    override suspend fun invoke(input: Unit): Flow<SubscriptionStatus> {
        return repository.getSubscriptionState().distinctUntilChanged()
    }
}
