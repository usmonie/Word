package com.usmonie.word.features.quotes.ui.di

import com.usmonie.word.features.dictionary.domain.di.dictionaryDomainUseCase
import com.usmonie.word.features.settings.domain.di.settingsDomainModule
import org.koin.dsl.module

val qutoesUiModule = module {
    includes(settingsDomainModule)
    includes(dictionaryDomainUseCase)

}