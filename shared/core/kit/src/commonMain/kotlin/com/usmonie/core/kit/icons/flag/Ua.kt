package com.usmonie.core.kit.icons.flag

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.usmonie.core.kit.icons.Flag

@Suppress("MagicNumber")
public val Flag.Ua: ImageVector
    get() {
        if (_ua != null) {
            return _ua!!
        }
        _ua = Builder(
            name = "Ua", defaultWidth = 128.0.dp, defaultHeight = 128.0.dp,
            viewportWidth =
            512.0f,
            viewportHeight = 512.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFffd700)), stroke = null, strokeLineWidth = 1.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(0.0f, 0.0f)
                horizontalLineToRelative(512.0f)
                verticalLineToRelative(512.0f)
                horizontalLineTo(0.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF0057b8)), stroke = null, strokeLineWidth = 1.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(0.0f, 0.0f)
                horizontalLineToRelative(512.0f)
                verticalLineToRelative(256.0f)
                horizontalLineTo(0.0f)
                close()
            }
        }
            .build()
        return _ua!!
    }

private var _ua: ImageVector? = null
