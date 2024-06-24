package com.usmonie.word.features.settings.ui.usecases

import com.usmonie.core.domain.usecases.FlowUseCase
import com.usmonie.core.domain.usecases.invoke
import com.usmonie.core.kit.design.themes.WordThemes
import com.usmonie.core.kit.design.themes.typographies.ModernChic
import com.usmonie.core.kit.design.themes.typographies.WordTypography
import com.usmonie.word.features.settings.domain.usecase.CurrentThemeUseCase
import com.usmonie.word.features.settings.ui.models.UserSelectedTheme
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

interface UserSelectedThemeUseCase : FlowUseCase<Unit, UserSelectedTheme>

internal class UserSelectedThemeUseCaseImpl(
	private val currentThemeUseCase: CurrentThemeUseCase,
	private val subscriptionStatusUseCase: SubscriptionStatusUseCase
) : UserSelectedThemeUseCase {

	override fun invoke(input: Unit): Flow<UserSelectedTheme> {
		val subscriptionFlow = subscriptionStatusUseCase()
		val currentThemeFlow = currentThemeUseCase()
		return currentThemeFlow
			.combine(subscriptionFlow) { selectedTheme, subscriptionStatus ->
				val theme = WordThemes.valueOf(selectedTheme.colorsName ?: "DEEP_INDIGO")
				val typography = WordTypography.valueOf(selectedTheme.fonts ?: "Modern Chic")
				UserSelectedTheme(
					if (theme.paid && subscriptionStatus !is SubscriptionStatus.Purchased) {
						WordThemes.DEEP_INDIGO
					} else {
						theme
					},
					if (typography !is ModernChic &&
						subscriptionStatus !is SubscriptionStatus.Purchased
					) {
						ModernChic
					} else {
						typography
					},
					selectedTheme.darkThemeMode
				)
			}
	}
}
