package com.usmonie.word.features.dashboard.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.compass.viewmodel.ContentState
import com.usmonie.compass.viewmodel.StateScreen
import com.usmonie.core.kit.composables.base.buttons.TextButton
import com.usmonie.core.kit.composables.word.LargeWordScaffold
import com.usmonie.core.kit.tools.addVertical
import com.usmonie.word.features.dictionary.ui.RandomWordCollapsedState
import com.usmonie.word.features.dictionary.ui.RandomWordExpandedState
import com.usmonie.word.features.dictionary.ui.WordCardLarge
import com.usmonie.word.features.dictionary.ui.WordCardSmall
import com.usmonie.word.features.dictionary.ui.WordOfTheDayState
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.dashboard.ui.generated.resources.Res
import word.shared.feature.dashboard.ui.generated.resources.dashboard_search_history_title
import word.shared.feature.dashboard.ui.generated.resources.favorites_subtitle
import word.shared.feature.dashboard.ui.generated.resources.games_subtitle
import word.shared.feature.dashboard.ui.generated.resources.network_connection_description
import word.shared.feature.dashboard.ui.generated.resources.network_connection_error
import word.shared.feature.dashboard.ui.generated.resources.network_connection_try_again
import word.shared.feature.dashboard.ui.generated.resources.search_title
import word.shared.feature.dashboard.ui.generated.resources.settings_subtitle

internal class DashboardScreen(
    viewModel: DashboardViewModel,
    private val onOpenWord: (wordCombined: WordCombinedUi) -> Unit,
) : StateScreen<DashboardState, DashboardAction, DashboardEvent, DashboardEffect, DashboardViewModel>(
    viewModel
) {
    override val id: ScreenId = DashboardScreenFactory.ID

    @Composable
    override fun Content() {
        val state by viewModel.state.collectAsState()
        val searchPlaceholder = stringResource(Res.string.search_title)

        DashboardEffect(onOpenWord, viewModel)

        LargeWordScaffold(
            query = { state.searchFieldState.searchFieldValue },
            onQueryChanged = viewModel::inputQuery,
            placeholder = { searchPlaceholder },
            getShowBackButton = { state.searchFieldState.searchFieldValue.text.isNotEmpty() },
            onBackClicked = viewModel::backToMain,
            hasSearchFieldFocus = { state.searchFieldState.hasFocus },
            updateSearchFieldFocus = viewModel::queryFieldFocusChanged,
        ) { insets ->
            val lazyListState = rememberLazyListState()
            val isSearchEmpty by remember { derivedStateOf { state.recentSearch.isEmpty() } }
            LaunchedEffect(isSearchEmpty) {
                if (!isSearchEmpty) {
                    lazyListState.scrollToItem(0)
                }
            }

            val newInsets = remember(insets) { insets.addVertical(16.dp) }
            LazyColumn(
                state = lazyListState,
                contentPadding = newInsets,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                when (val foundWordState = state.foundWords) {
                    is ContentState.Error<*, *> -> defaultState(state)
                    is ContentState.Loading -> item {
                        LinearProgressIndicator(Modifier.fillParentMaxWidth().padding(32.dp))
                    }

                    is ContentState.Success -> {
                        if (state.searchFieldState.searchFieldValue.text.isBlank()) {
                            defaultState(state)
                        } else {
                            items(foundWordState.data) {
                                WordCardLarge(
                                    viewModel::openSearchWord,
                                    viewModel::favoriteWord,
                                    it,
                                    Modifier.fillParentMaxWidth().padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun LazyListScope.defaultState(state: DashboardState) {
        if (state.recentSearch.isNotEmpty()) {
            item {
                RecentSearchHistory(state)
            }
        }


        item(
            key = DashboardMenuItem.FAVORITES,
            contentType = DashboardMenuItem::class
        ) {
            MenuItem(
                viewModel::openFavorites,
                stringResource(Res.string.favorites_subtitle),
                menuItemModifier
            )
        }
        item(key = DashboardMenuItem.GAMES, contentType = DashboardMenuItem::class) {
            MenuItem(
                viewModel::openGames,
                stringResource(Res.string.games_subtitle),
                menuItemModifier
            )
        }
        item(key = DashboardMenuItem.SETTINGS, contentType = DashboardMenuItem::class) {
            MenuItem(
                viewModel::openSettings,
                stringResource(Res.string.settings_subtitle),
                menuItemModifier
            )
        }

        if (state.wordOfTheDay is ContentState.Error<*, *> &&
            state.randomWord is ContentState.Error<*, *>
        ) {
            item {
                Column(
                    Modifier.padding(horizontal = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        stringResource(Res.string.network_connection_error),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        stringResource(Res.string.network_connection_description),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    TextButton(
                        stringResource(Res.string.network_connection_try_again),
                        viewModel::tryAgain,
                        contentPadding = PaddingValues(0.dp)
                    )
                }
            }
        }

        if (state.wordOfTheDay !is ContentState.Error<*, *>) {
            item(key = "WordOfTheDay", contentType = "WordOfTheDay") {
                WordOfTheDayState({ state.wordOfTheDay }, {}, {})
            }
        }

        item(key = "RandomWord", contentType = "RandomWord") {
            AnimatedContent(
                state.wordOfTheDay is ContentState.Error<*, *> ||
                        state.wordOfTheDay is ContentState.Loading
            ) { wordOfTheDayLoadingState ->
                if (wordOfTheDayLoadingState) {
                    RandomWordExpandedState(
                        { state.randomWord },
                        viewModel::openWord,
                        viewModel::favoriteWord,
                        viewModel::nextRandomWord,
                        Modifier.padding(horizontal = 16.dp)
                    )
                } else {
                    RandomWordCollapsedState(
                        { state.randomWord },
                        viewModel::openWord,
                        viewModel::favoriteWord,
                        viewModel::nextRandomWord,
                        Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }

    @Composable
    private fun RecentSearchHistory(state: DashboardState) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                stringResource(Res.string.dashboard_search_history_title),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.recentSearch) { historyWord ->
                    WordCardSmall(
                        viewModel::openWord,
                        historyWord,
                        Modifier.fillParentMaxWidth(fraction = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
internal fun DashboardEffect(
    onOpenWord: (wordCombined: WordCombinedUi) -> Unit,
    viewModel: DashboardViewModel
) {
    val effect by viewModel.effect.collectAsState(null)
    LaunchedEffect(effect) {
        when (val e = effect) {
            is DashboardEffect.OnMenuItemClicked -> TODO()
            is DashboardEffect.OpenWord -> onOpenWord(e.word)
            null -> Unit
        }
    }
}

private val menuItemTitleModifier =
    Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
private val menuItemModifier = Modifier.padding(horizontal = 16.dp)

@Composable
fun MenuItem(onClick: () -> Unit, title: String, modifier: Modifier = Modifier) {
    Card(
        onClick,
        modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
                modifier = menuItemTitleModifier
            )
        }
    }
}
