package com.usmonie.word.features.favorites.ui.words

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
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
import com.usmonie.word.features.ads.ui.LocalAdsManager
import com.usmonie.word.features.games.ui.WordCardLarge
import com.usmonie.word.features.games.ui.models.WordCombinedUi
import com.usmonie.word.features.favorites.ui.FavoritesScreenFactory
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionPage
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionScreenState
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.favorites.ui.generated.resources.Res
import word.shared.feature.favorites.ui.generated.resources.favorite_words_empty_description
import word.shared.feature.favorites.ui.generated.resources.favorites_empty_title
import word.shared.feature.favorites.ui.generated.resources.favorites_title

internal class FavoriteWordsScreen(
    viewModel: FavoriteWordsViewModel,
    private val subscriptionsViewModel: SubscriptionViewModel,
    private val openWord: (WordCombinedUi) -> Unit
) : StateScreen<FavoritesState, FavoritesAction, FavoritesEvent, FavoritesEffect, FavoriteWordsViewModel>(
    viewModel
) {
    override val id: ScreenId = FavoritesScreenFactory.ID

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current
        val favoritesPlaceholder = stringResource(Res.string.favorites_title)
        val subscriptionsState by subscriptionsViewModel.state.collectAsState()

        HeaderWordScaffold(
            placeholder = { favoritesPlaceholder },
            onBackClicked = routeManager::popBackstack,
            header = if (subscriptionsState is SubscriptionScreenState.Empty) {
                null
            } else {
                { SubscriptionPage(subscriptionsViewModel) }
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
        ) {
            FavoriteWordsContent(openWord, viewModel, it)
        }
    }
}

@Composable
internal fun FavoriteWordsContent(
    openWord: (WordCombinedUi) -> Unit,
    viewModel: FavoriteWordsViewModel,
    insets: PaddingValues
) {
    FavoritesEffects(openWord, viewModel)
    val state by viewModel.state.collectAsState()
    when (val w = state.words) {
        is ContentState.Error<*, *> -> Box(Modifier.padding(insets)) {
            EmptyItem(
                stringResource(Res.string.favorites_empty_title),
                stringResource(Res.string.favorite_words_empty_description),
                modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp)
            )
        }

        is ContentState.Loading -> LinearProgressIndicator(
            Modifier.padding(insets).fillMaxWidth().padding(horizontal = 32.dp)
        )

        is ContentState.Success -> if (w.data.isEmpty()) {
            Box(Modifier.padding(insets)) {
                EmptyItem(
                    stringResource(Res.string.favorites_empty_title),
                    stringResource(Res.string.favorite_words_empty_description),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp)
                )
            }
        } else {
            WordsList(w, insets, viewModel)
        }
    }
}

@Composable
private fun FavoritesEffects(openWord: (WordCombinedUi) -> Unit, viewModel: FavoriteWordsViewModel) {
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
    insets: PaddingValues,
    viewModel: FavoriteWordsViewModel
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

@Composable
fun EmptyItem(title: String, description: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 32.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = description,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 32.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
