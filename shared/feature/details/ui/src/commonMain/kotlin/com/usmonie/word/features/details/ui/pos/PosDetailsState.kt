package com.usmonie.word.features.details.ui.pos

import androidx.compose.runtime.Immutable
import com.usmonie.compass.viewmodel.ScreenAction
import com.usmonie.compass.viewmodel.ScreenEffect
import com.usmonie.compass.viewmodel.ScreenEvent
import com.usmonie.compass.viewmodel.ScreenState
import com.usmonie.word.features.dictionary.ui.models.WordUi

@Immutable
data class PosDetailsState(val word: WordUi) : ScreenState
object PosDetailAction : ScreenAction
object PosDetailEvent : ScreenEvent
object PosDetailEffect : ScreenEffect