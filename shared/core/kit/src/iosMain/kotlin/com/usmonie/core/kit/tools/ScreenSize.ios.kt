package com.usmonie.core.kit.tools

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenSize(): Pair<Dp, Dp> {
	val windowInfo = LocalWindowInfo.current
	return with(LocalDensity.current) {
		windowInfo.containerSize.width.toDp() to windowInfo.containerSize.height.toDp()
	}
}
