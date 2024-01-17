package com.usmonie.word.features.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.runtime.derivedStateOf
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
import com.usmonie.word.features.ui.DashboardTopBar
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
    val recentSearchWords by remember(state) {
        derivedStateOf { state.recentSearch }
    }

    val randomWord by remember(state) {
        derivedStateOf { state.randomWord }
    }

    val wordOfTheDay by remember(state) {
        derivedStateOf { state.wordOfTheDay }
    }

    val query by remember(state) {
        derivedStateOf { state.query }
    }

    val showRandomWord by remember(state) {
        derivedStateOf { state.showRandomWord }
    }

    val showGames by remember(state) {
        derivedStateOf { state.showGames }
    }

    val showAbout by remember(state) {
        derivedStateOf { state.showAbout }
    }

    val showMenuItems by remember(query.text) { derivedStateOf { query.text.isBlank() } }

    val hasFocus = remember { mutableStateOf(false) }

    val focused by remember(hasFocus.value) {
        derivedStateOf {
            hasFocus.value
        }
    }

    val showRecentWords by remember(focused, recentSearchWords.isNotEmpty) {
        derivedStateOf {
            focused && recentSearchWords.isNotEmpty
        }
    }

    val foundWordsState by remember(state) {
        derivedStateOf { state.foundWords }
    }

    val foundWords by remember(state, showMenuItems) {
        derivedStateOf {
            val words = state.foundWords.item
            if (showMenuItems || words == null) WordsState(listOf()) else WordsState(words)
        }
    }

    val showLoading by remember(foundWordsState, showMenuItems) {
        derivedStateOf { foundWordsState is ContentState.Loading && !showMenuItems }
    }

    val onPointerInput: () -> Unit = remember { { } }
    val listState = rememberLazyListState()
    val localFocusManager = LocalFocusManager.current

    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        if (listState.firstVisibleItemScrollOffset > 100) {
            localFocusManager.clearFocus()
        }
    }
    DashboardContent(
        scrollBehavior,
        viewModel,
        query,
        hasFocus,
        listState,
        showRecentWords,
        recentSearchWords,
        showLoading,
        foundWords,
        onPointerInput,
        showMenuItems,
        wordOfTheDay,
        showRandomWord,
        randomWord,
        showGames,
        showAbout,
        adMob
    )
}

@ExperimentalMaterial3Api
@Composable
private fun DashboardContent(
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: DashboardViewModel,
    query: TextFieldValue,
    hasFocus: MutableState<Boolean>,
    listState: LazyListState,
    showRecentWords: Boolean,
    recentSearchWords: WordsState,
    showLoading: Boolean,
    foundWords: WordsState,
    onPointerInput: () -> Unit,
    showMenuItems: Boolean,
    wordOfTheDay: ContentState<Pair<WordUi, WordCombinedUi>>,
    showRandomWord: Boolean,
    randomWord: ContentState<Pair<WordUi, WordCombinedUi>>,
    showGames: Boolean,
    showAbout: Boolean,
    adMob: AdMob
) {
    val localFocusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DashboardTopBar(
                viewModel::onBackClick,
                viewModel::onQueryChanged,
                true,
                "[S]earch",
                query,
                hasFocus,
                scrollBehavior
            )
        },
    ) { insets ->
        ListContent(
            insets,
            listState,
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
    listState: LazyListState,
    showRecentWords: Boolean,
    viewModel: DashboardViewModel,
    recentSearchWords: WordsState,
    showLoading: Boolean,
    foundWords: WordsState,
    showMenuItems: Boolean,
    wordOfTheDay: ContentState<Pair<WordUi, WordCombinedUi>>,
    showRandomWord: Boolean,
    randomWord: ContentState<Pair<WordUi, WordCombinedUi>>,
    showGames: Boolean,
    showAbout: Boolean,
    adMob: AdMob,
    modifier: Modifier
) {
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
                VerticalAnimatedVisibility(showRecentWords) {
                    RecentCards(viewModel::onOpenWord, recentSearchWords)
                }
            }

            item {
                if (showLoading) {
                    LoadingProgress()
                }
            }

            items(foundWords.words, key = { it.word }) { wordCombined ->
                SearchWordCard(
                    viewModel::onOpenWord,
                    remember { {} },
                    viewModel::onUpdateFavouritesPressed,
                    remember { {} },
                    wordCombined,
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
                        showRandomWord = { showRandomWord },
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
                    Settings(
                        viewModel::onSettingsItemClicked,
                    )
                }
            }

            item(key = "MENU_ABOUT") {
                if (showMenuItems) {
                    About(
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