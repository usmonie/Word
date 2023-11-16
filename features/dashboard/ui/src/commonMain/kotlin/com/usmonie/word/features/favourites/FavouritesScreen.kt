package com.usmonie.word.features.favourites

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.GetAllFavouritesUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCaseImpl
import com.usmonie.word.features.detail.WordScreen
import com.usmonie.word.features.ui.BaseDashboardLazyColumn
import com.usmonie.word.features.ui.EmptyItem
import com.usmonie.word.features.ui.SearchBar
import com.usmonie.word.features.ui.TobBackButtonBar
import com.usmonie.word.features.ui.words
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.RouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder

class FavouritesScreen(
    private val favouritesViewModel: FavouritesViewModel,
) : Screen(favouritesViewModel) {
    override val id: String = ID

    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current
        val state by favouritesViewModel.state.collectAsState()
        val effect by favouritesViewModel.effect.collectAsState(null)

        FavouritesEffect(effect, routeManager)

        Scaffold(
            topBar = { TobBackButtonBar(routeManager::navigateBack, true) },
        ) { insets ->
            BaseDashboardLazyColumn(rememberLazyListState(), insets) {
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
                    is FavouritesState.Empty -> item {
                        EmptyItem(
                            "Favorites are empty",
                            "You can add favorite words from the search screen"
                        )
                    }

                    is FavouritesState.Items -> words(
                        favouritesViewModel::onOpenWord,
                        favouritesViewModel::onUpdateFavourite,
                        favouritesViewModel::onShareWord,
                        favouritesViewModel::onSynonym,
                        s.favourites,
                        Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    )

                    is FavouritesState.Loading -> item {
//                        Row(
//                            modifier = Modifier.fillParentMaxWidth(),
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            CircularProgressIndicator(
//                                color = MaterialTheme.colorScheme.secondary,
//                                trackColor = MaterialTheme.colorScheme.surface
//                            )
//                        }
                    }
                }

                item { Spacer(Modifier.height(16.dp)) }
            }
        }
    }

    @Composable
    private fun FavouritesEffect(
        effect: FavouritesEffect?,
        routeManager: RouteManager
    ) {
        LaunchedEffect(effect) {
            when (effect) {
                is FavouritesEffect.OnBack -> routeManager.navigateBack()
                is FavouritesEffect.OpenWord -> routeManager.navigateTo(
                    WordScreen.ID,
                    extras = WordScreen.Companion.WordExtra(effect.wordUi)
                )

                null -> Unit
            }
        }
    }

    companion object {
        const val ID = "FAVOURITES_SCREEN"
    }

    class Builder(private val wordRepository: WordRepository) : ScreenBuilder {
        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return FavouritesScreen(
                FavouritesViewModel(
                    UpdateFavouriteUseCaseImpl(wordRepository),
                    GetAllFavouritesUseCaseImpl(wordRepository)
                ),
            )
        }
    }
}