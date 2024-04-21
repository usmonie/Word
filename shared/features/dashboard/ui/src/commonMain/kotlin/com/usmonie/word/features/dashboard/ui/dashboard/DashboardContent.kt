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
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.ui.ui.AdMob
import com.usmonie.word.features.dashboard.ui.ui.BaseTextButton
import com.usmonie.word.features.dashboard.ui.ui.PrimaryStatusCard
import com.usmonie.word.features.dashboard.ui.ui.SearchWordCard
import com.usmonie.word.features.dashboard.ui.ui.StatusCard
import wtf.speech.core.ui.testDesc
import wtf.word.core.design.themes.icons.MyIconPack
import wtf.word.core.design.themes.icons.myiconpack.IcRocketLaunchOutline
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.ui.models.WordCombinedUi
import com.usmonie.word.features.dashboard.ui.models.WordUi
import com.usmonie.word.features.dashboard.ui.ui.Sense
import com.usmonie.word.features.dashboard.ui.ui.WordButtons
import com.usmonie.word.features.dashboard.ui.ui.WordLargeResizableTitle
import wtf.speech.core.ui.BaseCard
import wtf.speech.core.ui.ContentState
import wtf.word.core.domain.tools.fastForEach

private val fillMaxWidthModifier = Modifier.fillMaxWidth()
private val fillMaxWidthModifierHorizontalPadding =
    fillMaxWidthModifier.padding(horizontal = 24.dp)

@Composable
internal fun NewDashboardContent(
    insets: () -> PaddingValues,
    dashboardViewModel: DashboardViewModel,
    adMob: AdMob,
) {
    Box(Modifier.fillMaxSize()) {
        val state by dashboardViewModel.state.collectAsState()
        when (val s = state) {
            is DashboardState.Error -> ErrorDashboardContent(
                dashboardViewModel::onTryAgain,
                Modifier.align(Alignment.Center)
            )

            is DashboardState.Loading -> CircularProgressIndicator(
                Modifier.align(Alignment.Center)
                    .size(64.dp)
            )

            is DashboardState.Success -> DashboardContent(
                dashboardViewModel::onFavoritesClick,
                dashboardViewModel::onGamesClick,
                dashboardViewModel::onSettingsClick,
                dashboardViewModel::onAboutClick,
                dashboardViewModel::onLearnClick,
                dashboardViewModel::onOpenWord,
                dashboardViewModel::onUpdateFavorite,
                { dashboardViewModel.onNextRandomWord(s) },
                insets,
                { s }
            )
        }

        Box(
            Modifier.fillMaxWidth().align(Alignment.BottomCenter)
        ) {
            adMob.Banner(
                Modifier.fillMaxWidth()
                    .padding(insets())
            )
        }
    }
}

@Composable
internal fun ErrorDashboardContent(
    onTryAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text("Error while loading", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))
        BaseTextButton({ "Try Again" }, onTryAgain)
    }
}

@Composable
internal fun DashboardContent(
    onFavoritesClick: () -> Unit,
    onGamesClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit,
    onLearnClick: (WordCombinedUi) -> Unit,
    onClick: (WordCombinedUi) -> Unit,
    onUpdateFavoriteClick: (WordCombinedUi) -> Unit,
    onUpdateRandomWord: () -> Unit,
    getInsets: () -> PaddingValues,
    getState: () -> DashboardState.Success,
) {
    val insets = getInsets()
    val contentPadding = remember(insets) {
        PaddingValues(
            start = insets.calculateLeftPadding(LayoutDirection.Ltr) + 24.dp,
            end = insets.calculateRightPadding(LayoutDirection.Ltr) + 24.dp,
            top = insets.calculateTopPadding() + 16.dp,
            bottom = 80.dp
        )
    }

    val columns = remember { GridCells.Fixed(5) }

    val state = getState()
    val showSearchItems by remember(state) {
        derivedStateOf { state.query.text.isNotBlank() }
    }

    LazyVerticalGrid(
        columns,
        modifier = fillMaxWidthModifier.imePadding(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (!showSearchItems) {
            if (false) studyingItems(state)

            vocabulary(
                onLearnClick,
                onClick,
                onUpdateFavoriteClick,
                onUpdateRandomWord,
                state.wordOfTheDay,
                state.randomWord
            )


            item(span = { GridItemSpan(5) }, contentType = { "menu_item" }) {
                DashboardMenuItem(
                    "[F]avorites",
                    fillMaxWidthModifier.clickable(onClick = onFavoritesClick)
                )
            }

            item(span = { GridItemSpan(5) }, contentType = { "menu_item" }) {
                DashboardMenuItem(
                    "[G]ames",
                    fillMaxWidthModifier.clickable(onClick = onGamesClick),
                    badgeEnable = true
                )
            }
            item(span = { GridItemSpan(5) }, contentType = { "menu_item" }) {
                DashboardMenuItem(
                    "[S]ettings",
                    fillMaxWidthModifier.clickable(onClick = onSettingsClick).testTag("setting")
                )
            }
        } else {
            items(
                state.foundWords.item ?: listOf(),
                span = { GridItemSpan(5) },
                contentType = { "search_word_card" }) {
                SearchWordCard(
                    onClick,
                    onLearnClick,
                    onUpdateFavoriteClick,
                    {},
                    { it },
                    fillMaxWidthModifier.testDesc("search_card")
                )
            }
        }
    }
}

internal fun LazyGridScope.studyingItems(state: DashboardState.Success) {
    item(span = { GridItemSpan(5) }) {
        DashboardSubtitle("Learning status", fillMaxWidthModifier)
    }

    item(span = { GridItemSpan(2) }) {
        StatusCard({}, { state.learnedWordsStatus }, fillMaxWidthModifier)
    }
    item(span = { GridItemSpan(3) }) {
        StatusCard({}, { state.practiceWordsStatus }, fillMaxWidthModifier)
    }
    item(span = { GridItemSpan(3) }) {
        PrimaryStatusCard(
            {},
            { state.newWordsStatus.title },
            { state.newWordsStatus.status.toString() },
            { state.newWordsStatus.description },
            fillMaxWidthModifier
        )
    }
    item(span = { GridItemSpan(2) }) {
        StatusCard({}, { state.streakDaysStatus }, fillMaxWidthModifier)
    }
    item(span = { GridItemSpan(5) }) {
        Button({},
            modifier = Modifier.semantics {
                contentDescription = "continue_learning"
            }
        ) {
            Icon(
                MyIconPack.IcRocketLaunchOutline,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )

            Text(
                "Continue learning",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
            )
        }
    }
}

internal fun LazyGridScope.vocabulary(
    onLearnClick: (WordCombinedUi) -> Unit,
    onClick: (WordCombinedUi) -> Unit,
    onUpdateFavoriteClick: (WordCombinedUi) -> Unit,
    onUpdateRandomWord: () -> Unit,
    wordOfTheDay: ContentState<Pair<WordUi, WordCombinedUi>>,
    randomWord: ContentState<Pair<WordUi, WordCombinedUi>>,
) {
    item(span = { GridItemSpan(5) }) {
        DashboardSubtitle("Vocabulary", fillMaxWidthModifier)
    }

    if (wordOfTheDay !is ContentState.Error<*, *>) {
        item(span = { GridItemSpan(5) }) {
            WordOfTheDayCard(
                onClick,
                onUpdateFavoriteClick,
                onLearnClick,
                { wordOfTheDay },
                fillMaxWidthModifier.testDesc("word_of_the_day_card")
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
            fillMaxWidthModifier.testDesc("random_word_card")
        )
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
    color: Color = MaterialTheme.colorScheme.onBackground,
    badgeEnable: Boolean = false
) {
    Box(Modifier.clip(RoundedCornerShape(12.dp))) {
        Box(modifier) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = color,
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
            )

            if (badgeEnable) {
                Badge(Modifier.align(Alignment.CenterEnd).size(12.dp))
            }
        }
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
            onClick = { onClick(w.data.second) },
            modifier = modifier
        ) {
            Spacer(Modifier.height(24.dp))
            Text(
                "Word of the Day",
                style = MaterialTheme.typography.titleMedium,
                modifier = fillMaxWidthModifierHorizontalPadding,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            WordLargeResizableTitle(
                w.data.first.word,
                fillMaxWidthModifierHorizontalPadding,
                TextAlign.Start
            )
            Column(
                Modifier.fillMaxWidth().padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                w.data.first.senses.take(2).fastForEach { sense ->
                    BaseCard(
                        Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ) {
                        Sense(
                            sense.gloss,
                            Modifier.fillMaxWidth()
                                .padding(24.dp)
                        )
                    }
                }
            }
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
