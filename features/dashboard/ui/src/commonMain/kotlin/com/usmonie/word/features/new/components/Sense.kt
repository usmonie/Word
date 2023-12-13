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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.new.models.ExampleUi
import com.usmonie.word.features.new.models.SenseUi
import com.usmonie.word.features.ui.BaseCard

@Composable
fun SenseCard(sense: SenseUi, modifier: Modifier = Modifier, elevation: Dp = 2.dp) {
    BaseCard({}, elevation, modifier) {
        Spacer(Modifier.height(20.dp))
        Sense(sense, modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp))
        sense.examples.forEach { example ->
            Spacer(Modifier.height(8.dp))
            ExampleItem(example)
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun Sense(
    senseUi: SenseUi,
    modifier: Modifier = Modifier,
    expanded: Boolean = false
) {
    val maxLines by remember(expanded) { mutableStateOf(if (expanded) Int.MAX_VALUE else 6) }
    Sense(senseUi, modifier = modifier, maxLines = maxLines)
}

@Composable
fun Sense(senseUi: SenseUi, modifier: Modifier = Modifier, maxLines: Int) {
    Text(
        text = senseUi.glosses.first(),
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

@Composable
fun ExampleItem(example: ExampleUi, modifier: Modifier = Modifier) {
    Column(modifier) {
        Row(
            Modifier.fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(start = 32.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = example.text ?: "",
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
                    .fillMaxHeight()  //fill the max height
                    .width(1.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
        }

        if (example.ref != null) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = example.text ?: "",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth().padding(start = 32.dp, end = 20.dp)
            )
        }
    }
}