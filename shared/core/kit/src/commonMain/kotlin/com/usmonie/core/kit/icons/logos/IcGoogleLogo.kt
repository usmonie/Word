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
public val Logo.IcGoogleLogo: ImageVector
    get() {
        if (_icGoogleLogo != null) {
            return _icGoogleLogo!!
        }
        _icGoogleLogo = Builder(
            name = "IcGoogleLogo", defaultWidth = 48.0.dp,
            defaultHeight =
            48.0.dp,
            viewportWidth = 48.0f, viewportHeight = 48.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFFC107)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(43.611f, 20.083f)
                horizontalLineTo(42.0f)
                verticalLineTo(20.0f)
                horizontalLineTo(24.0f)
                verticalLineToRelative(8.0f)
                horizontalLineToRelative(11.303f)
                curveToRelative(-1.649f, 4.657f, -6.08f, 8.0f, -11.303f, 8.0f)
                curveToRelative(-6.627f, 0.0f, -12.0f, -5.373f, -12.0f, -12.0f)
                curveToRelative(0.0f, -6.627f, 5.373f, -12.0f, 12.0f, -12.0f)
                curveToRelative(3.059f, 0.0f, 5.842f, 1.154f, 7.961f, 3.039f)
                lineToRelative(5.657f, -5.657f)
                curveTo(34.046f, 6.053f, 29.268f, 4.0f, 24.0f, 4.0f)
                curveTo(12.955f, 4.0f, 4.0f, 12.955f, 4.0f, 24.0f)
                curveToRelative(0.0f, 11.045f, 8.955f, 20.0f, 20.0f, 20.0f)
                curveToRelative(11.045f, 0.0f, 20.0f, -8.955f, 20.0f, -20.0f)
                curveTo(44.0f, 22.659f, 43.862f, 21.35f, 43.611f, 20.083f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFF3D00)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(6.306f, 14.691f)
                lineToRelative(6.571f, 4.819f)
                curveTo(14.655f, 15.108f, 18.961f, 12.0f, 24.0f, 12.0f)
                curveToRelative(3.059f, 0.0f, 5.842f, 1.154f, 7.961f, 3.039f)
                lineToRelative(5.657f, -5.657f)
                curveTo(34.046f, 6.053f, 29.268f, 4.0f, 24.0f, 4.0f)
                curveTo(16.318f, 4.0f, 9.656f, 8.337f, 6.306f, 14.691f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF4CAF50)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(24.0f, 44.0f)
                curveToRelative(5.166f, 0.0f, 9.86f, -1.977f, 13.409f, -5.192f)
                lineToRelative(-6.19f, -5.238f)
                curveTo(29.211f, 35.091f, 26.715f, 36.0f, 24.0f, 36.0f)
                curveToRelative(-5.202f, 0.0f, -9.619f, -3.317f, -11.283f, -7.946f)
                lineToRelative(-6.522f, 5.025f)
                curveTo(9.505f, 39.556f, 16.227f, 44.0f, 24.0f, 44.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF1976D2)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(43.611f, 20.083f)
                horizontalLineTo(42.0f)
                verticalLineTo(20.0f)
                horizontalLineTo(24.0f)
                verticalLineToRelative(8.0f)
                horizontalLineToRelative(11.303f)
                curveToRelative(-0.792f, 2.237f, -2.231f, 4.166f, -4.087f, 5.571f)
                curveToRelative(0.001f, -0.001f, 0.002f, -0.001f, 0.003f, -0.002f)
                lineToRelative(6.19f, 5.238f)
                curveTo(36.971f, 39.205f, 44.0f, 34.0f, 44.0f, 24.0f)
                curveTo(44.0f, 22.659f, 43.862f, 21.35f, 43.611f, 20.083f)
                close()
            }
        }
            .build()
        return _icGoogleLogo!!
    }

private var _icGoogleLogo: ImageVector? = null
