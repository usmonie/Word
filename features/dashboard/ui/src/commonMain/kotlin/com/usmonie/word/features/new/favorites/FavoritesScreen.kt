package com.usmonie.word.features.new.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.GetAllFavouritesUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCaseImpl
import com.usmonie.word.features.new.details.WordDetailsScreen
import com.usmonie.word.features.ui.AdMob
import com.usmonie.word.features.ui.BaseLazyColumn
import com.usmonie.word.features.ui.EmptyItem
import com.usmonie.word.features.ui.SearchBar
import com.usmonie.word.features.ui.TopBackButtonBar
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.RouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.speech.core.ui.AdKeys
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

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return FavoritesScreen(
                FavouritesViewModel(
                    UpdateFavouriteUseCaseImpl(wordRepository),
                    GetAllFavouritesUseCaseImpl(wordRepository),
                    analytics
                ),
                adMob
            )
        }
    }
}

@Composable
private fun FavoritesContent(favoritesViewModel: FavouritesViewModel, adMob: AdMob) {
    val routeManager = LocalRouteManager.current
    val state by favoritesViewModel.state.collectAsState()
    val effect by favoritesViewModel.effect.collectAsState(null)

    FavouritesEffect(effect, routeManager)

    Scaffold(
        topBar = { TopBackButtonBar(routeManager::navigateBack, true) },
    ) { insets ->
        Box(
            Modifier.padding(
                start = insets.calculateLeftPadding(LayoutDirection.Ltr),
                end = insets.calculateRightPadding(LayoutDirection.Ltr),
                top = insets.calculateTopPadding()
            )
        ) {
            BaseLazyColumn(
                rememberLazyListState(),
                contentPadding = PaddingValues(bottom = insets.calculateBottomPadding()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    SearchBar(
                        {},
                        {},
                        "[F]avorites",
                        "",
                        hasFocus = false,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth().testTag("FAVOURITES_SEARCH_BAR"),
                    )
                }

                when (val s = state) {
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

                item { Spacer(Modifier.height(80.dp)) }
            }
            adMob.Banner(
                AdKeys.BANNER_ID,
                Modifier.fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(insets)
            )
        }
    }
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
