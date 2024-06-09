package com.usmonie.word.features.qutoes.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.qutoes.domain.models.QuoteCategories
import com.usmonie.word.features.qutoes.domain.repositories.QuotesRepository

interface GetFavoriteQuoteCategoriesUseCase : CoroutineUseCase<Unit, List<QuoteCategories>>

internal class GetFavoriteQuoteCategoriesUseCaseImpl(
    private val quotesRepository: QuotesRepository
) : GetFavoriteQuoteCategoriesUseCase {
    override suspend fun invoke(input: Unit): List<QuoteCategories> {
        return quotesRepository.getFavoritesByCategories()
    }
}
