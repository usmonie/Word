package com.usmonie.word.features.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.DetailsWordCardMedium
import com.usmonie.word.features.FormsCard
import com.usmonie.word.features.FormsCardState
import com.usmonie.word.features.RelatedCard
import com.usmonie.word.features.RelatedCardState
import com.usmonie.word.features.SenseTreeCard
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCaseImpl
import com.usmonie.word.features.gradientBackground
import com.usmonie.word.features.models.Forms
import com.usmonie.word.features.models.WordCombinedUi
import com.usmonie.word.features.studying.StudyingCard
import com.usmonie.word.features.ui.AdMob
import com.usmonie.word.features.ui.BaseLazyColumn
import com.usmonie.word.features.ui.SubtitleItemText
import com.usmonie.word.features.ui.TitleBar
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.speech.core.ui.AppKeys
import wtf.word.core.domain.Analytics
import wtf.word.core.domain.tools.fastForEachIndexed

private const val COLLAPSED_SENSES_COUNT = 3
private const val MINIMUM_SENSES_TO_COLLAPSE_COUNT = 5

class WordDetailsScreen(
    private val wordViewModel: WordViewModel,
    private val adMob: AdMob
) : Screen(wordViewModel) {
    override val id: String = ID

    @Composable
    override fun Content() {
        WordDetailsContent(wordViewModel, adMob)
    }

    companion object {
        const val ID = "WordDetailsScreen"
        const val KEY = "WordDetailsExtra"

        data class WordExtra(val word: WordCombinedUi) : Extra {
            override val key: String = KEY
        }
    }

    class Builder(
        private val wordRepository: WordRepository,
        private val analytics: Analytics,
        private val adMob: AdMob
    ) : ScreenBuilder {
        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            val wordExtra = requireNotNull(extra) as WordExtra

            return WordDetailsScreen(
                WordViewModel(
                    wordExtra,
                    UpdateFavouriteUseCaseImpl(wordRepository),
                    analytics,
                ),
                adMob
            )
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
private fun WordDetailsContent(wordViewModel: WordViewModel, adMob: AdMob) {
    val routeManager = LocalRouteManager.current
    val state by wordViewModel.state.collectAsState()
    val effect by wordViewModel.effect.collectAsState(null)

    val selectedEtymologyTabIndex = state.selectedEtymologyIndex
    val selectedEtymology =
        state.word.wordEtymology.getOrElse(selectedEtymologyTabIndex) { state.word.wordEtymology.last() }
    val selectedPosIndex = state.selectedPosIndex
    val selectedPos =
        remember(selectedEtymology, selectedPosIndex) { selectedEtymology.words[selectedPosIndex] }

    var sensesExpanded = state.sensesExpanded
    WordEffect(effect)

    val listState = state.listState
    val appBarState = state.appBarState
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appBarState)

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    TitleBar(
                        "[D]etails",
                        MaterialTheme.typography.displayLarge.fontSize
                                * (1 - scrollBehavior.state.collapsedFraction)
                            .coerceIn(0.6f, 1f)
                    )
                },
                navigationIcon = {
                    IconButton(routeManager::navigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = Icons.Default.ArrowBack.name,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color.Transparent),
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        Box(
            Modifier
                .gradientBackground()
                .padding(it)
        ) {
            BaseLazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                listState = listState
            ) {

                if (state.word.wordEtymology.size > 1) {
                    item {
                        ScrollableTabRow(
                            selectedEtymologyTabIndex,
                            modifier = Modifier.fillMaxWidth(),
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ) {
                            state.word.wordEtymology.fastForEachIndexed { index, _ ->
                                Tab(
                                    selected = selectedEtymologyTabIndex == index,
                                    onClick = remember { { wordViewModel.selectEtymology(index) } },
                                ) {
                                    Text(
                                        "Root ${index + 1}",
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    DetailsWordCardMedium(
                        {},
                        {},
                        { wordViewModel.onUpdateFavouritePressed(state.word) },
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        word = selectedPos,
                        bookmarked = state.word.isFavorite
                    )
                }

                if (selectedEtymology.words.size > 1) {
                    item {
                        ScrollableTabRow(
                            selectedPosIndex,
                            modifier = Modifier.fillMaxWidth(),
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ) {
                            selectedEtymology.words.fastForEachIndexed { index, word ->
                                Tab(
                                    selected = selectedPosIndex == index,
                                    onClick = remember { { wordViewModel.selectPos(index) } },
                                ) {
                                    Text(
                                        word.pos,
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                if (selectedPos.forms.isNotEmpty()) {
                    item {
                        FormsCard(FormsCardState(selectedPos.forms))
                    }
                }

                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        SubtitleItemText("Senses", Modifier.weight(1f))
                        if (selectedPos.senses.size > MINIMUM_SENSES_TO_COLLAPSE_COUNT) {
                            TextButton({ sensesExpanded = !sensesExpanded }) {
                                Text(
                                    text = if (sensesExpanded) "Collapse" else "Expand",
                                    modifier = Modifier.padding(
                                        vertical = 10.dp,
                                        horizontal = 20.dp
                                    ),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }

                item {
                    var revealed by remember {
                        mutableStateOf(false)
                    }
                    StudyingCard(
                        { revealed = !revealed},
                        revealed = revealed,
                        selectedPos,
                        "ru",
                        Modifier.fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    )
                }

                items(selectedPos.senses.take(if (sensesExpanded) Int.MAX_VALUE else COLLAPSED_SENSES_COUNT)) { sense ->
                    SenseTreeCard(
                        sense,
                        state.word.word,
                        Forms(selectedPos.forms),
                        modifier = Modifier.padding(horizontal = 20.dp).animateItemPlacement()
                    )
                }

                if (selectedPos.senses.size > MINIMUM_SENSES_TO_COLLAPSE_COUNT) {
                    item {
                        TextButton(wordViewModel::onSenseExpand) {
                            Text(
                                text = if (sensesExpanded) "Collapse Senses" else "Expand Senses",
                                modifier = Modifier.fillMaxWidth().padding(
                                    vertical = 10.dp,
                                    horizontal = 20.dp
                                ),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }

                if (selectedPos.thesaurusAvailable) {
                    item {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            SubtitleItemText("Thesaurus", Modifier.weight(1f))
                        }
                    }
                    items(selectedPos.thesaurus) { item ->
                        val relatedCardState =
                            remember(selectedPos) { RelatedCardState(item.second) }
                        RelatedCard(item.first, relatedCardState)
                    }
                }

                item {
                    Spacer(Modifier.height(80.dp))
                }
            }

            adMob.Banner(
                AppKeys.BANNER_ID,
                Modifier.fillMaxWidth()
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun WordEffect(effect: WordEffect?) {
    val routeManager = LocalRouteManager.current


    LaunchedEffect(effect) {
        when (effect) {
            is WordEffect.OpenWord -> routeManager.navigateTo(
                WordDetailsScreen.ID,
                extras = WordDetailsScreen.Companion.WordExtra(effect.word)
            )

            null -> Unit
        }
    }
}