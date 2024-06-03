package com.usmonie.word.features.games.ui.enigma

import androidx.collection.ScatterMap
import com.usmonie.compass.core.Extra
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenFactory
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel

class EnigmaGameScreenFactory(
    private val getViewModel: () -> EnigmaGameViewModel,
    private val subscriptionViewModel: SubscriptionViewModel
) : ScreenFactory {
    override val id = ID
    override fun invoke(params: ScatterMap<String, String>?, extra: Extra?): Screen {
        return EnigmaGameScreen(
            getViewModel(),
            subscriptionViewModel
        )
    }

    companion object {
        val ID = ScreenId("ENIGMA_SCREEN")
    }
}
