package com.usmonie.word.features.details.ui.pos

import com.usmonie.compass.viewmodel.StateViewModel
import com.usmonie.word.features.games.ui.models.WordUi

class PosDetailsViewModel(private val word: WordUi) :
    StateViewModel<PosDetailsState, PosDetailAction, PosDetailEvent, PosDetailEffect>(
        PosDetailsState(word)
    ) {
    override fun PosDetailsState.reduce(event: PosDetailEvent): PosDetailsState {
        return this
    }

    override suspend fun processAction(action: PosDetailAction): PosDetailEvent {
        return PosDetailEvent
    }

    override suspend fun handleEvent(event: PosDetailEvent): PosDetailEffect? {
        return null
    }
}
