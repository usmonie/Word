package com.usmonie.word.features.games.ui.di

import com.usmonie.compass.viewmodel.ActionProcessor
import com.usmonie.compass.viewmodel.EffectHandler
import com.usmonie.compass.viewmodel.StateManager
import com.usmonie.word.features.games.domain.di.dictionaryDomainUseCase
import com.usmonie.word.features.games.domain.gamesDomainUseCase
import com.usmonie.word.features.games.ui.GamesScreenFactory
import com.usmonie.word.features.games.ui.enigma.*
import com.usmonie.word.features.games.ui.hangman.HangmanGameScreenFactory
import com.usmonie.word.features.games.ui.hangman.HangmanGameViewModel
import com.usmonie.word.features.settings.domain.di.settingsDomainModule
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gamesUiModule = module {
	includes(settingsDomainModule, dictionaryDomainUseCase, gamesDomainUseCase)

	singleOf(::GamesScreenFactory)
	single {
		HangmanGameScreenFactory(get(), { get() }, get())
	}
	factoryOf(::HangmanGameViewModel)

	single { EnigmaGameScreenFactory({ get() }, get()) }
	factoryOf(::EnigmaGameViewModel)

	factoryOf(::EnigmaStateManager) {
		bind<StateManager<EnigmaEvent, EnigmaState>>()
	}
	factoryOf(::EnigmaEffectHandler) {
		bind<EffectHandler<EnigmaEvent, EnigmaEffect>>()
	}
	factoryOf(::EnigmaActionProcessor) {
		bind<ActionProcessor<EnigmaAction, EnigmaState, EnigmaEvent>>()
	}
}
