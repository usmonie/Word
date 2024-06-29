package com.usmonie.word.features.games.domain.di

import com.usmonie.word.features.games.domain.usecases.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gamesDomainModule = module {
	factoryOf(::UpdateUserProgressUseCaseImpl) {
		bind<UpdateUserProgressUseCase>()
	}

	factoryOf(::GetEnigmaLevelUseCaseImpl) {
		bind<GetEnigmaLevelUseCase>()
	}

	singleOf(::GetCryptogramQuoteUseCaseImpl) {
		bind<GetCryptogramQuoteUseCase>()
	}

	singleOf(::DifficultyAdjuster)

	singleOf(::GetEnigmaLevelUseCaseImpl) {
		bind<GetEnigmaLevelUseCase>()
	}
}