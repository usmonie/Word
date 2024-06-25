package com.usmonie.word.features.games.ui.hangman

import androidx.collection.ScatterMap
import com.usmonie.compass.core.Extra
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenFactory
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.word.features.games.ui.models.WordCombinedUi
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel

class HangmanGameScreenFactory(
	private val onOpenWord: (WordCombinedUi) -> Unit,
	private val hangmanGameViewModel: () -> HangmanGameViewModel,
	private val subscriptionViewModel: SubscriptionViewModel
) : ScreenFactory {
	override val id: ScreenId = ID

	override fun invoke(storeInBackStack: Boolean, params: ScatterMap<String, String>?, extra: Extra?): Screen {
		return HangmanGameScreen(hangmanGameViewModel(), onOpenWord, subscriptionViewModel)
	}

	companion object {
		val ID = ScreenId("HangmanGamesScreen")
	}
}
