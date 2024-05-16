package com.usmonie.word.features.dictionary.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.usmonie.core.domain.tools.fastForEach
import com.usmonie.core.kit.composables.base.buttons.TextButton
import com.usmonie.word.features.dictionary.ui.models.SenseCombinedUi
import com.usmonie.word.features.dictionary.ui.models.WordUi
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.dictionary.ui.generated.resources.Res
import word.shared.feature.dictionary.ui.generated.resources.vocabulary_word_senses_collapse_examples
import word.shared.feature.dictionary.ui.generated.resources.vocabulary_word_senses_expand_examples
import word.shared.feature.dictionary.ui.generated.resources.vocabulary_word_thesaurus_antonyms
import word.shared.feature.dictionary.ui.generated.resources.vocabulary_word_thesaurus_synonyms

fun LazyListScope.words(words: List<WordUi>) {
    words.fastForEach {
        item {
            Column(
                Modifier.fillParentMaxWidth().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PartOfSpeech { it }
                MeaningsTitle()
            }
        }

        meanings({ it }, true)

        if (it.synonyms.isNotEmpty()) {
            item {
                ThesaurusItem(stringResource(Res.string.vocabulary_word_thesaurus_synonyms))
            }
        }

        if (it.antonyms.isNotEmpty()) {
            item {
                ThesaurusItem(stringResource(Res.string.vocabulary_word_thesaurus_antonyms))
            }
        }

        item {} // Adds space between parts of speech
    }
}

fun LazyListScope.meanings(getWord: () -> WordUi, showExamples: Boolean) {
    val word = getWord()

    word.senses.fastForEach { sense ->
        meaning(sense, getWord, showExamples, 16.dp)
    }
}

fun LazyListScope.meaning(
    senseCombined: SenseCombinedUi,
    getWord: () -> WordUi,
    showDetails: Boolean,
    startPadding: Dp = 0.dp
) {
    val examples = senseCombined.examples

    val examplesExpandable = examples.size > 1 && showDetails
    var examplesExpanded by mutableStateOf(examples.size < 2)
    item {
        Row(
            Modifier.fillMaxWidth().padding(start = startPadding),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.size(4.dp)
                    .background(MaterialTheme.colorScheme.onBackground, CircleShape)
            )
            Text(senseCombined.gloss, style = MaterialTheme.typography.bodyLarge)
        }
    }

    if (showDetails) {
        items(examples.let { if (examplesExpanded) it else it.take(2) }) {
            Quote(remember(senseCombined) { { it } }, getWord, Modifier.padding(start = startPadding + 20.dp))
        }
    }

    if (examplesExpandable) {
        item {
            TextButton(
                stringResource(
                    if (examplesExpanded) {
                        Res.string.vocabulary_word_senses_collapse_examples
                    } else {
                        Res.string.vocabulary_word_senses_expand_examples
                    }
                ),
                { examplesExpanded = !examplesExpanded },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.padding(start = startPadding + 18.dp)
            )
        }
    }

    if (showDetails) {
        senseCombined.children.take(2).fastForEach {
            meaning(it, getWord, false, startPadding + 20.dp)
        }
    }
}

@Composable
internal fun ThesaurusItem(title: String, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}
