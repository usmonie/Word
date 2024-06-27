package com.usmonie.word.features.games.ui.enigma

import androidx.compose.runtime.Immutable
import com.usmonie.compass.viewmodel.StateViewModel
import com.usmonie.core.domain.usecases.invoke
import com.usmonie.word.features.settings.domain.usecase.GetUserHintsCountUseCase
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlin.time.Duration.Companion.minutes

@Immutable
class EnigmaGameViewModel(
	private val actionProcessor: EnigmaActionProcessor,
	private val stateManager: EnigmaStateManager,
	private val effectHandler: EnigmaEffectHandler,
	private val getUserHintsCountUseCase: GetUserHintsCountUseCase,
) : StateViewModel<EnigmaState, EnigmaAction, EnigmaEvent, EnigmaEffect>(EnigmaState.Loading()) {

	private val timer = flow {
		while (currentCoroutineContext().isActive) {
			emit(Unit)
			delay(4.minutes)
		}
	}

	init {
		handleAction(EnigmaAction.NextPhrase)
		viewModelScope.launchSafe {
			timer.collect { handleAction(EnigmaAction.AdTime) }
		}
		viewModelScope.launchSafe {
			getUserHintsCountUseCase().collect {
				handleAction(EnigmaAction.UpdateUserHints(it))
			}
		}
	}

	override fun EnigmaState.reduce(event: EnigmaEvent) = stateManager.reduce(this, event)

	override suspend fun processAction(action: EnigmaAction) = actionProcessor.process(action, state.value)

	override suspend fun handleEvent(event: EnigmaEvent): EnigmaEffect? {
		val effect = effectHandler.handle(event)

		if (event is EnigmaEvent.Correct) {
			if (effect is EnigmaEffect.InputEffect.Correct && event.phrase.encryptedPositions.isEmpty()) {
				handleAction(EnigmaAction.Won)
			}
		}

		if (event is EnigmaEvent.UpdateCurrentPhrase) {
			if (effect is EnigmaEffect.InputEffect.Correct && event.phrase.encryptedPositions.isEmpty()) {
				handleAction(EnigmaAction.Won)
			}
		}

		return effectHandler.handle(event)
	}

	internal fun onLetterInput(letter: Char) = handleAction(EnigmaAction.InputLetter(letter))
	internal fun onNextPhrase() = handleAction(EnigmaAction.NextPhrase)
	internal fun onAddLifeClick() = handleAction(EnigmaAction.ReviveClicked)
	internal fun onReviveGranted(amount: Int) = handleAction(EnigmaAction.ReviveGranted)
	internal fun onHintGranted(amount: Int) = handleAction(EnigmaAction.AddHints(amount))
	internal fun useHint() = handleAction(EnigmaAction.UseHint)
	internal fun onCellSelected(
		letterIndex: Int,
		cellPositionInWord: Int,
		wordPosition: Int
	) = handleAction(EnigmaAction.CellSelected(letterIndex, cellPositionInWord, wordPosition))
}
