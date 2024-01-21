package com.usmonie.word.features.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.SearchWordCard
import com.usmonie.word.features.models.WordCombinedUi
import com.usmonie.word.features.models.WordUi
import com.usmonie.word.features.ui.AdMob
import com.usmonie.word.features.ui.BaseLazyColumn
import com.usmonie.word.features.ui.GradientBox
import com.usmonie.word.features.ui.MenuItem
import com.usmonie.word.features.ui.VerticalAnimatedVisibility
import wtf.speech.core.ui.AppKeys
import wtf.speech.core.ui.ContentState

@ExperimentalMaterial3Api
@Composable
fun DashboardContent(
    viewModel: DashboardViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
    adMob: AdMob
) {
    val state by viewModel.state.collectAsState()
    val hasFocus = remember { mutableStateOf(false) }

    val localFocusManager = LocalFocusManager.current

    DashboardContent(
        remember { { scrollBehavior } },
        viewModel,
        remember { { state.query } },
        hasFocus,
        remember { { hasFocus.value && state.recentSearch.isNotEmpty } },
        remember { { state.recentSearch } },
        remember { { state.foundWords is ContentState.Loading && state.query.text.isNotBlank() } },
        remember {
            {
                val words = state.foundWords.item
                if (state.query.text.isBlank() || words == null) WordsState(listOf())
                else WordsState(words)
            }
        },
        remember { { localFocusManager.clearFocus(true) } },
        remember { { state.query.text.isBlank() } },
        remember { { state.wordOfTheDay } },
        remember { { state.showRandomWord } },
        remember { { state.randomWord } },
        remember { { state.showGames } },
        remember { { state.showAbout } },
        adMob
    )
}

@ExperimentalMaterial3Api
@Composable
private fun DashboardContent(
    scrollBehavior: () -> TopAppBarScrollBehavior,
    viewModel: DashboardViewModel,
    query: () -> TextFieldValue,
    hasFocus: MutableState<Boolean>,
    showRecentWords: () -> Boolean,
    recentSearchWords: () -> WordsState,
    showLoading: () -> Boolean,
    foundWords: () -> WordsState,
    onPointerInput: () -> Unit,
    showMenuItems: () -> Boolean,
    wordOfTheDay: () -> ContentState<Pair<WordUi, WordCombinedUi>>,
    showRandomWord: () -> Boolean,
    randomWord: () -> ContentState<Pair<WordUi, WordCombinedUi>>,
    showGames: () -> Boolean,
    showAbout: () -> Boolean,
    adMob: AdMob
) {
    val localFocusManager = LocalFocusManager.current
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior().nestedScrollConnection),
        topBar = {
//            WordTopBar(
//                viewModel::onBackClick,
//                viewModel::onQueryChanged,
//                true,
//                "[S]earch",
//                query,
//                hasFocus,
//                scrollBehavior
//            )
        },
    ) { insets ->
        ListContent(
            insets,
            showRecentWords,
            viewModel,
            recentSearchWords,
            showLoading,
            foundWords,
            showMenuItems,
            wordOfTheDay,
            showRandomWord,
            randomWord,
            showGames,
            showAbout,
            adMob,
            Modifier.pointerInput(Unit) {
                detectTapGestures { localFocusManager.clearFocus(true) }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ListContent(
    insets: PaddingValues,
    showRecentWords: () -> Boolean,
    viewModel: DashboardViewModel,
    recentSearchWords: () -> WordsState,
    showLoading: () -> Boolean,
    foundWords: () -> WordsState,
    onShowMenuItems: () -> Boolean,
    wordOfTheDay: () -> ContentState<Pair<WordUi, WordCombinedUi>>,
    showRandomWord: () -> Boolean,
    randomWord: () -> ContentState<Pair<WordUi, WordCombinedUi>>,
    showGames: () -> Boolean,
    showAbout: () -> Boolean,
    adMob: AdMob,
    modifier: Modifier
) {
    val listState = rememberLazyListState()
    val localFocusManager = LocalFocusManager.current

    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        localFocusManager.clearFocus()
    }
    val showMenuItems = onShowMenuItems()

    LaunchedEffect(showRecentWords()) {
        listState.scrollToItem(0)
    }
    GradientBox(
        modifier.fillMaxSize(),
        insets = PaddingValues(
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
                VerticalAnimatedVisibility(showRecentWords()) {
                    RecentCards(viewModel::onOpenWord, recentSearchWords)
                }
            }

            item {
                if (showLoading()) {
                    LoadingProgress()
                }
            }

            items(foundWords().words, key = { it.word }) { wordCombined ->
                SearchWordCard(
                    viewModel::onOpenWord,
                    remember { {} },
                    viewModel::onUpdateFavouritesPressed,
                    remember { {} },
                    remember { { wordCombined } },
                    Modifier.fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                        .animateItemPlacement(),
                )
            }

            item {
                if (showMenuItems) {
                    WordOfTheDayMenuItem(
                        onWordClick = viewModel::onOpenWord,
                        onAddFavouritePressed = viewModel::onUpdateFavouritesPressed,
                        onSharePressed = { },
                        showWordOfTheDay = showMenuItems,
                        word = wordOfTheDay
                    )
                }
            }

            item(key = "MENU_RANDOM_WORD") {
                if (showMenuItems) {
                    RandomWordMenuItem(
                        onMenuItemClick = viewModel::onRandomWordMenuClick,
                        onWordClick = viewModel::onOpenWord,
                        onAddFavouritePressed = viewModel::onUpdateFavouritesPressed,
                        onSharePressed = { },
                        onUpdatePressed = viewModel::onUpdateRandomCard,
                        showRandomWord = showRandomWord,
                        word = randomWord
                    )
                }
            }

            item(key = "MENU_GAMES") {
                if (showMenuItems) {
                    Games(
                        viewModel::onGamesClicked,
                        viewModel::onHangman,
                        showGames,
                    )
                }
            }

            item(key = "MENU_FAVORITES") {
                if (showMenuItems) {
                    FavouritesMenuItem(
                        viewModel::onFavouritesItemClicked,
                        Modifier.fillMaxWidth()
                    )
                }
            }

            item(key = "MENU_SETTINGS") {
                if (showMenuItems) {
                    SettingsMenuItems(
                        viewModel::onSettingsItemClicked,
                    )
                }
            }

            item(key = "MENU_ABOUT") {
                if (showMenuItems) {
                    AboutMenuItems(
                        viewModel::onAboutItemClicked,
                        {},
                        viewModel::onTelegramItemClicked,
                        {},
                        showAbout
                    )
                }
            }
        }

        adMob.Banner(
            AppKeys.BANNER_ID,
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(insets)
        )
    }
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
private fun FavouritesMenuItem(
    onMenuClick: () -> Unit, modifier: Modifier = Modifier
) {
    MenuItem(onMenuClick, "Favorites", modifier.testTag("FAVOURITES_ITEM"))
}