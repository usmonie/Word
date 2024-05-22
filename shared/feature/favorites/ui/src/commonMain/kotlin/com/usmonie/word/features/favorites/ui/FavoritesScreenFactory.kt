package com.usmonie.word.features.favorites.ui

import androidx.collection.ScatterMap
import com.usmonie.compass.core.Extra
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenFactory
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.word.features.details.ui.notification.SubscriptionViewModel
import com.usmonie.word.features.dictionary.domain.usecases.GetAllFavouritesUseCase
import com.usmonie.word.features.dictionary.domain.usecases.UpdateFavouriteUseCase
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import com.usmonie.word.features.subscription.domain.usecase.SubscribeUseCase
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCase

class FavoritesScreenFactory(
    private val getFavoritesUseCase: GetAllFavouritesUseCase,
    private val updateFavouriteUseCase: UpdateFavouriteUseCase,
    private val subscribeUseCase: SubscribeUseCase,
    private val subscriptionStatusUseCase: SubscriptionStatusUseCase,
    private val openWord: (WordCombinedUi) -> Unit
) : ScreenFactory {
    override val id: ScreenId = ID

    override fun invoke(params: ScatterMap<String, String>?, extra: Extra?): Screen {
        return FavoritesScreen(
            FavoritesViewModel(getFavoritesUseCase, updateFavouriteUseCase),
            SubscriptionViewModel(subscribeUseCase, subscriptionStatusUseCase),
            openWord
        )
    }

    companion object {
        val ID = ScreenId("FavoritesScreen")
    }
}
