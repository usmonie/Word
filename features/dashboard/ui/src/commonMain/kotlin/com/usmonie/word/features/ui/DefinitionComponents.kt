package com.usmonie.word.features.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.models.DefinitionUi

@Composable
fun Definition(
    definitionUi: DefinitionUi,
    modifier: Modifier = Modifier,
    position: Int? = null,
    expanded: Boolean = false
) {
    val maxLines by remember(expanded) { mutableStateOf(if (expanded) Int.MAX_VALUE else 6) }
    Row(modifier, verticalAlignment = Alignment.Top) {
        if (position != null) {
            DefinitionNumber(position)
            Spacer(Modifier.width(8.dp))
        }

        Definition(definitionUi, modifier = Modifier.weight(1f), maxLines = maxLines)
    }
}

@Composable
fun Definition(definitionUi: DefinitionUi, modifier: Modifier = Modifier, maxLines: Int) {
    Text(
        definitionUi.text,
        textAlign = TextAlign.Justify,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun DefinitionNumber(position: Int) {
    Text(
        "${position + 1}.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Justify
    )
}

@Composable
private fun DefinitionsTitle() {
    Text(
        "Definitions",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}

@Composable
fun Definitions(definitions: List<DefinitionUi>, expandedByDefault: Boolean = false) {
    var expanded by remember(definitions, expandedByDefault) { mutableStateOf(expandedByDefault || definitions.size < 2) }
    val size by remember(expanded) { mutableStateOf(if (expanded) definitions.size else 2) }

    Column(Modifier.animateContentSize()) {
        DefinitionsTitle()
        Spacer(Modifier.height(4.dp))

        repeat(size) {
            if (it > 0) Spacer(Modifier.padding(vertical = 8.dp))

            val definition = definitions.getOrNull(it) ?: return@repeat
            key(definition.text) {
                Definition(
                    definition,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    position = if (definitions.size > 1) it else null,
                    expanded = expanded
                )
            }
        }


        if (definitions.size > 2 && !expandedByDefault) {
            Row {
                Spacer(Modifier.width(28.dp))
                TextButton(
                    { expanded = !expanded },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        if (expanded) "Collapse" else "Expand",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}