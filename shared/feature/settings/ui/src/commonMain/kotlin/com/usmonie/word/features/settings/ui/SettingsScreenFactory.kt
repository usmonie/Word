package com.usmonie.word.features.settings.ui

import androidx.collection.ScatterMap
import com.usmonie.compass.core.Extra
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenFactory
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.word.features.settings.domain.usecase.ChangeThemeUseCase
import com.usmonie.word.features.settings.ui.usecases.UserSelectedThemeUseCase
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCase
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel

class SettingsScreenFactory(
	private val userSelectedThemeUseCase: UserSelectedThemeUseCase,
	private val changeThemeUseCase: ChangeThemeUseCase,
	private val subscriptionStatusUseCase: SubscriptionStatusUseCase,
	private val subscriptionViewModel: SubscriptionViewModel
) : ScreenFactory {
	override val id: ScreenId = ID

	override fun invoke(storeInBackStack: Boolean, params: ScatterMap<String, String>?, extra: Extra?): Screen {
		return SettingsScreen(
			SettingsViewModel(
				userSelectedThemeUseCase,
				changeThemeUseCase,
				subscriptionStatusUseCase
			),
			subscriptionViewModel
		)
	}

	companion object {
		val ID = ScreenId("SettingsScreen")
	}
}
