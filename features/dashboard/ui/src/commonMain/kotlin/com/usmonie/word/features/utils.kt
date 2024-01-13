package com.usmonie.word.features

import androidx.compose.runtime.Composable


@Composable
expect fun OpenBrowser(url: Url)

class Url(val url: String)