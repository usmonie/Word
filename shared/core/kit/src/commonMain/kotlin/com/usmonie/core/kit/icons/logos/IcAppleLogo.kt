package com.usmonie.core.kit.icons.logos

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.usmonie.core.kit.icons.Logo

@Suppress("MagicNumber")
public val Logo.IcAppleLogo: ImageVector
    get() {
        if (_icAppleLogo != null) {
            return _icAppleLogo!!
        }
        _icAppleLogo = Builder(
            name = "IcAppleLogo", defaultWidth = 24.0.dp,
            defaultHeight =
            24.0.dp,
            viewportWidth = 50.0f, viewportHeight = 50.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(44.527f, 34.75f)
                curveTo(43.449f, 37.145f, 42.93f, 38.215f, 41.543f, 40.328f)
                curveTo(39.602f, 43.281f, 36.863f, 46.969f, 33.48f, 46.992f)
                curveTo(30.469f, 47.02f, 29.691f, 45.027f, 25.602f, 45.063f)
                curveTo(21.516f, 45.082f, 20.664f, 47.031f, 17.648f, 47.0f)
                curveTo(14.262f, 46.969f, 11.672f, 43.648f, 9.73f, 40.699f)
                curveTo(4.301f, 32.43f, 3.727f, 22.734f, 7.082f, 17.578f)
                curveTo(9.457f, 13.922f, 13.211f, 11.773f, 16.738f, 11.773f)
                curveTo(20.332f, 11.773f, 22.59f, 13.746f, 25.559f, 13.746f)
                curveTo(28.441f, 13.746f, 30.195f, 11.77f, 34.352f, 11.77f)
                curveTo(37.492f, 11.77f, 40.813f, 13.48f, 43.188f, 16.434f)
                curveTo(35.422f, 20.691f, 36.684f, 31.781f, 44.527f, 34.75f)
                close()
                moveTo(31.195f, 8.469f)
                curveTo(32.707f, 6.527f, 33.855f, 3.789f, 33.438f, 1.0f)
                curveTo(30.973f, 1.168f, 28.09f, 2.742f, 26.406f, 4.781f)
                curveTo(24.879f, 6.641f, 23.613f, 9.398f, 24.105f, 12.066f)
                curveTo(26.797f, 12.152f, 29.582f, 10.547f, 31.195f, 8.469f)
                close()
            }
        }
            .build()
        return _icAppleLogo!!
    }

private var _icAppleLogo: ImageVector? = null
