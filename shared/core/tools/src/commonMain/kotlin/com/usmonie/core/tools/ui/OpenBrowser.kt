package com.usmonie.core.tools.ui

import androidx.compose.runtime.Composable
import kotlin.jvm.JvmInline

@Composable
expect fun OpenBrowser(url: Url)

@JvmInline
value class Url(val link: String)
