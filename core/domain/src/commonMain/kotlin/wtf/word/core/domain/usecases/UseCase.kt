package wtf.word.core.domain.usecases

import kotlinx.coroutines.flow.Flow

fun interface UseCase<in Input, out Output> {
    operator fun invoke(input: Input): Output
}

interface CoroutineUseCase<in Input, out Output> {
    suspend operator fun invoke(input: Input): Output
}

interface FlowUseCase<in Input, out Output> {
    operator fun invoke(input: Input): Flow<Output>
}

interface BiFlowUseCase<in Input, out Output> {
    suspend operator fun invoke(input: Flow<Input>): Flow<Output>
}
