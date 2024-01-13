package com.usmonie.word.features.new.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.new.models.RelatedUi
import com.usmonie.word.features.ui.BaseCard
import wtf.word.core.domain.tools.fastForEach

@Immutable
data class RelatedCardState(val forms: List<RelatedUi>)

@Composable
fun RelatedCard(title: String, relatedState: RelatedCardState) {
    val related by remember(relatedState) {
        derivedStateOf {
            relatedState.forms
                .groupBy { sound -> sound.tags.joinToString { tag -> tag } }
                .mapValues { item ->
                    item.value.asSequence()
                        .map { form -> form.word }
                        .filterNotNull()
                        .joinToString { it }
                }
                .toList()
        }
    }
    BaseCard({}, enabled = false, modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
        TitleUiComponent(
            title,
            Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(top = 20.dp),
            MaterialTheme.colorScheme.onSurface
        )

        related.fastForEach {
            RelatedItem(it.first, it.second)
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun RelatedItem(tag: String?, forms: String) {
    val titleSmall = MaterialTheme.typography.titleSmall
    val labelLarge = MaterialTheme.typography.labelLarge
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariantColor = MaterialTheme.colorScheme.onSurfaceVariant
    val text = remember(tag, forms) {
        buildAnnotatedString {
            val titleSmallSpan = titleSmall.toSpanStyle()
            val labelLargeSpan = labelLarge.toSpanStyle()
            if (!tag.isNullOrBlank()) {
                withStyle(titleSmallSpan.copy(onSurfaceColor)) {
                    append(tag.replaceFirstChar { it.uppercaseChar() })
                    append(": ")
                }
            }

            withStyle(labelLargeSpan.copy(onSurfaceVariantColor)) {
                append(forms)
            }
        }
    }
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp).padding(horizontal = 20.dp)
    )
}
