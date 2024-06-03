package com.usmonie.word.features.games.ui.di

import com.usmonie.word.features.dictionary.domain.di.dictionaryDomainUseCase
import com.usmonie.word.features.dictionary.domain.di.gamesDomainUseCase
import com.usmonie.word.features.games.ui.GamesScreenFactory
import com.usmonie.word.features.games.ui.enigma.EnigmaGameScreenFactory
import com.usmonie.word.features.games.ui.enigma.EnigmaGameViewModel
import com.usmonie.word.features.games.ui.hangman.HangmanGameScreenFactory
import com.usmonie.word.features.games.ui.hangman.HangmanGameViewModel
import com.usmonie.word.features.settings.domain.di.settingsDomainModule
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
}
