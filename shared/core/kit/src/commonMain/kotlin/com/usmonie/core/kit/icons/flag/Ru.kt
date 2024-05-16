package com.usmonie.core.kit.icons.flag

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.usmonie.core.kit.icons.Flag

@Suppress("MagicNumber")
public val Flag.Ru: ImageVector
    get() {
        if (_ru != null) {
            return _ru!!
        }
        _ru = Builder(
            name = "Ru", defaultWidth = 128.0.dp, defaultHeight = 128.0.dp,
            viewportWidth =
            900.0f,
            viewportHeight = 600.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveToRelative(0.0f, 0.0f)
                horizontalLineToRelative(900.0f)
                verticalLineToRelative(600.0f)
                horizontalLineTo(0.0f)
            }
            path(
                fill = SolidColor(Color(0xFF0083d6)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveToRelative(0.0f, 200.0f)
                horizontalLineToRelative(900.0f)
                verticalLineToRelative(200.0f)
                horizontalLineTo(0.0f)
            }
        }
            .build()
        return _ru!!
    }

private var _ru: ImageVector? = null
