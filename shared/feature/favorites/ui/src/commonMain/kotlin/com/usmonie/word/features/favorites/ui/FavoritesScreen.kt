package com.usmonie.word.features.favorites.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.compass.core.LocalRouteManager
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.compass.viewmodel.StateScreen
import com.usmonie.core.kit.composables.word.HeaderWordScaffold
import com.usmonie.core.kit.tools.add
import com.usmonie.word.features.ads.ui.LocalAdsManager
import com.usmonie.word.features.favorites.ui.quotes.FavoriteQuotesViewModel
import com.usmonie.word.features.favorites.ui.quotes.onFavoriteQuote
import com.usmonie.word.features.favorites.ui.quotes.onSelectCategory
import com.usmonie.word.features.favorites.ui.words.FavoriteWordsContent
import com.usmonie.word.features.favorites.ui.words.FavoriteWordsViewModel
import com.usmonie.word.features.games.ui.models.WordCombinedUi
import com.usmonie.word.features.quotes.kit.di.QuotesFilterContent
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionPage
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionScreenState
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.favorites.ui.generated.resources.Res
import word.shared.feature.favorites.ui.generated.resources.favorite_quotes_title
import word.shared.feature.favorites.ui.generated.resources.favorite_words_title
import word.shared.feature.favorites.ui.generated.resources.favorites_title

internal class FavoritesScreen(
    viewModel: FavoritesViewModel,
    private val subscriptionsViewModel: SubscriptionViewModel,
    private val favoriteWordsViewModel: FavoriteWordsViewModel,
    private val favoriteQuotesViewModel: FavoriteQuotesViewModel,
    private val openWord: (WordCombinedUi) -> Unit
) : StateScreen<FavoritesState, FavoritesAction, FavoritesEvent, FavoritesEffect, FavoritesViewModel>(
    viewModel
) {
    override val id: ScreenId = FavoritesScreenFactory.ID

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current
        val favoritesPlaceholder = stringResource(Res.string.favorites_title)
        val subscriptionsState by subscriptionsViewModel.state.collectAsState()

        val wordsTitle = stringResource(Res.string.favorite_words_title)
        val quotesTitle = stringResource(Res.string.favorite_quotes_title)
        val tabsTitles = remember {
            listOf(wordsTitle, quotesTitle)
        }

        val pagerState = rememberPagerState { tabsTitles.size }
        HeaderWordScaffold(
            placeholder = { favoritesPlaceholder },
            onBackClicked = routeManager::popBackstack,
            header = if (subscriptionsState is SubscriptionScreenState.Empty) {
                null
            } else {
                { SubscriptionPage(subscriptionsViewModel) }
            },
            topBarBottom = {
                val coroutineScope = rememberCoroutineScope()

                PrimaryScrollableTabRow(
                    pagerState.currentPage,
                    edgePadding = 16.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Tab(
                        selected = pagerState.currentPage == 0,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(0)
                            }
                        },
                    ) {
                        Text(
                            tabsTitles[0],
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                        )
                    }
                    Tab(
                        selected = pagerState.currentPage == 1,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(1)
                            }
                        },
                    ) {
                        Text(
                            tabsTitles[1],
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                        )
                    }
                }
            },
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
        ) { insets ->
            HorizontalPager(pagerState, verticalAlignment = Alignment.Top) { page ->
                if (page == 0) {
                    FavoriteWordsContent(openWord, favoriteWordsViewModel, insets)
                } else {
                    val state by favoriteQuotesViewModel.state.collectAsState()
                    QuotesFilterContent(
                        onFavoriteClicked = favoriteQuotesViewModel::onFavoriteQuote,
                        quotes = state.quotes,
                        categories = state.categories,
                        selectedCategory = state.selectedCategory,
                        onCategorySelected = favoriteQuotesViewModel::onSelectCategory,
                        insets = insets.add(horizontal = 16.dp),
                    )
                }
            }
        }
    }
}
