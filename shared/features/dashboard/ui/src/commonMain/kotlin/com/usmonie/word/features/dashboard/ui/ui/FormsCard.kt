package com.usmonie.word.features.dashboard.ui.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.ui.models.FormUi
import wtf.speech.core.ui.BaseCard
import wtf.word.core.domain.tools.fastForEach

private val formsModifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)

@Composable
fun FormsCard(getFormsState: () -> List<FormUi>) {
    val formsState = getFormsState()
    val forms by remember(formsState) {
        derivedStateOf {
            formsState
                .groupBy {
                    it.tags
                        .lastOrNull()
                        ?.replaceFirstChar { c -> c.uppercaseChar() }
                }
                .mapValues { item ->
                    item.value.asSequence()
                        .map { form -> form.formText }
                        .filterNotNull()
                        .joinToString { it }
                }
                .toList()
        }
    }
    BaseCard(modifier = formsModifier) {
        TitleUiComponent(
            "Forms",
            formsModifier.padding(top = 24.dp),
            MaterialTheme.colorScheme.onSurface
        )

        forms.fastForEach {
            FormsItem(it.first, it.second)
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun FormsItem(tag: String?, forms: String) {
    val titleSmall = MaterialTheme.typography.titleMedium
    val labelLarge = MaterialTheme.typography.bodyLarge
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val text = remember(tag, forms) {
        buildAnnotatedString {
            val titleSmallSpan = titleSmall.toSpanStyle()
            val labelLargeSpan = labelLarge.toSpanStyle()
            if (!tag.isNullOrBlank()) {
                withStyle(titleSmallSpan.copy(onSurfaceColor)) {
                    append(tag)
                    append(": ")
                }
            }

            withStyle(labelLargeSpan.copy(onSurfaceColor)) {
                append(forms)
            }
        }
    }
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = onSurfaceColor,
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp).padding(horizontal = 24.dp)
    )
}