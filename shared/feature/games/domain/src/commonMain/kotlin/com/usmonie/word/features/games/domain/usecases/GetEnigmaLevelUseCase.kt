package com.usmonie.word.features.games.domain.usecases

import com.usmonie.core.domain.usecases.FlowUseCase
import com.usmonie.word.features.games.domain.repositories.EnigmaRepository
import kotlinx.coroutines.flow.Flow

interface GetEnigmaLevelUseCase : FlowUseCase<Unit, Int>

internal class GetEnigmaLevelUseCaseImpl(private val enigmaRepository: EnigmaRepository) : GetEnigmaLevelUseCase {
    override fun invoke(input: Unit): Flow<Int> {
        return enigmaRepository.getLevel()
    }
}
