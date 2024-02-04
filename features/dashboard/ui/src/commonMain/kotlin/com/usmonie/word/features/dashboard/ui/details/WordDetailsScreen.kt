package com.usmonie.word.features.dashboard.ui.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.ui.DetailsWordCardMedium
import com.usmonie.word.features.dashboard.ui.FormsCard
import com.usmonie.word.features.dashboard.ui.RelatedCard
import com.usmonie.word.features.dashboard.ui.RelatedCardState
import com.usmonie.word.features.dashboard.ui.SenseTreeCard
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCaseImpl
import com.usmonie.word.features.dashboard.ui.models.Forms
import com.usmonie.word.features.dashboard.ui.models.WordCombinedUi
import com.usmonie.word.features.dashboard.ui.models.WordUi
import com.usmonie.word.features.dashboard.ui.ui.AdMob
import com.usmonie.word.features.dashboard.ui.ui.BaseLazyColumn
import com.usmonie.word.features.dashboard.ui.ui.SubtitleItemText
import com.usmonie.word.features.dashboard.ui.ui.WordTopBar
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current
        val state by wordViewModel.state.collectAsState()
        val appBarState = state.appBarState
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appBarState)

        Scaffold(
            topBar = {
                WordTopBar(
                    routeManager::navigateBack,
                    "[D]etails",
                    remember { { true } }
                ) { scrollBehavior }
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { insets ->

            val effect by wordViewModel.effect.collectAsState(null)

            WordEffect(effect)

            WordDetailsContent(
                insets,
                { state.listState },
                { state.word },
                { state.selectedEtymologyIndex },
                wordViewModel,
                { state.selectedPosIndex },
                { state.sensesExpanded },
                wordViewModel::selectEtymology,
                wordViewModel::selectPos,
                wordViewModel::onSenseExpand,
                adMob
            )
        }
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
@OptIn(ExperimentalFoundationApi::class)
private fun WordDetailsContent(
    insets: PaddingValues,
    listState: () -> LazyListState,
    getWordCombined: () -> WordCombinedUi,
    getSelectedEtymologyTabIndex: () -> Int,
    wordViewModel: WordViewModel,
    getSelectedPosIndex: () -> Int,
    getSensesExpanded: () -> Boolean,
    onEtymologySelected: (Int) -> Unit,
    onPosSelected: (Int) -> Unit,
    onExpandSenses: () -> Unit,
    adMob: AdMob
) {
    val sensesExpanded = getSensesExpanded()
    val selectedPosIndex = getSelectedPosIndex()
    val selectedEtymologyIndex = getSelectedEtymologyTabIndex()
    val wordCombined = getWordCombined()

    val selectedEtymology = remember(selectedEtymologyIndex) {
        wordCombined.wordEtymology.getOrElse(selectedEtymologyIndex) {
            wordCombined.wordEtymology.last()
        }
    }

    val selectedPos = remember(selectedEtymology, selectedPosIndex) {
        selectedEtymology.words.getOrElse(selectedPosIndex) { selectedEtymology.words.last() }
    }

    val sensesCollapsed = remember(selectedPos, sensesExpanded) {
        selectedPos.senses
            .take(if (sensesExpanded) Int.MAX_VALUE else COLLAPSED_SENSES_COUNT)
            .toMutableList()
    }

    Box(Modifier.padding(insets)) {
        BaseLazyColumn(
            contentPadding = PaddingValues(bottom = insets.calculateBottomPadding() + 80.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            listState = listState()
        ) {

            if (wordCombined.wordEtymology.size > 1) {
                item {
                    ScrollableTabRow(
                        selectedEtymologyIndex,
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                    ) {
                        wordCombined.wordEtymology.fastForEachIndexed { index, word ->
                            TabItem(
                                { selectedEtymologyIndex == index },
                                remember { { wordViewModel.selectEtymology(index) } },
                                "Root ${index + 1}"
                            )
                        }
                    }
                }
            }

            item {
                DetailsWordCardMedium(
                    remember { {} },
                    remember { {} },
                    remember { { wordViewModel.onUpdateFavouritePressed(wordCombined) } },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    getWord = { selectedPos },
                    getBookmarked = { wordCombined.isFavorite }
                )
            }

            if (selectedEtymology.words.size > 1) {
                item {
                    ScrollableTabRow(
                        selectedPosIndex,
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    ) {
                        selectedEtymology.words.fastForEachIndexed { index, word ->
                            TabItem(
                                { selectedPosIndex == index },
                                remember { { wordViewModel.selectPos(index) } },
                                word.pos
                            )
                        }
                    }
                }
            }

            if (selectedPos.forms.isNotEmpty()) {
                item {
                    FormsCard { selectedPos.forms }
                }
            }

            item {
                SensesTitle(
                    selectedPos,
                    onExpandSenses,
                    getSensesExpanded
                ) { selectedPos.senses.size > MINIMUM_SENSES_TO_COLLAPSE_COUNT }
            }

            items(sensesCollapsed) { sense ->
                SenseTreeCard(
                    { sense },
                    { wordCombined.word },
                    { Forms(selectedPos.forms) },
                    modifier = Modifier.padding(horizontal = 20.dp).animateItemPlacement()
                )
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
                    RelatedCard({ item.first }, { relatedCardState })
                }
            }
        }

        adMob.Banner(
            AppKeys.BANNER_ID,
            Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun TabItem(
    isSelected: () -> Boolean,
    onClick: () -> Unit,
    title: String,
) {
    Tab(
        selected = isSelected(),
        onClick = onClick,
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
        )
    }
}

@Composable
private fun SensesExpandButton(
    onExpandSenses: () -> Unit,
    sensesExpanded: () -> Boolean
) {
    TextButton(onExpandSenses) {
        Text(
            text = if (sensesExpanded()) "Collapse Senses" else "Expand Senses",
            modifier = Modifier.fillMaxWidth().padding(
                vertical = 10.dp,
                horizontal = 20.dp
            ),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun SensesTitle(
    selectedPos: WordUi,
    onExpandSenses: () -> Unit,
    sensesExpanded: () -> Boolean,
    sensesExpandable: () -> Boolean,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        SubtitleItemText("Senses", Modifier.weight(1f))
        if (sensesExpandable()) {
            TextButton(onExpandSenses) {
                Text(
                    text = if (sensesExpanded()) "Collapse" else "Expand",
                    modifier = Modifier.padding(
                        vertical = 10.dp,
                        horizontal = 20.dp
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium
                )
            }
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