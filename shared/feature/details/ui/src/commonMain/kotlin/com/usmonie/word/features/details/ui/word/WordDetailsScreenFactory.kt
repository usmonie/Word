package com.usmonie.word.features.details.ui.word

import androidx.collection.ScatterMap
import com.usmonie.compass.core.Extra
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenFactory
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.word.features.dictionary.domain.usecases.CheckIsFavoriteUseCase
import com.usmonie.word.features.dictionary.domain.usecases.UpdateFavouriteWordUseCase
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel

class WordDetailsScreenFactory(
    private val favoriteUseCase: UpdateFavouriteWordUseCase,
    private val checkIsFavoriteUseCase: CheckIsFavoriteUseCase,
    private val subscriptionViewModel: SubscriptionViewModel
) : ScreenFactory {
    override val id: ScreenId = ID

    override fun invoke(params: ScatterMap<String, String>?, extra: Extra?): Screen {
        require(extra is WordDetailsExtra)
        return WordDetailsScreen(
            WordDetailsViewModel(
                favoriteUseCase,
                checkIsFavoriteUseCase,
                extra.wordCombined
            ),
            subscriptionViewModel
        )
    }

    companion object {
        val ID = ScreenId("WordDetailsScreen")
    }

    data class WordDetailsExtra(val wordCombined: WordCombinedUi) : Extra
}
