package com.usmonie.word.features.favorites.ui

import androidx.collection.ScatterMap
import com.usmonie.compass.core.Extra
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenFactory
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.word.features.dictionary.domain.usecases.GetAllFavouritesUseCase
import com.usmonie.word.features.dictionary.domain.usecases.UpdateFavouriteWordUseCase
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel

class FavoritesScreenFactory(
    private val getFavoritesUseCase: GetAllFavouritesUseCase,
    private val updateFavouriteWordUseCase: UpdateFavouriteWordUseCase,
    private val subscriptionViewModel: SubscriptionViewModel,
    private val openWord: (WordCombinedUi) -> Unit
) : ScreenFactory {
    override val id: ScreenId = ID

    override fun invoke(params: ScatterMap<String, String>?, extra: Extra?): Screen {
        return FavoritesScreen(
            FavoritesViewModel(getFavoritesUseCase, updateFavouriteWordUseCase),
            subscriptionViewModel,
            openWord
        )
    }

    companion object {
        val ID = ScreenId("FavoritesScreen")
    }
}
