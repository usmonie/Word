package com.usmonie.word.features.dictionary.domain.di

import com.usmonie.word.features.dictionary.domain.usecases.CheckIsFavoriteUseCase
import com.usmonie.word.features.dictionary.domain.usecases.CheckIsFavoriteUseCaseImpl
import com.usmonie.word.features.dictionary.domain.usecases.ClearRecentUseCase
import com.usmonie.word.features.dictionary.domain.usecases.ClearRecentUseCaseImpl
import com.usmonie.word.features.dictionary.domain.usecases.GetAllFavouritesUseCase
import com.usmonie.word.features.dictionary.domain.usecases.GetAllFavouritesUseCaseImpl
import com.usmonie.word.features.dictionary.domain.usecases.GetRandomWordUseCase
import com.usmonie.word.features.dictionary.domain.usecases.GetRandomWordUseCaseImpl
import com.usmonie.word.features.dictionary.domain.usecases.GetSearchHistoryUseCase
import com.usmonie.word.features.dictionary.domain.usecases.GetSearchHistoryUseCaseImpl
import com.usmonie.word.features.dictionary.domain.usecases.GetWordOfTheDayUseCase
import com.usmonie.word.features.dictionary.domain.usecases.GetWordOfTheDayUseCaseImpl
import com.usmonie.word.features.dictionary.domain.usecases.SearchWordsUseCase
import com.usmonie.word.features.dictionary.domain.usecases.SearchWordsUseCaseImpl
import com.usmonie.word.features.dictionary.domain.usecases.UpdateFavouriteWordUseCase
import com.usmonie.word.features.dictionary.domain.usecases.UpdateFavouriteWordUseCaseImpl
import com.usmonie.word.features.dictionary.domain.usecases.UpdateSearchHistory
import com.usmonie.word.features.dictionary.domain.usecases.UpdateSearchHistoryImpl
import org.koin.dsl.module

val dictionaryDomainUseCase = module {
    factory<CheckIsFavoriteUseCase> { CheckIsFavoriteUseCaseImpl(get()) }
    factory<ClearRecentUseCase> { ClearRecentUseCaseImpl(get()) }
    factory<GetSearchHistoryUseCase> { GetSearchHistoryUseCaseImpl(get()) }
    factory<GetAllFavouritesUseCase> { GetAllFavouritesUseCaseImpl(get()) }
    factory<GetRandomWordUseCase> { GetRandomWordUseCaseImpl(get()) }
    factory<GetWordOfTheDayUseCase> { GetWordOfTheDayUseCaseImpl(get()) }
    factory<UpdateFavouriteWordUseCase> { UpdateFavouriteWordUseCaseImpl(get()) }
    factory<SearchWordsUseCase> { SearchWordsUseCaseImpl(get()) }
    factory<UpdateSearchHistory> { UpdateSearchHistoryImpl(get()) }
}
