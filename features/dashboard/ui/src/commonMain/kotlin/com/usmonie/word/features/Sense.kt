package com.usmonie.word.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.models.ExampleUi
import com.usmonie.word.features.models.Forms
import com.usmonie.word.features.models.SenseCombinedUi
import com.usmonie.word.features.new.ui.BaseCard
import wtf.word.core.domain.tools.fastForEach

@Composable
fun SenseTreeCard(
    sense: () -> SenseCombinedUi,
    word: () -> String,
    forms: () -> Forms,
    modifier: Modifier = Modifier,
) {
    BaseCard(modifier) {
        Spacer(Modifier.height(20.dp))
        SenseTreeItem(sense, word, forms)
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun ColumnScope.SenseTreeItem(
    getSense: () -> SenseCombinedUi,
    getWord: () -> String,
    getForms: () -> Forms,
    deep: Int = 1
) {
    val sense = remember { getSense() }
    val gloss = remember(sense) {
        sense.gloss
    }
    Sense(gloss, modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp))

    sense.examples.fastForEach { example ->
        Spacer(Modifier.height(8.dp))
        ExampleItem(remember { { example } }, getWord, getForms)
    }

    val deepWidth = deep / 5f
    sense.children.fastForEach { senseCombined ->
        Divider(
            Modifier.fillMaxWidth(deepWidth)
                .padding(vertical = 20.dp)
                .clip(RoundedCornerShape(bottomEnd = 10.dp, topEnd = 10.dp)),
            thickness = 4.dp,
        )

        SenseTreeItem({ senseCombined }, getWord, getForms, deep + 1)
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
        LocalHapticFeedback.current.performHapticFeedback(HapticFeedbackType.LongPress)
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

@Composable
fun ExampleItem(
    getExample: () -> ExampleUi,
    getWord: () -> String,
    getForms: () -> Forms,
    modifier: Modifier = Modifier
) {
    val example = getExample()
    val word = getWord()
    val forms = getForms()
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

            setSpan(exampleText, word, bodyLargeSpan, onSurfaceColor)

            forms.forms.fastForEach {
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
                modifier = Modifier
                    .fillMaxHeight()
                    .width(4.dp)
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

@Composable
fun Divider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
    ),
) {
    val targetThickness = if (thickness == Dp.Hairline) {
        (1f / LocalDensity.current.density).dp
    } else {
        thickness
    }
    Box(
        modifier
            .fillMaxWidth()
            .height(targetThickness)
            .background(
                Brush.verticalGradient(color, tileMode = TileMode.Repeated),
            )
    )
}