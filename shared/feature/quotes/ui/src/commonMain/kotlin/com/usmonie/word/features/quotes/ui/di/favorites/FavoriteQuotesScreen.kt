package com.usmonie.word.features.quotes.ui.di.favorites

import androidx.compose.runtime.Composable
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.compass.viewmodel.StateScreen

internal class FavoriteQuotesScreen(viewModel: FavoriteQuotesViewModel) :
    StateScreen<FavoriteQuotesScreenState, FavoriteQuotesScreenAction, FavoriteQuotesScreenEvent, FavoriteQuotesScreenEffect, FavoriteQuotesViewModel>(
        viewModel
    ) {

    override val id: ScreenId = ScreenId("")

    @Composable
    override fun Content() {
        TODO("Not yet implemented")
    }
}
