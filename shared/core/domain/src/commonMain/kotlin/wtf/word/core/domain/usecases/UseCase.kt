package wtf.word.core.domain.usecases

import kotlinx.coroutines.flow.Flow

fun interface UseCase<in Input, out Output> {
    operator fun invoke(input: Input): Output
}

interface CoroutineUseCase<in Input, out Output> {
    suspend operator fun invoke(input: Input): Output
}

fun interface FlowUseCase<in Input, out Output> {
    operator fun invoke(input: Input): Flow<Output>
}

fun interface BiFlowUseCase<in Input, out Output> {
    suspend operator fun invoke(input: Flow<Input>): Flow<Output>
}

operator fun <Output> UseCase<Unit, Output>.invoke() = invoke(Unit)
suspend operator fun <Output> CoroutineUseCase<Unit, Output>.invoke() = invoke(Unit)
operator fun <Output> FlowUseCase<Unit, Output>.invoke() = invoke(Unit)
