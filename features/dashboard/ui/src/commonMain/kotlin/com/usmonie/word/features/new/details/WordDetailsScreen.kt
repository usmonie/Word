package com.usmonie.word.features.new.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.GetSimilarWordsUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCaseImpl
import com.usmonie.word.features.new.components.DetailsWordCardLarge
import com.usmonie.word.features.new.components.SenseCard
import com.usmonie.word.features.new.models.WordCombinedUi
import com.usmonie.word.features.ui.AdMob
import com.usmonie.word.features.ui.BaseDashboardLazyColumn
import com.usmonie.word.features.ui.MenuItemText
import com.usmonie.word.features.ui.SearchBar
import com.usmonie.word.features.ui.TopBackButtonBar
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.speech.core.ui.AdKeys
import wtf.word.core.domain.Analytics
import wtf.word.core.domain.tools.fastForEachIndexed

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
    val selectedPos = selectedEtymology.words[selectedPosIndex]

    WordEffect(effect)

    Scaffold(
        topBar = { TopBackButtonBar(routeManager::navigateBack, true) },
    ) {
        Box {
            BaseDashboardLazyColumn(contentPadding = it) {
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
                                        modifier = Modifier.padding(top = 10.dp, bottom = 12.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    DetailsWordCardLarge(
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
                                        modifier = Modifier.padding(top = 10.dp, bottom = 12.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    MenuItemText("Senses")
                }

                items(selectedPos.senses) {
                    SenseCard(it, modifier = Modifier.padding(horizontal = 20.dp))
                }

                item {
                    Spacer(Modifier.height(80.dp))
                }
            }

            adMob.Banner(
                AdKeys.BANNER_ID,
                Modifier.fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(it)
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