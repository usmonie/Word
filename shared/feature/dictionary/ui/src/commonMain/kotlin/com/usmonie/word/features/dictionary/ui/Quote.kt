package com.usmonie.word.features.dictionary.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.usmonie.core.domain.tools.fastForEach
import com.usmonie.word.features.dictionary.ui.models.ExampleUi
import com.usmonie.word.features.dictionary.ui.models.WordUi

@Composable
fun Quote(
    getExample: () -> ExampleUi,
    getWord: () -> WordUi,
    modifier: Modifier = Modifier
) {
    val example by derivedStateOf(getExample)
    val word by derivedStateOf(getWord)
    val forms by derivedStateOf { word.forms }
    val bodyMedium = MaterialTheme.typography.bodyMedium
    val bodyLarge = MaterialTheme.typography.bodyLarge
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariantColor = MaterialTheme.colorScheme.onSurfaceVariant
    val exampleText = example.text ?: return

    val text = remember(example) {
        buildAnnotatedString {
            val bodyMediumSpan = bodyMedium.toSpanStyle()
            val bodyLargeSpan = bodyLarge.toSpanStyle()
            withStyle(bodyMediumSpan.copy(onSurfaceVariantColor)) {
                append(exampleText)
            }

            setSpan(exampleText, word.word, bodyLargeSpan, onSurfaceColor)

            forms.fastForEach {
                it.formText?.let { form ->
                    setSpan(exampleText, form, bodyLargeSpan, onSurfaceColor)
                }
            }
        }
    }

    Quote(text, example.ref, modifier)
}

@Composable
fun Quote(quote: AnnotatedString, ref: String?, modifier: Modifier = Modifier) {
    SelectionContainer {
        Column(modifier) {
            Text(quote, style = MaterialTheme.typography.bodyMedium)
            ref?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
        }
    }
}

private fun AnnotatedString.Builder.setSpan(
    exampleText: String,
    word: String,
    bodyMediumSpan: SpanStyle,
    onSurfaceColor: Color
) {
    val start = exampleText.indexOf(word, ignoreCase = true)
    if (start > -1) {
        val end = start + word.length
        addStyle(bodyMediumSpan.copy(onSurfaceColor), start, end)
    }
}
