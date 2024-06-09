package com.usmonie.word.features.games.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.usmonie.core.domain.tools.fastForEach
import com.usmonie.word.features.games.ui.models.SenseCombinedUi
import com.usmonie.word.features.games.ui.models.WordEtymologyUi
import com.usmonie.word.features.games.ui.models.WordUi
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.dictionary.ui.generated.resources.Res
import word.shared.feature.dictionary.ui.generated.resources.vocabulary_word_senses
import word.shared.feature.dictionary.ui.generated.resources.vocabulary_word_thesaurus

val modifier = Modifier.fillMaxWidth()

@Composable
fun ColumnScope.Words(getEtymology: () -> WordEtymologyUi) {
    val etymology by derivedStateOf(getEtymology)

    etymology.words
        .take(2)
        .fastForEach {
            Word(remember(etymology) { { it.copy(senses = it.senses.take(2)) } })
        }
}

@Composable
fun Word(
    getWord: () -> WordUi,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PartOfSpeech(getWord, Modifier.fillMaxWidth().padding(horizontal = 16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            MeaningsTitle(
                Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Meaning(getWord, false)
        }

        ThesaurusItem(
            stringResource(Res.string.vocabulary_word_thesaurus),
            Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun WordDetailed(
    getWord: () -> WordUi,
    modifier: Modifier = Modifier,
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        PartOfSpeech(getWord)
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            MeaningsTitle()
            Meaning(getWord, true)
        }
    }
}

@Composable
internal fun MeaningsTitle(modifier: Modifier = Modifier) {
    Text(
        stringResource(Res.string.vocabulary_word_senses),
        style = MaterialTheme.typography.titleSmall,
        modifier = modifier
    )
}

@Composable
private fun Meaning(
    getWord: () -> WordUi,
    isDetailed: Boolean
) {
    val word by derivedStateOf(getWord)
    word.senses.fastForEach {
        if (isDetailed) {
            MeaningTreeDetailed(remember(word) { { it } }, getWord, modifier)
        } else {
            MeaningTree(remember(word) { { it } }, modifier)
        }
    }
}

@Composable
fun MeaningTreeDetailed(
    getSenseCombined: () -> SenseCombinedUi,
    getWord: () -> WordUi,
    modifier: Modifier = Modifier,
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        val senseCombined by derivedStateOf(getSenseCombined)
        val examples = senseCombined.examples
        val examplesExpandable = remember(senseCombined) {
            senseCombined.children.isNotEmpty() || examples.size > 1
        }
        var examplesExpanded by remember(senseCombined) { mutableStateOf(examples.size < 2) }

        MeaningItem(
            Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable(enabled = examplesExpandable) {
                    examplesExpanded = !examplesExpanded
                },
            examplesExpanded,
            examplesExpandable,
            senseCombined
        )

        AnimatedVisibility(examples.isNotEmpty() && (!examplesExpandable || examplesExpanded)) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                examples.fastForEach {
                    Quote(
                        remember(senseCombined) { { it } },
                        getWord,
                        Modifier.padding(start = 20.dp)
                    )
                }
            }
        }

        AnimatedVisibility(senseCombined.children.isNotEmpty() && examplesExpanded) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                senseCombined.children.fastForEach {
                    MeaningTreeDetailed(
                        remember(senseCombined) { { it } },
                        getWord,
                        Modifier.padding(start = 20.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun MeaningItem(
    modifier: Modifier,
    examplesExpanded: Boolean,
    examplesExpandable: Boolean,
    senseCombined: SenseCombinedUi
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(4.dp)
                .background(LocalContentColor.current, CircleShape)
        )
        Text(
            senseCombined.gloss,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        if (examplesExpandable) {
            val rotation by animateFloatAsState(if (examplesExpanded) 0f else DROP_UP_ICON_ROTATION)
            Icon(
                Icons.Rounded.KeyboardArrowUp,
                contentDescription = null,
                modifier = Modifier.graphicsLayer {
                    rotationZ = rotation
                },
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
fun MeaningTree(
    getSenseCombined: () -> SenseCombinedUi,
    modifier: Modifier = Modifier,
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        val senseCombined by derivedStateOf(getSenseCombined)
        MeaningItem(
            modifierWithPaddings,
            examplesExpanded = false,
            examplesExpandable = false,
            senseCombined = senseCombined
        )
    }
}

private const val DROP_UP_ICON_ROTATION = -180F
