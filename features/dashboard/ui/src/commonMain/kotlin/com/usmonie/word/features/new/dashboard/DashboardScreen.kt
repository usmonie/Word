package com.usmonie.word.features.new.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.OpenBrowser
import com.usmonie.word.features.Url
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.ClearRecentUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.GetSearchHistoryUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.GetWordOfTheDayUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.SearchWordsUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCaseImpl
import com.usmonie.word.features.new.components.SearchWordCard
import com.usmonie.word.features.new.details.WordDetailsScreen
import com.usmonie.word.features.new.favorites.FavoritesScreen
import com.usmonie.word.features.new.games.hangman.HangmanGameScreen
import com.usmonie.word.features.new.settings.SettingsScreen
import com.usmonie.word.features.ui.AdMob
import com.usmonie.word.features.ui.BaseLazyColumn
import com.usmonie.word.features.ui.MenuItem
import com.usmonie.word.features.ui.SearchBar
import com.usmonie.word.features.ui.TopBackButtonBar
import com.usmonie.word.features.ui.VerticalAnimatedVisibility
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.speech.core.ui.AppKeys
import wtf.speech.core.ui.ContentState
import wtf.word.core.domain.Analytics

@Stable
class DashboardScreen(
    private val dashboardViewModel: DashboardViewModel,
    private val adMob: AdMob
) : Screen(dashboardViewModel) {

    override val id: String = ID

    @Composable
    override fun Content() {
        val state by dashboardViewModel.state.collectAsState()
        val effect by dashboardViewModel.effect.collectAsState(null)
        val localFocusManager = LocalFocusManager.current
        val listState = rememberLazyListState()

        val onPointerInput: () -> Unit = remember { { localFocusManager.clearFocus() } }

        if (effect is DashboardEffect.OpenUrl) {
            OpenBrowser(Url((effect as DashboardEffect.OpenUrl).url))
        }

        DashboardEffects(listState, localFocusManager, effect)

        Scaffold(
            topBar = {
                TopBackButtonBar(dashboardViewModel::onBackClick, state.query.text.isNotBlank())
            },
            modifier = Modifier.fillMaxSize().pointerInput(Unit) { onPointerInput() }
        ) { insets ->
            MainState(onPointerInput, dashboardViewModel, listState, insets, state, adMob)
        }
    }

    companion object {
        const val ID = "DASHBOARD_SCREEN"
    }

    class Builder(
        private val wordRepository: WordRepository,
        private val adMob: AdMob,
        private val analytics: Analytics
    ) : ScreenBuilder {
        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?) = DashboardScreen(
            DashboardViewModel(
                SearchWordsUseCaseImpl(wordRepository),
                GetSearchHistoryUseCaseImpl(wordRepository),
                GetWordOfTheDayUseCaseImpl(wordRepository),
                UpdateFavouriteUseCaseImpl(wordRepository),
                RandomWordUseCaseImpl(wordRepository),
                ClearRecentUseCaseImpl(wordRepository),
                analytics
            ),
            adMob
        )
    }
}


@Composable
private fun DashboardEffects(
    listState: LazyListState,
    localFocusManager: FocusManager,
    effect: DashboardEffect?
) {
    val routeManager = LocalRouteManager.current
    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        if (listState.firstVisibleItemScrollOffset > 100) {
            localFocusManager.clearFocus()
        }
    }

    LaunchedEffect(effect) {
        when (effect) {
            is DashboardEffect.OpenFavourites -> routeManager.navigateTo(FavoritesScreen.ID)
            is DashboardEffect.OpenWord -> routeManager.navigateTo(
                WordDetailsScreen.ID,
                extras = WordDetailsScreen.Companion.WordExtra(effect.word)
            )

            is DashboardEffect.OpenHangman -> routeManager.navigateTo(HangmanGameScreen.ID)
            is DashboardEffect.OpenSettings -> routeManager.navigateTo(SettingsScreen.ID)
            else -> Unit
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainState(
    onPointerInput: () -> Unit,
    dashboardViewModel: DashboardViewModel,
    listState: LazyListState,
    insets: PaddingValues,
    state: DashboardState,
    adMob: AdMob,
) {
    val showMenuItems by remember(state.query) { derivedStateOf { state.query.text.isBlank() } }
    val hasFocus = remember { mutableStateOf(false) }
    val query by remember(state) { derivedStateOf { state.query } }
    val searchEnabled by remember(state) { derivedStateOf { true } }
    Box(
        Modifier.padding(
            start = insets.calculateLeftPadding(LayoutDirection.Ltr),
            end = insets.calculateRightPadding(LayoutDirection.Ltr),
            top = insets.calculateTopPadding()
        )
    ) {
        BaseLazyColumn(
            listState,
            contentPadding = PaddingValues(bottom = insets.calculateBottomPadding()),
        ) {
            item {
                TopBar(
                    query,
                    dashboardViewModel::onQueryChanged,
                    searchEnabled,
                    hasFocus.value
                ) { hasFocus.value = it }
            }

            item {
                VerticalAnimatedVisibility(hasFocus.value && state.recentSearch.isNotEmpty) {
                    RecentCards(dashboardViewModel::onOpenWord, state.recentSearch)
                }
            }

            item {
                if ((state.foundWords is ContentState.Loading && !showMenuItems)) {
                    LoadingProgress()
                }
            }

            items(state.foundWords.item ?: listOf(), key = { word -> word.word }) { word ->
                if (!showMenuItems) {
                    SearchWordCard(
                        dashboardViewModel::onOpenWord,
                        remember { {} },
                        dashboardViewModel::onUpdateFavouritesPressed,
                        remember { {} },
                        word,
                        Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .animateItemPlacement()
                            .pointerInput(Unit) { onPointerInput() },
                    )
                }
            }

            item {
                WordOfTheDayMenuItem(
                    onWordClick = dashboardViewModel::onOpenWord,
                    onAddFavouritePressed = dashboardViewModel::onUpdateFavouritesPressed,
                    onSharePressed = { },
                    showWordOfTheDay = showMenuItems,
                    word = state.wordOfTheDay
                )
            }

            item(key = "MENU_RANDOM_WORD") {
                RandomWordMenuItem(
                    onMenuItemClick = dashboardViewModel::onRandomWordMenuClick,
                    onWordClick = dashboardViewModel::onOpenWord,
                    onAddFavouritePressed = dashboardViewModel::onUpdateFavouritesPressed,
                    onSharePressed = { },
                    onUpdatePressed = dashboardViewModel::onUpdateRandomCard,
                    showItem = showMenuItems,
                    showRandomWord = state.showRandomWord,
                    word = state.randomWord
                )
            }

            item(key = "MENU_GAMES") {
                Games(
                    dashboardViewModel::onGamesClicked,
                    dashboardViewModel::onHangman,
                    onPointerInput,
                    showMenuItems,
                    state.showGames,
                )
            }

            item(key = "MENU_FAVORITES") {
                FavouritesMenuItem(
                    showMenuItems,
                    dashboardViewModel::onFavouritesItemClicked,
                    Modifier.fillMaxWidth().pointerInput(Unit) { onPointerInput() }
                )
            }

            item(key = "MENU_SETTINGS") {
                Settings(
                    dashboardViewModel::onSettingsItemClicked,
                    onPointerInput,
                    showMenuItems,
                )
            }

            item(key = "MENU_ABOUT") {
                About(
                    dashboardViewModel::onAboutItemClicked,
                    {},
                    dashboardViewModel::onTelegramItemClicked,
                    {},
                    onPointerInput,
                    showMenuItems,
                    state.showAbout
                )
            }

            item { Spacer(Modifier.height(80.dp)) }
        }

        adMob.Banner(
            AppKeys.BANNER_ID,
            Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(insets)
        )
    }
}

@Composable
private fun TopBar(
    query: TextFieldValue,
    onQueryChanged: (TextFieldValue) -> Unit,
    enabled: Boolean,
    hasFocus: Boolean,
    onFocusChange: (Boolean) -> Unit
) {
    SearchBar(
        onQueryChanged,
        placeholder = "[S]earch",
        query = query,
        modifier = Modifier.fillMaxWidth(),
        hasFocus = hasFocus,
        enabled = enabled,
        onFocusChange = onFocusChange,
    )
}

@Composable
private fun LoadingProgress() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
    }
}

@Composable
fun FavouritesMenuItem(
    showItem: Boolean,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    VerticalAnimatedVisibility(showItem) {
        Column {
            MenuItem(onMenuClick, "Favorites", modifier.testTag("FAVOURITES_ITEM"))
        }
    }
}
