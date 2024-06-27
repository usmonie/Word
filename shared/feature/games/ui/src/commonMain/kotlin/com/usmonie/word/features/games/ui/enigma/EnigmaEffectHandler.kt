package com.usmonie.word.features.games.ui.enigma

import com.usmonie.compass.viewmodel.EffectHandler

class EnigmaEffectHandler : EffectHandler<EnigmaEvent, EnigmaEffect> {
	override fun handle(event: EnigmaEvent) = when (event) {
		is EnigmaEvent.Incorrect -> EnigmaEffect.InputEffect.Incorrect()
		is EnigmaEvent.Lost -> EnigmaEffect.ShowMiddleGameAd()
		is EnigmaEvent.NextPhrase -> EnigmaEffect.ShowMiddleGameAd()
		is EnigmaEvent.ReviveClicked -> EnigmaEffect.ShowRewardedLifeAd()
		is EnigmaEvent.NoHints -> EnigmaEffect.ShowRewardedHintAd()
		is EnigmaEvent.Correct -> EnigmaEffect.InputEffect.Correct()
		is EnigmaEvent.UpdateCurrentPhrase -> EnigmaEffect.InputEffect.Correct()
		is EnigmaEvent.ShowMiddleGameAd -> EnigmaEffect.ShowMiddleGameAd()
		is EnigmaEvent.UpdateSelectedCell -> EnigmaEffect.CellSelected()
		else -> null
	}
}
