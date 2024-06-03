package com.usmonie.word.features.details.ui.mobile

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.usmonie.core.kit.tools.add
import com.usmonie.word.features.details.ui.word.WordDetailsViewModel
import com.usmonie.word.features.details.ui.word.openPos
import com.usmonie.word.features.dictionary.ui.Pronunciations
import com.usmonie.word.features.dictionary.ui.Word
import com.usmonie.word.features.dictionary.ui.WordDetailed

private const val ETYMOLOGY_MAX_LINES = 2

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun WordDetailsContent(
    viewModel: WordDetailsViewModel,
    insets: PaddingValues,
    etymologiesPagerState: PagerState
) {
    val state by viewModel.state.collectAsState()

    HorizontalPager(
        etymologiesPagerState,
        userScrollEnabled = state.word.wordEtymology.size > 1,
        verticalAlignment = Alignment.Top,
    ) {
        val etymology = state.word.wordEtymology[it]
        val etymologyText = etymology.etymologyText

        var expanded by remember(etymology) { mutableStateOf(false) }
        val maxLines = remember(expanded, etymology) {
            if (expanded) Int.MAX_VALUE else ETYMOLOGY_MAX_LINES
        }

        LazyColumn(
            contentPadding = insets.add(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            if (etymologyText != null) {
                item {
                    Surface(
                        onClick = { expanded = !expanded },
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillParentMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                            .animateContentSize()
                    ) {
                        Text(
                            etymologyText,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = maxLines,
                            textAlign = TextAlign.Justify,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(16.dp)
                                .animateContentSize()
                        )
                    }
                }
            }

            if (etymology.sounds.isNotEmpty()) {
                item {
                    Pronunciations(
                        { etymology },
                        Modifier.fillParentMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }

            if (etymology.words.size > 1) {
                items(etymology.words) {
                    Surface(
                        { viewModel.openPos(it) },
                        shape = MaterialTheme.shapes.large
                    ) {
                        Word(
                            remember(etymology) { { it } },
                            Modifier.fillParentMaxWidth().padding(vertical = 16.dp),
                        )
                    }
                }
            } else {
                val word = etymology.words.last()

                item {
                    WordDetailed(
                        { word },
                        Modifier.fillParentMaxWidth().padding(horizontal = 16.dp),
                    )
                }

                items(word.thesaurusFlatted) {
                    Column(
                        Modifier.fillParentMaxWidth().padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            it.first,
                            style = MaterialTheme.typography.titleSmall,
                        )

                        Text(it.second, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}
