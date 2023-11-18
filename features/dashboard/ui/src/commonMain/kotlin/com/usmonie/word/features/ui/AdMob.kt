package com.usmonie.word.features.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

expect class AdMob {

    @Composable
    fun Banner(adKey: String, modifier: Modifier = Modifier)

    @Composable
    fun RewardedInterstitial(adKey: String, modifier: Modifier = Modifier)

    @Composable
    fun Startup(adKey: String, modifier: Modifier = Modifier)
}