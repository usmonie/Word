package com.usmonie.word.features.details.ui.di

import com.usmonie.word.features.details.ui.pos.PosDetailsScreenFactory
import com.usmonie.word.features.details.ui.word.WordDetailsScreenFactory
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val wordDetailsUiModule = module {
    factoryOf(::WordDetailsScreenFactory)
    factoryOf(::PosDetailsScreenFactory)
}
