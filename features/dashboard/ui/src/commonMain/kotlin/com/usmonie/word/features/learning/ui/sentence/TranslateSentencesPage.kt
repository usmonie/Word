package com.usmonie.word.features.learning.ui.sentence

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.ui.ui.AdMob
import com.usmonie.word.features.learning.ui.LetterButtons
import com.usmonie.word.features.learning.ui.models.Exercise

@Composable
fun TranslateSentencePage(
    onInputLetter: (Char) -> Unit,
    onBackspace: () -> Unit,
    onContinueClick: () -> Unit,
    translateSentence: Exercise.TranslateSentence,
    answer: () -> String,
    adMob: AdMob,
    modifier: Modifier
) {
    Column(modifier, verticalArrangement = Arrangement.Bottom) {
        Text(
            text = translateSentence.sentence,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .weight(1f)
        )

        Text(
            text = answer(),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        )

        LetterButtons(onInputLetter, onBackspace, Modifier.fillMaxWidth())

        Spacer(Modifier.height(24.dp))

        Button(
            onContinueClick,
            Modifier
                .height(56.dp)
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                "Continue",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}