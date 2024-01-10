package com.usmonie.word.features.new.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.new.models.ExampleUi
import com.usmonie.word.features.new.models.FormUi
import com.usmonie.word.features.new.models.SenseCombinedUi
import com.usmonie.word.features.ui.BaseCard
import wtf.word.core.domain.tools.fastForEach
import wtf.word.core.domain.tools.fastForEachIndexed

@Suppress("NonSkippableComposable")
@Composable
fun SenseCard(
    sense: SenseCombinedUi,
    word: String,
    forms: List<FormUi>,
    modifier: Modifier = Modifier,
    elevation: Dp = 2.dp
) {
    BaseCard({}, elevation, modifier) {
        Spacer(Modifier.height(20.dp))
        SenseTreeItem(sense, word, forms)
        Spacer(Modifier.height(20.dp))
    }
}

@Suppress("NonSkippableComposable")
@Composable
fun SenseTreeCard(
    sense: SenseCombinedUi,
    word: String,
    forms: List<FormUi>,
    modifier: Modifier = Modifier,
    elevation: Dp = 2.dp
) {
    BaseCard({}, elevation, modifier) {
        Spacer(Modifier.height(20.dp))
        SenseTreeItem(sense, word, forms)
        Spacer(Modifier.height(20.dp))
    }
}

@Suppress("NonSkippableComposable")
@Composable
private fun SenseTreeItem(sense: SenseCombinedUi, word: String, forms: List<FormUi>) {
    Sense(sense.gloss, modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp))

    sense.examples.forEach { example ->
        Spacer(Modifier.height(8.dp))
        ExampleItem(example, word, forms)
    }

    if (sense.children.isNotEmpty()) {
        Spacer(Modifier.height(8.dp))
        Divider(
            Modifier.fillMaxWidth(),
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))

        sense.children.fastForEachIndexed { index, senseCombined ->
            if (index > 0) {
                Divider(
                    Modifier.fillMaxWidth(),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                Spacer(Modifier.height(8.dp))
            }

            SenseTreeItem(senseCombined, word, forms)
        }
    }
}

@Composable
fun Sense(
    sense: String,
    modifier: Modifier = Modifier,
) {
    Sense(sense, modifier = modifier, true)
}

@Composable
fun Sense(
    sense: String,
    modifier: Modifier = Modifier,
    expanded: Boolean
) {
    val maxLines by remember(expanded) { mutableStateOf(if (expanded) Int.MAX_VALUE else 6) }
    Sense(sense, modifier = modifier, maxLines = maxLines)
}

@Composable
fun Sense(gloss: String, modifier: Modifier = Modifier, maxLines: Int) {
    SelectionContainer {
        Text(
            text = gloss,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = modifier,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun SenseNumber(position: Int) {
    Text(
        "${position + 1}.",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Justify
    )
}

@Suppress("NonSkippableComposable")
@Composable
fun ExampleItem(
    example: ExampleUi,
    word: String,
    forms: List<FormUi>,
    modifier: Modifier = Modifier
) {
    val bodyMedium = MaterialTheme.typography.bodyMedium
    val bodyLarge = MaterialTheme.typography.bodyLarge
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariantColor = MaterialTheme.colorScheme.onSurfaceVariant
    println("FORM: $forms")

    val exampleText = example.text ?: return
    val text = remember(example) {
        buildAnnotatedString {
            val bodyMediumSpan = bodyMedium.toSpanStyle()
            val bodyLargeSpan = bodyLarge.toSpanStyle()
            withStyle(bodyMediumSpan.copy(onSurfaceVariantColor)) {
                append(exampleText)
            }

            setSpan(exampleText, word, bodyLargeSpan, onSurfaceColor)

            forms.fastForEach {
                it.formText?.let { form ->
                    setSpan(exampleText, form, bodyLargeSpan, onSurfaceColor)
                }
            }
        }
    }

    Column(modifier) {
        Row(
            Modifier.fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(start = 32.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Justify,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.weight(1f).padding(vertical = 8.dp)
            )

            Spacer(Modifier.width(12.dp))
            Divider(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
        }

        if (example.ref != null) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = example.ref,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth().padding(start = 32.dp, end = 20.dp)
            )
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

fun String.chunkedWords(
    limitChars: Int = 100,
    delimiter: Char = ' ',
    joinCharacter: Char = '\n'
) =
    splitToSequence(delimiter)
        .reduce { cumulatedString, word ->
            val exceedsSize =
                cumulatedString.length - cumulatedString.indexOfLast { it == joinCharacter } + "$delimiter$word".length > limitChars
            cumulatedString + if (exceedsSize) {
                joinCharacter
            } else {
                delimiter
            } + word
        }