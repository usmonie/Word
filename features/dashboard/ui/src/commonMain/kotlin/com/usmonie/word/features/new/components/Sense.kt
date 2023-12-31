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
    forms: List<FormUi>,
    modifier: Modifier = Modifier,
    elevation: Dp = 2.dp
) {
    BaseCard({}, elevation, modifier) {
        Spacer(Modifier.height(20.dp))
        SenseTreeItem(sense, forms)
        Spacer(Modifier.height(20.dp))
    }
}

@Suppress("NonSkippableComposable")
@Composable
fun SenseTreeCard(
    sense: SenseCombinedUi,
    forms: List<FormUi>,
    modifier: Modifier = Modifier,
    elevation: Dp = 2.dp
) {
    BaseCard({}, elevation, modifier) {
        Spacer(Modifier.height(20.dp))
        SenseTreeItem(sense, forms)
        Spacer(Modifier.height(20.dp))
    }
}

@Suppress("NonSkippableComposable")
@Composable
private fun SenseTreeItem(sense: SenseCombinedUi, forms: List<FormUi>) {
    Sense(sense.gloss, modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp))

    sense.examples.forEach { example ->
        Spacer(Modifier.height(8.dp))
        ExampleItem(example, forms)
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

            SenseTreeItem(senseCombined, forms)
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
fun ExampleItem(example: ExampleUi, forms: List<FormUi>, modifier: Modifier = Modifier) {
    val bodyMedium = MaterialTheme.typography.bodyMedium
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariantColor = MaterialTheme.colorScheme.onSurfaceVariant
    val text = buildAnnotatedString {
        val bodyMediumSpan = bodyMedium.toSpanStyle()
        val text = example.text ?: return
        withStyle(bodyMediumSpan.copy(onSurfaceVariantColor)) {
            append(text)
        }
        forms.fastForEach {
            it.formText?.let { form ->
                val start = text.indexOf(form)
                val end = start + form.length
                addStyle(bodyMediumSpan.copy(onSurfaceColor), start, end)
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