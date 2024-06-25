package com.usmonie.word.features.games.ui

import androidx.collection.ScatterMap
import com.usmonie.compass.core.Extra
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenFactory
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel

class GamesScreenFactory(private val subscriptionViewModel: SubscriptionViewModel) : ScreenFactory {
	override val id: ScreenId = ID

	override fun invoke(storeInBackStack: Boolean, params: ScatterMap<String, String>?, extra: Extra?): Screen {
		return GamesScreen(subscriptionViewModel)
	}

	companion object {
		val ID = ScreenId("GamesScreen")
	}
}