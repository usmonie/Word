package com.usmonie.word.features.learning.ui.word

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.ui.ui.Sense
import com.usmonie.word.features.dashboard.ui.models.WordCombinedUi
import com.usmonie.word.features.dashboard.ui.ui.WordMediumResizableTitle
import com.usmonie.word.features.learning.ui.models.Exercise
import wtf.speech.core.ui.BaseCard
import wtf.word.core.domain.tools.fastForEach

@Composable
fun NewWordPage(
    onContinueClick: () -> Unit,
    onWordDetailsClick: (WordCombinedUi) -> Unit,
    newWord: Exercise.NewWord,
    modifier: Modifier
) {
    Box(modifier) {
        LearnWordCard(onWordDetailsClick, { newWord }, Modifier)
    }
}

@Composable
fun LearnWordCard(
    onCardClick: (WordCombinedUi) -> Unit,
    getWord: () -> Exercise.NewWord,
    modifier: Modifier = Modifier
) {
    val exercise = getWord()

    BaseCard(onClick = { onCardClick(exercise.wordCombined) }, modifier = modifier) {
        Spacer(Modifier.height(32.dp))
        WordMediumResizableTitle(
            exercise.wordCombined.word,
            Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = exercise.pronunciation.ipa
                ?: throw NullPointerException("Word ${exercise.wordCombined.word} has no IPA"),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp)
        )

        Text(
            text = exercise.translation.word
                ?: throw NullPointerException("Word ${exercise.wordCombined.word} has no TRANSLATION ${exercise.translation.lang}"),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        )

        Sense(exercise.sense.gloss, Modifier.padding(horizontal = 24.dp))

        exercise.example.fastForEach {

        }
        Spacer(Modifier.height(24.dp))
    }
}