package com.usmonie.word.features.dashboard.ui.screen

import androidx.collection.ScatterMap
import com.usmonie.compass.core.Extra
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenFactory
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.word.features.dictionary.domain.usecases.GetRandomWordUseCase
import com.usmonie.word.features.dictionary.domain.usecases.GetSearchHistoryUseCase
import com.usmonie.word.features.dictionary.domain.usecases.GetWordOfTheDayUseCase
import com.usmonie.word.features.dictionary.domain.usecases.SearchWordsUseCase
import com.usmonie.word.features.dictionary.domain.usecases.UpdateFavouriteUseCase
import com.usmonie.word.features.dictionary.domain.usecases.UpdateSearchHistory
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi

class DashboardScreenFactory(
    private val searchWordsUseCase: SearchWordsUseCase,
    private val searchHistoryUseCase: GetSearchHistoryUseCase,
    private val updateSearchHistory: UpdateSearchHistory,
    private val getRandomWordUseCase: GetRandomWordUseCase,
    private val wordOfTheDayUseCase: GetWordOfTheDayUseCase,
    private val updateFavoriteUseCase: UpdateFavouriteUseCase,
    private val openWord: (WordCombinedUi) -> Unit

) : ScreenFactory {
    override val id: ScreenId = ID

    override fun invoke(params: ScatterMap<String, String>?, extra: Extra?): Screen {
        return DashboardScreen(
            DashboardViewModel(
                searchWordsUseCase,
                searchHistoryUseCase,
                getRandomWordUseCase,
                updateSearchHistory,
                wordOfTheDayUseCase,
                updateFavoriteUseCase,
            ),
            openWord,
        )
    }

    companion object {
        val ID = ScreenId("DashboardScreen")
    }
}
