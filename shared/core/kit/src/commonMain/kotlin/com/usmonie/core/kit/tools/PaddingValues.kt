package com.usmonie.core.kit.tools

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

fun PaddingValues.add(
    all: Dp,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr
): PaddingValues {
    return add(all, all, all, all, layoutDirection)
}

fun PaddingValues.add(
    horizontal: Dp = 0.dp,
    vertical: Dp = 0.dp,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr
): PaddingValues {
    return add(horizontal, vertical, vertical, horizontal, layoutDirection)
}

fun PaddingValues.add(
    start: Dp = 0.dp,
    top: Dp = 0.dp,
    bottom: Dp = 0.dp,
    end: Dp = 0.dp,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr
): PaddingValues {
    return PaddingValues(
        this.calculateStartPadding(layoutDirection) + start,
        this.calculateTopPadding() + top,
        this.calculateEndPadding(layoutDirection) + end,
        this.calculateBottomPadding() + bottom,
    )
}
