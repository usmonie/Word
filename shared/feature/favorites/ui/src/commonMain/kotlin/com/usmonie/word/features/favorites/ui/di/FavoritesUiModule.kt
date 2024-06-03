package com.usmonie.word.features.favorites.ui.di

import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import com.usmonie.word.features.favorites.ui.FavoritesScreenFactory
import org.koin.dsl.module

val favoritesUiModule = module {
    factory { (openWord: ((WordCombinedUi) -> Unit)) ->
        FavoritesScreenFactory(
            get(),
            get(),
            get(),
            openWord
        )
    }
}
