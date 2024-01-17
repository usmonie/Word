package com.usmonie.word.features.studying

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.ExampleItem
import com.usmonie.word.features.models.ExampleUi
import com.usmonie.word.features.models.Forms
import com.usmonie.word.features.models.WordUi
import com.usmonie.word.features.ui.BaseCard
import com.usmonie.word.features.ui.CardFace
import com.usmonie.word.features.ui.FlipBaseCard
import com.usmonie.word.features.ui.WordLargeResizableTitle

@Composable
fun StudyingCard(
    onClick: (WordUi) -> Unit,
    revealed: Boolean,
    word: WordUi,
    userLanguageCode: String,
    modifier: Modifier = Modifier
) {
    FlipBaseCard(
        if (revealed) CardFace.Back else CardFace.Front,
        { onClick(word) },
        modifier,
        back = {
            StudyingRevealedCard(onClick, word, userLanguageCode)
        },
        front = {
            StudyingCoveredCard(onClick, word, userLanguageCode)
        }
    )
}

@Composable
fun StudyingRevealedCard(
    onClick: (WordUi) -> Unit,
    word: WordUi,
    userLanguageCode: String,
    modifier: Modifier = Modifier
) {
    val pronunciation = remember(word) {
        val sound = word.sounds.getOrNull(0)

        sound?.ipa ?: sound?.enpr
    }
    val translation = remember(word, userLanguageCode) {
        word.translations.asSequence()
            .filter { translation -> translation.code == userLanguageCode }
            .map { translation -> translation.word }
            .filterNotNull()
            .joinToString { word -> word }
    }
    val sense = remember(word) {
        word.senses[0]
    }
    val example = remember(word, sense) {
        sense.examples.getOrNull(0)
    }

    BaseCard({ onClick(word) }, modifier = modifier) {
        Spacer(Modifier.height(24.dp))
        WordLargeResizableTitle(
            word.word,
            Modifier.fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        if (pronunciation != null) {
            Spacer(Modifier.height(8.dp))
            PronunciationLabel(
                pronunciation,
                Modifier.fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }
        TranslationText(
            translation,
            Modifier.fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(Modifier.height(20.dp))
        DescriptionText(
            sense.gloss,
            Modifier.fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        example?.let { example ->
            Spacer(Modifier.height(12.dp))
            Example(
                example,
                word.word,
                Forms(word.forms),
                Modifier.fillMaxWidth()
                    .padding(start = 36.dp)
            )
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun StudyingCoveredCard(
    onClick: (WordUi) -> Unit,
    word: WordUi,
    userLanguageCode: String,
    modifier: Modifier = Modifier
) {
    val pronunciation = remember(word) {
        val sound = word.sounds.getOrNull(0)

        sound?.ipa ?: sound?.enpr
    }

    BaseCard({ onClick(word) }, modifier = modifier) {
        Spacer(Modifier.height(24.dp))
        WordLargeResizableTitle(
            word.word,
            Modifier.fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        if (pronunciation != null) {
            Spacer(Modifier.height(8.dp))
            PronunciationLabel(
                pronunciation,
                Modifier.fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun PronunciationLabel(pronunciation: String, modifier: Modifier = Modifier) {
    Text(
        pronunciation,
        modifier,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelLarge
    )
}

@Composable
fun TranslationText(translation: String, modifier: Modifier = Modifier) {
    Text(
        translation,
        modifier,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
fun DescriptionText(description: String, modifier: Modifier = Modifier) {
    Text(
        description,
        modifier,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun Example(example: ExampleUi, word: String, forms: Forms, modifier: Modifier = Modifier) {
    ExampleItem(example, word, forms, modifier)
}