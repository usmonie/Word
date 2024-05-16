package com.usmonie.word.features.details.ui.word

import androidx.compose.runtime.Immutable
import com.usmonie.compass.viewmodel.ContentState
import com.usmonie.compass.viewmodel.ScreenAction
import com.usmonie.compass.viewmodel.ScreenEffect
import com.usmonie.compass.viewmodel.ScreenEvent
import com.usmonie.compass.viewmodel.ScreenState
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import com.usmonie.word.features.dictionary.ui.models.WordUi

@Immutable
internal data class WordDetailsState(
    val word: WordCombinedUi,
    val sensesExpanded: Boolean = false,
    val similarWords: ContentState<List<WordCombinedUi>> = ContentState.Loading(),
) : ScreenState {
    val isFavorite: Boolean
        get() = word.isFavorite
}

internal sealed class WordDetailsAction : ScreenAction {
    data class OnOpenPos(val wordUi: WordUi) : WordDetailsAction()

    data class OnFavoriteWord(val word: WordCombinedUi) : WordDetailsAction()

    data object Update : WordDetailsAction()
}

internal sealed class WordDetailsEvent : ScreenEvent {
    data class UpdateWord(val word: WordCombinedUi) : WordDetailsEvent()
    data class OpenPos(val wordUi: WordUi) : WordDetailsEvent()
}

internal sealed class WordDetailsEffect : ScreenEffect {
    class OpenPos(val wordUi: WordUi) : WordDetailsEffect()
}
