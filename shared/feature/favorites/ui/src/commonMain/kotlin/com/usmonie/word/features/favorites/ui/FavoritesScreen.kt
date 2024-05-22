package com.usmonie.word.features.favorites.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.compass.core.LocalRouteManager
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.compass.viewmodel.ContentState
import com.usmonie.compass.viewmodel.StateScreen
import com.usmonie.core.kit.composables.word.HeaderWordScaffold
import com.usmonie.core.kit.tools.add
import com.usmonie.word.features.details.ui.notification.SubscriptionPage
import com.usmonie.word.features.details.ui.notification.SubscriptionScreenState
import com.usmonie.word.features.details.ui.notification.SubscriptionViewModel
import com.usmonie.word.features.dictionary.ui.WordCardLarge
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.favorites.ui.generated.resources.Res
import word.shared.feature.favorites.ui.generated.resources.favorites_empty_description
import word.shared.feature.favorites.ui.generated.resources.favorites_empty_title
import word.shared.feature.favorites.ui.generated.resources.favorites_title

internal class FavoritesScreen(
    viewModel: FavoritesViewModel,
    private val subscriptionsViewModel: SubscriptionViewModel,
    private val openWord: (WordCombinedUi) -> Unit
) : StateScreen<FavoritesState, FavoritesAction, FavoritesEvent, FavoritesEffect, FavoritesViewModel>(
    viewModel
) {
    override val id: ScreenId = FavoritesScreenFactory.ID

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current
        val favoritesPlaceholder = stringResource(Res.string.favorites_title)
        val state by viewModel.state.collectAsState()
        val subscriptionsState by subscriptionsViewModel.state.collectAsState()

        FavoritesEffects()
        HeaderWordScaffold(
            placeholder = { favoritesPlaceholder },
            onBackClicked = routeManager::popBackstack,
            header = if (subscriptionsState is SubscriptionScreenState.Empty) {
                null
            } else {
                { SubscriptionPage(subscriptionsViewModel) }
            },
        ) {
            when (val w = state.words) {
                is ContentState.Error<*, *> -> Box(Modifier.padding(it)) {
                    EmptyItem(
                        stringResource(Res.string.favorites_empty_title),
                        stringResource(Res.string.favorites_empty_description)
                    )
                }

                is ContentState.Loading -> LinearProgressIndicator(
                    Modifier.padding(it).fillMaxWidth().padding(horizontal = 32.dp)
                )

                is ContentState.Success -> if (w.data.isEmpty()) {
                    Box(Modifier.padding(it)) {
                        EmptyItem(
                            stringResource(Res.string.favorites_empty_title),
                            stringResource(Res.string.favorites_empty_description)
                        )
                    }
                } else {
                    WordsList(w, it)
                }
            }
        }
    }

    @Composable
    private fun FavoritesEffects() {
        val effect by viewModel.effect.collectAsState(null)
        LaunchedEffect(effect) {
            when (val e = effect) {
                is FavoritesEffect.OpenWord -> openWord(e.wordCombined)
                null -> Unit
            }
        }
    }

    @Composable
    private fun WordsList(
        w: ContentState.Success<List<WordCombinedUi>>,
        insets: PaddingValues
    ) {
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = insets.add(vertical = 16.dp, horizontal = 0.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(w.data, key = { word -> word.word }) { word ->
                WordCardLarge(
                    viewModel::openWord,
                    viewModel::favoriteWord,
                    word,
                    Modifier.fillParentMaxWidth().padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun EmptyItem(title: String, description: String) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 20.dp),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = description,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}