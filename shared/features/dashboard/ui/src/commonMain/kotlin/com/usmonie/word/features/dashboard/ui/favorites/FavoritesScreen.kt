package com.usmonie.word.features.dashboard.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.GetAllFavouritesUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCaseImpl
import com.usmonie.word.features.dashboard.ui.details.WordDetailsScreen
import com.usmonie.word.features.dashboard.ui.ui.AdMob
import com.usmonie.word.features.dashboard.ui.ui.BaseLazyColumn
import com.usmonie.word.features.dashboard.ui.ui.EmptyItem
import com.usmonie.word.features.dashboard.ui.ui.WordTopBar
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.RouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.word.core.domain.Analytics

class FavoritesScreen(
    private val favoritesViewModel: FavouritesViewModel,
    private val adMob: AdMob,
) : Screen(favoritesViewModel) {
    override val id: String = ID

    @Composable
    override fun Content() {
        FavoritesContent(favoritesViewModel, adMob)
    }

    companion object {
        const val ID = "FAVOURITES_SCREEN"
    }

    class Builder(
        private val wordRepository: WordRepository,
        private val adMob: AdMob,
        private val analytics: Analytics
    ) : ScreenBuilder {
        override val id: String = ID

        @OptIn(ExperimentalMaterial3Api::class)
        override fun build(params: Map<String, String>?, extra: Extra?): Screen = FavoritesScreen(
            FavouritesViewModel(
                UpdateFavouriteUseCaseImpl(wordRepository),
                GetAllFavouritesUseCaseImpl(wordRepository),
                analytics
            ),
            adMob
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoritesContent(
    favoritesViewModel: FavouritesViewModel,
    adMob: AdMob,
) {
    val routeManager = LocalRouteManager.current
    FavoritesEffect(favoritesViewModel, routeManager)

    val state by favoritesViewModel.state.collectAsState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            WordTopBar(
                routeManager::navigateBack,
                "[F]avorites",
                { true },
                { scrollBehavior },
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { insets ->
        FavoritesContent({ insets }, { state }, favoritesViewModel, adMob)
    }
}

@Composable
private fun FavoritesContent(
    getInsets: () -> PaddingValues,
    getState: () -> FavoritesState,
    favoritesViewModel: FavouritesViewModel,
    adMob: AdMob
) {
    val insets = getInsets()
    Box(Modifier.fillMaxSize()) {
        BaseLazyColumn(
            contentPadding = PaddingValues(
                start = insets.calculateLeftPadding(LayoutDirection.Ltr),
                end = insets.calculateRightPadding(LayoutDirection.Ltr),
                top = insets.calculateTopPadding() + 16.dp,
                bottom = insets.calculateBottomPadding() + 80.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            when (val s = getState()) {
                is FavoritesState.Empty -> item {
                    EmptyItem(
                        "Favorites are empty",
                        "You can add favorite words from the search screen"
                    )
                }

                is FavoritesState.Items -> wordsCardsList(
                    favoritesViewModel::onOpenWord,
                    favoritesViewModel::onUpdateFavourite,
                    favoritesViewModel::onShareWord,
                    //                            favouritesViewModel::onSynonym,
                    s.favourites,
                    Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )

                is FavoritesState.Loading -> Unit
            }
        }
        adMob.Banner(
            Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(insets)
        )
    }
}

@Composable
private fun FavoritesEffect(
    favoritesViewModel: FavouritesViewModel,
    routeManager: RouteManager
) {
    val effect by favoritesViewModel.effect.collectAsState(null)

    FavouritesEffect(effect, routeManager)
}

@Composable
private fun FavouritesEffect(
    effect: FavoritesEffect?,
    routeManager: RouteManager
) {
    LaunchedEffect(effect) {
        when (effect) {
            is FavoritesEffect.OnBack -> routeManager.navigateBack()
            is FavoritesEffect.OpenWord -> routeManager.navigateTo(
                WordDetailsScreen.ID,
                extras = WordDetailsScreen.Companion.WordExtra(effect.wordUi)
            )

            null -> Unit
        }
    }
}
