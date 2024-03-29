package com.usmonie.word.features.dashboard.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.ui.SearchWordCard
import com.usmonie.word.features.dashboard.ui.models.LearningStatus
import com.usmonie.word.features.dashboard.ui.models.WordCombinedUi
import com.usmonie.word.features.dashboard.ui.models.WordUi
import com.usmonie.word.features.dashboard.ui.ui.AdMob
import com.usmonie.word.features.dashboard.ui.ui.BaseTextButton
import com.usmonie.word.features.dashboard.ui.ui.PrimaryStatusCard
import com.usmonie.word.features.dashboard.ui.ui.StatusCard
import com.usmonie.word.features.dashboard.ui.ui.WordButtons
import com.usmonie.word.features.dashboard.ui.ui.WordLargeResizableTitle
import org.jetbrains.compose.resources.ExperimentalResourceApi
import wtf.speech.core.ui.AppKeys
import wtf.speech.core.ui.BaseCard
import wtf.speech.core.ui.ContentState
import wtf.word.core.design.themes.Resources

@ExperimentalMaterial3Api
@Composable
internal fun DashboardContent(
    listState: LazyGridState,
    dashboardViewModel: DashboardViewModel,
    adMob: AdMob,
    insets: PaddingValues,
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(
                PaddingValues(
                    start = insets.calculateLeftPadding(LayoutDirection.Ltr),
                    end = insets.calculateRightPadding(LayoutDirection.Ltr),
                    top = insets.calculateTopPadding()
                )
            )
    ) {
        val state by dashboardViewModel.state.collectAsState()

        when (val s = state) {
            is DashboardState.Error -> {
                Column(Modifier.align(Alignment.Center)) {
                    Text("Error while loading", style = MaterialTheme.typography.headlineMedium)
                    Spacer(Modifier.height(12.dp))
                    BaseTextButton({ "Try Again" }, dashboardViewModel::onTryAgain)
                }
            }

            is DashboardState.Loading -> {
                CircularProgressIndicator(
                    Modifier.align(Alignment.Center)
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 24.dp)
                )
            }

            is DashboardState.Success -> {
                val learnedWords = s.learnedWordsStatus
                val practiceWords = s.practiceWordsStatus
                val newWords = s.newWordsStatus
                val streak = s.streakDaysStatus
                val wordOfTheDay = s.wordOfTheDay
                val randomWord = s.randomWord
                val foundWords = s.foundWords

                DashboardItemsList(
                    listState,
                    dashboardViewModel::onFavoritesClick,
                    dashboardViewModel::onGamesClick,
                    dashboardViewModel::onSettingsClick,
                    dashboardViewModel::onAboutClick,
                    dashboardViewModel::onLearnClick,
                    dashboardViewModel::onOpenWord,
                    dashboardViewModel::onUpdateFavorite,
                    remember { { dashboardViewModel.onNextRandomWord(s) } },
                    remember {
                        {
                            PaddingValues(
                                top = 12.dp,
                                start = 24.dp,
                                end = 24.dp,
                                bottom = insets.calculateBottomPadding() + 80.dp
                            )
                        }
                    },
                    { s.query.text.isNotBlank() },
                    learnedWords,
                    practiceWords,
                    newWords,
                    streak,
                    wordOfTheDay,
                    randomWord,
                    foundWords,
                    adMob
                )

                adMob.Banner(
                    AppKeys.BANNER_ID,
                    Modifier.fillMaxWidth().height(56.dp).align(Alignment.BottomCenter)
                )
            }
        }
    }
}

private val fillMaxWidthModifier = Modifier.fillMaxWidth()
private val fillMaxWidthModifierHorizontalPadding =
    fillMaxWidthModifier.padding(horizontal = 24.dp)

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun DashboardItemsList(
    listState: LazyGridState,
    onFavoritesClick: () -> Unit,
    onGamesClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit,
    onLearnClick: (WordCombinedUi) -> Unit,
    onClick: (WordCombinedUi) -> Unit,
    onUpdateFavoriteClick: (WordCombinedUi) -> Unit,
    onUpdateRandomWord: () -> Unit,
    contentPadding: () -> PaddingValues,
    getShowSearchItems: () -> Boolean,
    learnedWords: LearningStatus,
    practiceWords: LearningStatus,
    newWords: LearningStatus,
    streak: LearningStatus,
    wordOfTheDay: ContentState<Pair<WordUi, WordCombinedUi>>,
    randomWord: ContentState<Pair<WordUi, WordCombinedUi>>,
    foundWords: ContentState<List<WordCombinedUi>>,
    adMob: AdMob
) {
    val columns = GridCells.Fixed(5)

    val showSearchItems = getShowSearchItems()
    LazyVerticalGrid(
        columns,
        state = listState,
        modifier = fillMaxWidthModifier.imePadding(),
        contentPadding = contentPadding(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (!showSearchItems) {
            if (false) {
                item(span = { GridItemSpan(5) }) {
                    DashboardSubtitle(
                        "Learning status",
                        fillMaxWidthModifier
                    )
                }

                item(span = { GridItemSpan(2) }) {
                    StatusCard(
                        {},
                        { learnedWords.title },
                        { learnedWords.status.toString() },
                        { learnedWords.description },
                        fillMaxWidthModifier
                    )
                }
                item(span = { GridItemSpan(3) }) {
                    StatusCard(
                        {},
                        { practiceWords.title },
                        { practiceWords.status.toString() },
                        { practiceWords.description },
                        fillMaxWidthModifier
                    )
                }
                item(span = { GridItemSpan(3) }) {
                    PrimaryStatusCard(
                        {},
                        { newWords.title },
                        { newWords.status.toString() },
                        { newWords.description },
                        fillMaxWidthModifier
                    )
                }
                item(span = { GridItemSpan(2) }) {
                    StatusCard(
                        {},
                        { streak.title },
                        { streak.status.toString() },
                        { streak.description },
                        fillMaxWidthModifier
                    )
                }
                item(span = { GridItemSpan(5) }) {
                    Button({}) {
                        Icon(
                            Resources.ic_rocket_launch_outline,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )

                        Text(
                            "Continue learning",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp)
                        )
                    }
                }
            }

            item(span = { GridItemSpan(5) }) {
                DashboardSubtitle(
                    "Vocabulary",
                    fillMaxWidthModifier
                )
            }

            if (wordOfTheDay !is ContentState.Error<*, *>) {
                item(span = { GridItemSpan(5) }) {
                    WordOfTheDayCard(
                        onClick,
                        onUpdateFavoriteClick,
                        onLearnClick,
                        { wordOfTheDay },
                        fillMaxWidthModifier
                    )
                }
            }

            item(span = { GridItemSpan(5) }) {
                RandomWordCard(
                    onClick,
                    onUpdateFavoriteClick,
                    onUpdateRandomWord,
                    onLearnClick,
                    { randomWord },
                    fillMaxWidthModifier
                )
            }
            item(span = { GridItemSpan(5) }) {
                DashboardMenuItem(
                    "[F]avorites",
                    fillMaxWidthModifier.clickable(onClick = onFavoritesClick)
                )
            }

            item(span = { GridItemSpan(5) }) {
                Box(Modifier.clickable { })
                DashboardMenuItem(
                    "[G]ames",
                    fillMaxWidthModifier.clickable(onClick = onGamesClick)
                )
            }
            item(span = { GridItemSpan(5) }) {
                DashboardMenuItem(
                    "[S]ettings",
                    fillMaxWidthModifier.clickable(onClick = onSettingsClick)
                )
            }

//            item(span = { GridItemSpan(5) }) {
//                DashboardMenuItem(
//                    "[A]bout",
//                    fillMaxWidthModifierHorizontalPadding.clickable(onClick = onAboutClick)
//                )
//            }
        } else {
            items(foundWords.item ?: listOf(), span = { GridItemSpan(5) }) {
                SearchWordCard(
                    onClick,
                    onLearnClick,
                    onUpdateFavoriteClick,
                    {},
                    { it },
                    fillMaxWidthModifier
                )
            }
        }
    }
}

@Composable
fun DashboardSubtitle(
    subtitle: String,
    modifier: Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        text = subtitle,
        style = MaterialTheme.typography.titleMedium,
        color = color,
        modifier = modifier
    )
}


@Composable
fun DashboardMenuItem(
    title: String,
    modifier: Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Box(modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = color,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
    }
}

@Composable
fun WordOfTheDayCard(
    onClick: (WordCombinedUi) -> Unit,
    onUpdateFavoriteClick: (WordCombinedUi) -> Unit,
    onLearnClick: (WordCombinedUi) -> Unit,
    word: () -> ContentState<Pair<WordUi, WordCombinedUi>>,
    modifier: Modifier
) {
    when (val w = word()) {
        is ContentState.Error<*, *> -> Unit
        is ContentState.Loading -> BaseCard(
            modifier = modifier
        ) {
            Box(Modifier.fillMaxWidth().padding(vertical = 20.dp), Alignment.Center) {
                CircularProgressIndicator(
                    Modifier.size(32.dp),
                    MaterialTheme.colorScheme.onSurface
                )
            }
        }

        is ContentState.Success -> BaseCard(
            onClick = remember { { onClick(w.data.second) } },
            modifier = modifier
        ) {
            Spacer(Modifier.height(24.dp))
            Text(
                "Word of The Day",
                style = MaterialTheme.typography.titleMedium,
                modifier = fillMaxWidthModifierHorizontalPadding,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            WordLargeResizableTitle(
                w.data.first.word,
                fillMaxWidthModifierHorizontalPadding,
                TextAlign.Start
            )
            WordButtons(
                { onLearnClick(w.data.second) },
                { onUpdateFavoriteClick(w.data.second) },
                {},
                { w.data.second.isFavorite },
                modifier = fillMaxWidthModifierHorizontalPadding,
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}


@Composable
fun RandomWordCard(
    onClick: (WordCombinedUi) -> Unit,
    onUpdateFavoriteClick: (WordCombinedUi) -> Unit,
    onUpdateRandomWord: () -> Unit,
    onLearnClick: (WordCombinedUi) -> Unit,
    word: () -> ContentState<Pair<WordUi, WordCombinedUi>>,
    modifier: Modifier
) {
    when (val w = word()) {
        is ContentState.Error<*, *> -> Unit
        is ContentState.Loading -> BaseCard(
            modifier = modifier
        ) {
            Box(Modifier.fillMaxWidth().padding(vertical = 24.dp), Alignment.Center) {
                CircularProgressIndicator(
                    Modifier.size(32.dp),
                    MaterialTheme.colorScheme.onSurface
                )
            }
        }

        is ContentState.Success -> BaseCard(
            onClick = { onClick(w.data.second) },
            modifier = modifier
        ) {
            Spacer(Modifier.height(24.dp))
            Text(
                "Random Word",
                modifier = fillMaxWidthModifierHorizontalPadding,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            WordLargeResizableTitle(
                w.data.first.word,
                fillMaxWidthModifierHorizontalPadding,
                TextAlign.Start
            )
            WordButtons(
                remember { { onLearnClick(w.data.second) } },
                { onUpdateFavoriteClick(w.data.second) },
                onUpdateRandomWord,
                { w.data.second.isFavorite },
                nextEnabled = true,
                modifier = fillMaxWidthModifierHorizontalPadding,
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}
