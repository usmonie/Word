package com.usmonie.core.kit.tools

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

fun PaddingValues.add(
    all: Dp,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr
): PaddingValues {
    return PaddingValues(
        this.calculateStartPadding(layoutDirection) + all,
        this.calculateTopPadding() + all,
        this.calculateEndPadding(layoutDirection) + all,
        this.calculateBottomPadding() + all,
    )
}

fun PaddingValues.add(
    horizontal: Dp,
    vertical: Dp,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr
): PaddingValues {
    return PaddingValues(
        this.calculateStartPadding(layoutDirection) + horizontal,
        this.calculateTopPadding() + vertical,
        this.calculateEndPadding(layoutDirection) + horizontal,
        this.calculateBottomPadding() + vertical,
    )
}

fun PaddingValues.add(
    start: Dp,
    top: Dp,
    bottom: Dp,
    end: Dp,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr
): PaddingValues {
    return PaddingValues(
        this.calculateStartPadding(layoutDirection) + start,
        this.calculateTopPadding() + top,
        this.calculateEndPadding(layoutDirection) + end,
        this.calculateBottomPadding() + bottom,
    )
}

fun PaddingValues.addVertical(
    vertical: Dp,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr
): PaddingValues {
    return PaddingValues(
        this.calculateStartPadding(layoutDirection),
        this.calculateTopPadding() + vertical,
        this.calculateEndPadding(layoutDirection),
        this.calculateBottomPadding() + vertical,
    )
}

fun PaddingValues.addTop(
    top: Dp,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr
): PaddingValues {
    return PaddingValues(
        this.calculateStartPadding(layoutDirection),
        this.calculateTopPadding() + top,
        this.calculateEndPadding(layoutDirection),
        this.calculateBottomPadding(),
    )
}

fun PaddingValues.addBottom(
    bottom: Dp,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr
): PaddingValues {
    return PaddingValues(
        this.calculateStartPadding(layoutDirection),
        this.calculateTopPadding() + bottom,
        this.calculateEndPadding(layoutDirection),
        this.calculateBottomPadding(),
    )
}

fun PaddingValues.addHorizontal(
    horizontal: Dp,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr
): PaddingValues {
    return PaddingValues(
        this.calculateStartPadding(layoutDirection),
        this.calculateTopPadding() + horizontal,
        this.calculateEndPadding(layoutDirection),
        this.calculateBottomPadding() + horizontal,
    )
}

fun PaddingValues.addStart(
    start: Dp,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr
): PaddingValues {
    return PaddingValues(
        this.calculateStartPadding(layoutDirection) + start,
        this.calculateTopPadding(),
        this.calculateEndPadding(layoutDirection),
        this.calculateBottomPadding(),
    )
}

fun PaddingValues.addEnd(
    end: Dp,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr
): PaddingValues {
    return PaddingValues(
        this.calculateStartPadding(layoutDirection),
        this.calculateTopPadding(),
        this.calculateEndPadding(layoutDirection) + end,
        this.calculateBottomPadding(),
    )
}
