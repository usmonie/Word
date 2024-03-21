package com.usmonie.word.features.dashboard.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import wtf.word.core.domain.usecases.FlowUseCase

class GetLearnedStatusUseCase: FlowUseCase<Unit, Unit> {
    override fun invoke(input: Unit): Flow<Unit> {
        return flow {  }
    }
}