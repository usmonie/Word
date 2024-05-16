package com.usmonie.word.features.details.ui.mobile

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger
import com.usmonie.core.kit.tools.add
import com.usmonie.word.features.details.ui.word.WordDetailsViewModel
import com.usmonie.word.features.details.ui.word.openPos
import com.usmonie.word.features.dictionary.ui.Pronunciations
import com.usmonie.word.features.dictionary.ui.Word

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
        key = { it },
        verticalAlignment = Alignment.Top
    ) {
        val etymology = remember(state, it) { state.word.wordEtymology[it] }

        val etymologyText = etymology.etymologyText
        Rebugger(
            trackMap = mapOf(
                "viewModel" to viewModel,
                "insets" to insets,
                "etymologiesPagerState" to etymologiesPagerState,
                "state" to state,
                "Alignment.Top" to Alignment.Top,
            ),
        )
        LazyColumn(
            contentPadding = insets.add(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            if (etymologyText != null) {
                item {
                    var expanded by remember(etymologyText) { mutableStateOf(false) }
                    val maxLines by remember(expanded) {
                        mutableStateOf(if (expanded) Int.MAX_VALUE else ETYMOLOGY_MAX_LINES)
                    }

                    Surface(
                        onClick = { expanded = !expanded },
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillParentMaxWidth().animateContentSize()
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

            item {
                Pronunciations(
                    { etymology },
                    Modifier.fillParentMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            items(etymology.words) {
                Surface(
                    { viewModel.openPos(it) },
                    shape = MaterialTheme.shapes.large
                ) {
                    Word(
                        remember(etymology) { { it } },
                        Modifier.fillParentMaxWidth().padding(16.dp),
                    )
                }
            }
        }
    }
}
