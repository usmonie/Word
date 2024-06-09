package com.usmonie.word.features.details.ui.pos

import androidx.collection.ScatterMap
import com.usmonie.compass.core.Extra
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenFactory
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel
import com.usmonie.word.features.games.ui.models.WordUi
import com.usmonie.word.features.subscription.domain.usecase.SubscribeUseCase
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCase

class PosDetailsScreenFactory(
    private val subscribeUseCase: SubscribeUseCase,
    private val subscriptionStatusUseCase: SubscriptionStatusUseCase
) : ScreenFactory {
    override val id: ScreenId = ID
    override fun invoke(params: ScatterMap<String, String>?, extra: Extra?): Screen {
        require(extra is ScreenExtra)

        return PosDetailsScreen(
            PosDetailsViewModel(extra.word),
            SubscriptionViewModel(
                subscribeUseCase,
                subscriptionStatusUseCase
            )
        )
    }

    companion object {
        val ID = ScreenId("PosDetailedScreen")
    }

    data class ScreenExtra(val word: WordUi) : Extra
}
