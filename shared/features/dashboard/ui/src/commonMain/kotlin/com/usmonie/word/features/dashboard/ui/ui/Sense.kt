package com.usmonie.word.features.dashboard.ui.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.ui.models.ExampleUi
import com.usmonie.word.features.dashboard.ui.models.Forms
import com.usmonie.word.features.dashboard.ui.models.SenseCombinedUi
import wtf.speech.core.ui.BaseCardDefaults
import wtf.word.core.domain.tools.fastForEach

@Composable
fun SenseTreeCard(
    sense: () -> SenseCombinedUi,
    word: () -> String,
    forms: () -> Forms,
    modifier: Modifier = Modifier,
) {
    SenseTreeItem(sense, word, forms, modifier = modifier)
}

@Composable
private fun SenseTreeItem(
    getSense: () -> SenseCombinedUi,
    getWord: () -> String,
    getForms: () -> Forms,
    deep: Int = 1,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    modifier: Modifier = Modifier
) {
    var examplesExpanded by remember { mutableStateOf(false) }
    Card(
        shape = BaseCardDefaults.shape,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = modifier
    ) {
        val sense = getSense()
        Spacer(Modifier.height(24.dp))
        Sense(
            sense.gloss,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        )

        if (sense.examples.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            AnimatedContent(examplesExpanded) { expanded ->
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    if (expanded || sense.examples.size == 1) {
                        if (sense.examples.size > 1) {
                            QuoteCardCollapse(
                                "Collapse examples",
                                { examplesExpanded = false },
                                Modifier.fillMaxWidth().padding(horizontal = 24.dp)
                            )
                        }

                        sense.examples.fastForEach { example ->
                            ExampleItem(
                                { example },
                                getWord,
                                getForms,
                                modifier = Modifier.padding(horizontal = 24.dp)
                            )
                        }
                    } else {
                        QuoteCardExpand(
                            "Expand examples",
                            { examplesExpanded = true },
                            Modifier.fillMaxWidth().padding(horizontal = 24.dp)
                        )
                    }
                }

            }
        }

        val deepWidth = deep / 5f
        sense.children.fastForEach { senseCombined ->
            Divider(
                Modifier.fillMaxWidth(deepWidth)
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(12.dp)),
                thickness = 2.dp,
            )

            val newDeep = deep + 1

            SenseTreeItem(
                { senseCombined },
                getWord,
                getForms,
                newDeep,
                when (newDeep) {
                    2 -> MaterialTheme.colorScheme.surfaceContainer
                    3 -> MaterialTheme.colorScheme.surfaceContainerHigh
                    4 -> MaterialTheme.colorScheme.surfaceContainerHighest
                    5 -> MaterialTheme.colorScheme.surfaceContainerLow
                    else -> MaterialTheme.colorScheme.surfaceContainerLowest
                },
                Modifier.fillMaxWidth().padding(horizontal = 16.dp * deep)
            )
        }
        Spacer(Modifier.height(24.dp))
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
            color = MaterialTheme.colorScheme.onSurface,
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

    QuoteCard(text, example.ref, modifier)
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