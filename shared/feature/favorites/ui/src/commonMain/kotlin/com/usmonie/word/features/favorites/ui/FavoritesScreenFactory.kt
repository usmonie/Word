package com.usmonie.word.features.favorites.ui

import androidx.collection.ScatterMap
import com.usmonie.compass.core.Extra
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenFactory
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.word.features.games.domain.usecases.GetAllFavouritesUseCase
import com.usmonie.word.features.games.domain.usecases.UpdateFavouriteWordUseCase
import com.usmonie.word.features.games.ui.models.WordCombinedUi
import com.usmonie.word.features.favorites.ui.quotes.FavoriteQuotesViewModel
import com.usmonie.word.features.favorites.ui.words.FavoriteWordsViewModel
import com.usmonie.word.features.qutoes.domain.usecases.GetFavoriteQuotesUseCase
import com.usmonie.word.features.qutoes.domain.usecases.UpdateFavoriteQuoteUseCase
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel

class FavoritesScreenFactory(
    private val getFavoritesUseCase: GetAllFavouritesUseCase,
    private val getFavoriteQuotesScreen: GetFavoriteQuotesUseCase,
    private val updateFavoriteQuotesScreen: UpdateFavoriteQuoteUseCase,
    private val updateFavouriteWordUseCase: UpdateFavouriteWordUseCase,
    private val subscriptionViewModel: SubscriptionViewModel,
    private val openWord: (WordCombinedUi) -> Unit
) : ScreenFactory {
    override val id: ScreenId = ID

    override fun invoke(params: ScatterMap<String, String>?, extra: Extra?): Screen {
        return FavoritesScreen(
            FavoritesViewModel(),
            subscriptionViewModel,
            FavoriteWordsViewModel(getFavoritesUseCase, updateFavouriteWordUseCase),
            FavoriteQuotesViewModel(getFavoriteQuotesScreen, updateFavoriteQuotesScreen),
            openWord
        )
    }

    companion object {
        val ID = ScreenId("FavoritesScreen")
    }
}
