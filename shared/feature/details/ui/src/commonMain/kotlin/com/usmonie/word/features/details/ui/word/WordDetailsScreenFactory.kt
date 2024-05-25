package com.usmonie.word.features.details.ui.word

import androidx.collection.ScatterMap
import com.usmonie.compass.core.Extra
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenFactory
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel
import com.usmonie.word.features.dictionary.domain.usecases.CheckIsFavoriteUseCase
import com.usmonie.word.features.dictionary.domain.usecases.UpdateFavouriteUseCase
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import com.usmonie.word.features.subscription.domain.usecase.SubscribeUseCase
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCase

class WordDetailsScreenFactory(
    private val favoriteUseCase: UpdateFavouriteUseCase,
    private val checkIsFavoriteUseCase: CheckIsFavoriteUseCase,
    private val subscribeUseCase: SubscribeUseCase,
    private val subscriptionStatusUseCase: SubscriptionStatusUseCase,
) :
    ScreenFactory {
    override val id: ScreenId = ID

    override fun invoke(params: ScatterMap<String, String>?, extra: Extra?): Screen {
        require(extra is WordDetailsExtra)
        return WordDetailsScreen(
            WordDetailsViewModel(
                favoriteUseCase,
                checkIsFavoriteUseCase,
                extra.wordCombined
            ),
            SubscriptionViewModel(subscribeUseCase, subscriptionStatusUseCase)
        )
    }

    companion object {
        val ID = ScreenId("WordDetailsScreen")
    }

    data class WordDetailsExtra(val wordCombined: WordCombinedUi) : Extra
}
