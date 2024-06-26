package com.usmonie.core.kit.tools

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun getScreenSize(): Pair<Dp, Dp> {
	return LocalConfiguration.current.screenWidthDp.dp to LocalConfiguration.current.screenHeightDp.dp
}
