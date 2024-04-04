package wtf.word.core.design.themes.icons.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import wtf.word.core.design.themes.icons.MyIconPack

public val MyIconPack.Es: ImageVector
    get() {
        if (_Es != null) {
            return _Es!!
        }
        _Es = Builder(name = "Es", defaultWidth = 750.0.dp,
            defaultHeight = 500.0.dp, viewportWidth = 750.0f, viewportHeight = 500.0f).apply {
            path(fill = SolidColor(Color(0xFFc60b1e)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(0.0f, 0.0f)
                horizontalLineToRelative(750.0f)
                verticalLineToRelative(500.0f)
                horizontalLineToRelative(-750.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFffc400)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(0.0f, 125.0f)
                horizontalLineToRelative(750.0f)
                verticalLineToRelative(250.0f)
                horizontalLineToRelative(-750.0f)
                close()
            }
        }
            .build()
        return _Es!!
    }

private var _Es: ImageVector? = null

