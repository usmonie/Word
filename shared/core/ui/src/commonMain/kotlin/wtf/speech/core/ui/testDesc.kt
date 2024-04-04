package wtf.speech.core.ui

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Stable
fun Modifier.testDesc(tag: String) = semantics(properties = { contentDescription = tag })
