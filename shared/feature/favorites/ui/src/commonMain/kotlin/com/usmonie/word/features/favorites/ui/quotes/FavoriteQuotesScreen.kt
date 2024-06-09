package com.usmonie.word.features.favorites.ui.quotes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.usmonie.compass.core.LocalRouteManager
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.compass.viewmodel.StateScreen
import com.usmonie.core.kit.composables.word.HeaderWordScaffold
import com.usmonie.word.features.ads.ui.LocalAdsManager
import com.usmonie.word.features.quotes.kit.di.QuotesFilterContent
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.favorites.ui.generated.resources.Res
import word.shared.feature.favorites.ui.generated.resources.favorites_title

internal class FavoriteQuotesScreen(viewModel: FavoriteQuotesViewModel) :
    StateScreen<
            FavoriteQuotesScreenState,
            FavoriteQuotesScreenAction,
            FavoriteQuotesScreenEvent,
            FavoriteQuotesScreenEffect,
            FavoriteQuotesViewModel
            >(viewModel) {

    override val id: ScreenId = ScreenId("")

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current
        val favoritesPlaceholder = stringResource(Res.string.favorites_title)
//        val subscriptionsState by subscriptionsViewModel.state.collectAsState()

        HeaderWordScaffold(
            placeholder = { favoritesPlaceholder },
            onBackClicked = routeManager::popBackstack,
//            header = if (subscriptionsState is SubscriptionScreenState.Empty) {
//                null
//            } else {
//                { SubscriptionPage(subscriptionsViewModel) }
//            },
            bottomAdBanner = {
                Box(
                    Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    val adMob = LocalAdsManager.current

                    adMob.Banner(
                        Modifier.fillMaxWidth()
                            .navigationBarsPadding()
                    )
                }
            }
        ) {
            val state by viewModel.state.collectAsState()
            QuotesFilterContent(
                onFavoriteClicked = viewModel::onFavoriteQuote,
                quotes = state.quotes,
                categories = state.categories,
                selectedCategory = state.selectedCategory,
                onCategorySelected = viewModel::onSelectCategory,
                insets = it,
            )
        }
    }
}
