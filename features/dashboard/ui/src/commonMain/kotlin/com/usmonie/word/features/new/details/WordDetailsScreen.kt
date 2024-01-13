package com.usmonie.word.features.new.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.GetSimilarWordsUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCaseImpl
import com.usmonie.word.features.new.components.DetailsWordCardMedium
import com.usmonie.word.features.new.components.FormsCard
import com.usmonie.word.features.new.components.FormsCardState
import com.usmonie.word.features.new.components.RelatedCard
import com.usmonie.word.features.new.components.RelatedCardState
import com.usmonie.word.features.new.components.SenseTreeCard
import com.usmonie.word.features.new.models.Forms
import com.usmonie.word.features.new.models.WordCombinedUi
import com.usmonie.word.features.ui.AdMob
import com.usmonie.word.features.ui.BaseLazyColumn
import com.usmonie.word.features.ui.MenuItemText
import com.usmonie.word.features.ui.SearchBar
import com.usmonie.word.features.ui.TopBackButtonBar
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
                    GetSimilarWordsUseCaseImpl(wordRepository),
                    analytics,
                ),
                adMob
            )
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun WordDetailsContent(wordViewModel: WordViewModel, adMob: AdMob) {
    val routeManager = LocalRouteManager.current
    val state by wordViewModel.state.collectAsState()
    val effect by wordViewModel.effect.collectAsState(null)

    val (selectedEtymologyTabIndex, onSelectedTab) = remember(state.word) { mutableStateOf(0) }
    val selectedEtymology = state.word.wordEtymology[selectedEtymologyTabIndex]
    val (selectedPosIndex, onSelectedPos) = remember(
        state.word,
        selectedEtymologyTabIndex
    ) { mutableStateOf(0) }
    val selectedPos =
        remember(selectedEtymology, selectedPosIndex) { selectedEtymology.words[selectedPosIndex] }

    var sensesExpanded by remember(selectedPos) { mutableStateOf(false) }
    WordEffect(effect)

    val listState = rememberLazyListState()
    Scaffold(
        topBar = { TopBackButtonBar(routeManager::navigateBack, true) },
    ) {
        Box(Modifier.padding(it)) {
            BaseLazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                listState = listState
            ) {
                item {
                    SearchBar(
                        {},
                        {},
                        "[D]etails",
                        "",
                        false,
                        enabled = false
                    )
                }

                if (state.word.wordEtymology.size > 1) {
                    stickyHeader {
                        ScrollableTabRow(
                            selectedEtymologyTabIndex,
                            modifier = Modifier.fillMaxWidth(),
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ) {
                            state.word.wordEtymology.fastForEachIndexed { index, _ ->
                                Tab(
                                    selected = selectedEtymologyTabIndex == index,
                                    onClick = remember { { onSelectedTab(index) } },
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
                    stickyHeader {
                        ScrollableTabRow(
                            selectedPosIndex,
                            modifier = Modifier.fillMaxWidth(),
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ) {
                            selectedEtymology.words.fastForEachIndexed { index, word ->
                                Tab(
                                    selected = selectedPosIndex == index,
                                    onClick = remember { { onSelectedPos(index) } },
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
                        MenuItemText("Senses", Modifier.weight(1f))
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
                        TextButton({ sensesExpanded = !sensesExpanded }) {
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
                            MenuItemText("Thesaurus", Modifier.weight(1f))
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