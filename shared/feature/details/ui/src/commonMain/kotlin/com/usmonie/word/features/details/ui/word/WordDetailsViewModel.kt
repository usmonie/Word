package com.usmonie.word.features.details.ui.word

import androidx.compose.runtime.Immutable
import com.usmonie.compass.viewmodel.StateViewModel
import com.usmonie.word.features.dictionary.domain.usecases.CheckIsFavoriteUseCase
import com.usmonie.word.features.dictionary.domain.usecases.UpdateFavouriteUseCase
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import com.usmonie.word.features.dictionary.ui.models.WordUi

@Immutable
internal class WordDetailsViewModel(
    private val favoriteUseCase: UpdateFavouriteUseCase,
    private val checkIsFavoriteUseCase: CheckIsFavoriteUseCase,
    private val wordCombined: WordCombinedUi
) : StateViewModel<WordDetailsState, WordDetailsAction, WordDetailsEvent, WordDetailsEffect>(
    WordDetailsState(wordCombined)
) {

    init {
        handleAction(WordDetailsAction.Update)
    }

    override fun WordDetailsState.reduce(event: WordDetailsEvent) = when (event) {
        is WordDetailsEvent.UpdateWord -> copy(word = event.word)
        else -> this
    }

    override suspend fun processAction(action: WordDetailsAction): WordDetailsEvent {
        return when (action) {
            is WordDetailsAction.OnOpenPos -> WordDetailsEvent.OpenPos(action.wordUi)

            is WordDetailsAction.OnFavoriteWord -> {
                favoriteUseCase(
                    UpdateFavouriteUseCase.Param(
                        action.word.word,
                        action.word.isFavorite
                    )
                )

                val isFavorite = checkIsFavoriteUseCase(action.word.word)
                return WordDetailsEvent.UpdateWord(wordCombined.copy(isFavorite = isFavorite))
            }

            WordDetailsAction.Update -> {
                val isFavorite = checkIsFavoriteUseCase(wordCombined.word)

                WordDetailsEvent.UpdateWord(wordCombined.copy(isFavorite = isFavorite))
            }
        }
    }

    override suspend fun handleEvent(event: WordDetailsEvent) = when (event) {
        is WordDetailsEvent.OpenPos -> WordDetailsEffect.OpenPos(event.wordUi)
        else -> null
    }
}

internal fun WordDetailsViewModel.onFavorite(wordCombined: WordCombinedUi) =
    handleAction(WordDetailsAction.OnFavoriteWord(wordCombined))

internal fun WordDetailsViewModel.openPos(word: WordUi) = handleAction(WordDetailsAction.OnOpenPos(word))
