package com.usmonie.word.features.dashboard.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.usmonie.word.features.dashboard.ui.Url

@Suppress("NonSkippableComposable")
@Composable
actual fun OpenBrowser(url: Url) {
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.url))
    context.startActivity(intent)
}