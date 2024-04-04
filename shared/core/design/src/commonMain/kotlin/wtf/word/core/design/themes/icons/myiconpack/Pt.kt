package wtf.word.core.design.themes.icons.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import wtf.word.core.design.themes.icons.MyIconPack

public val MyIconPack.Pt: ImageVector
    get() {
        if (_pt != null) {
            return _pt!!
        }
        _pt = Builder(name = "Pt", defaultWidth = 128.0.dp, defaultHeight = 128.0.dp, viewportWidth
                = 512.0f, viewportHeight = 512.0f).apply {
            path(fill = SolidColor(Color(0xFFff0000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(204.8f, 0.0f)
                horizontalLineTo(512.0f)
                verticalLineToRelative(512.0f)
                horizontalLineTo(204.7f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(0.0f, 0.0f)
                horizontalLineToRelative(204.8f)
                verticalLineToRelative(512.0f)
                horizontalLineTo(-0.1f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(293.8f, 326.6f)
                curveToRelative(-34.4f, -1.0f, -192.0f, -99.4f, -193.0f, -115.1f)
                lineToRelative(8.6f, -14.5f)
                curveTo(125.0f, 219.7f, 286.2f, 315.4f, 302.0f, 312.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(107.6f, 195.0f)
                curveToRelative(-3.1f, 8.3f, 41.1f, 35.6f, 94.3f, 68.0f)
                curveToRelative(53.2f, 32.3f, 99.0f, 52.3f, 102.5f, 49.5f)
                lineToRelative(1.5f, -2.9f)
                curveToRelative(-0.6f, 1.0f, -2.2f, 1.3f, -4.6f, 0.6f)
                curveToRelative(-14.4f, -4.2f, -51.9f, -21.4f, -98.3f, -49.5f)
                reflectiveCurveToRelative(-86.8f, -54.1f, -93.0f, -65.1f)
                arcToRelative(6.7f, 6.7f, 0.0f, false, true, -0.7f, -3.3f)
                horizontalLineToRelative(-0.2f)
                lineToRelative(-1.3f, 2.3f)
                close()
                moveTo(294.6f, 327.0f)
                curveToRelative(-0.6f, 1.1f, -1.7f, 1.1f, -3.7f, 1.0f)
                curveToRelative(-12.9f, -1.5f, -52.0f, -20.5f, -98.0f, -48.1f)
                curveToRelative(-53.8f, -32.2f, -98.2f, -61.5f, -93.3f, -69.1f)
                lineToRelative(1.3f, -2.4f)
                lineToRelative(0.2f, 0.1f)
                curveToRelative(-4.3f, 13.0f, 87.6f, 65.5f, 93.0f, 68.9f)
                curveToRelative(53.1f, 33.0f, 98.0f, 52.2f, 102.0f, 47.2f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(205.0f, 221.0f)
                curveToRelative(34.3f, -0.3f, 76.8f, -4.7f, 101.2f, -14.4f)
                lineTo(301.0f, 198.0f)
                curveToRelative(-14.5f, 8.0f, -57.1f, 13.3f, -96.3f, 14.0f)
                curveToRelative(-46.4f, -0.4f, -79.1f, -4.7f, -95.5f, -15.7f)
                lineToRelative(-5.0f, 9.1f)
                curveToRelative(30.2f, 12.7f, 61.0f, 15.5f, 100.8f, 15.6f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(307.7f, 206.8f)
                curveToRelative(-0.9f, 1.3f, -16.8f, 6.8f, -40.3f, 10.8f)
                arcToRelative(416.0f, 416.0f, 0.0f, false, true, -62.6f, 4.6f)
                arcToRelative(444.3f, 444.3f, 0.0f, false, true, -60.0f, -3.8f)
                curveToRelative(-24.5f, -3.9f, -37.3f, -9.3f, -42.0f, -11.2f)
                lineToRelative(1.2f, -2.3f)
                arcToRelative(186.0f, 186.0f, 0.0f, false, false, 41.3f, 11.0f)
                arcToRelative(438.9f, 438.9f, 0.0f, false, false, 59.5f, 3.7f)
                arcToRelative(417.8f, 417.8f, 0.0f, false, false, 62.1f, -4.6f)
                curveToRelative(24.0f, -3.9f, 37.2f, -8.9f, 39.0f, -11.2f)
                close()
                moveTo(303.0f, 198.0f)
                curveToRelative(-2.6f, 2.0f, -15.6f, 6.7f, -38.4f, 10.3f)
                arcToRelative(414.0f, 414.0f, 0.0f, false, true, -59.5f, 4.2f)
                arcToRelative(400.0f, 400.0f, 0.0f, false, true, -57.4f, -3.7f)
                curveToRelative(-23.3f, -3.0f, -35.6f, -8.5f, -40.0f, -10.1f)
                lineToRelative(1.3f, -2.3f)
                curveToRelative(3.4f, 1.8f, 15.4f, 6.6f, 39.0f, 10.0f)
                arcToRelative(423.0f, 423.0f, 0.0f, false, false, 57.1f, 3.5f)
                arcToRelative(408.6f, 408.6f, 0.0f, false, false, 59.0f, -4.2f)
                curveToRelative(23.0f, -3.2f, 35.4f, -9.0f, 37.3f, -10.5f)
                close()
                moveTo(92.0f, 262.4f)
                curveToRelative(21.2f, 11.4f, 68.1f, 17.2f, 112.6f, 17.6f)
                curveToRelative(40.5f, 0.0f, 93.3f, -6.3f, 113.0f, -16.8f)
                lineToRelative(-0.6f, -11.4f)
                curveToRelative(-6.1f, 9.7f, -62.6f, 19.0f, -112.8f, 18.6f)
                curveToRelative(-50.2f, -0.4f, -96.8f, -8.2f, -112.3f, -18.2f)
                verticalLineToRelative(10.2f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(318.7f, 260.8f)
                verticalLineToRelative(2.7f)
                curveToRelative(-3.0f, 3.6f, -21.6f, 9.0f, -44.9f, 12.7f)
                arcToRelative(463.2f, 463.2f, 0.0f, false, true, -69.7f, 4.8f)
                curveToRelative(-27.4f, 0.0f, -49.2f, -2.0f, -66.2f, -4.6f)
                arcToRelative(164.8f, 164.8f, 0.0f, false, true, -47.3f, -12.7f)
                verticalLineToRelative(-3.2f)
                curveToRelative(10.4f, 6.9f, 38.3f, 11.9f, 47.7f, 13.4f)
                curveToRelative(16.8f, 2.6f, 38.5f, 4.5f, 65.8f, 4.5f)
                curveToRelative(28.7f, 0.0f, 51.7f, -2.0f, 69.3f, -4.7f)
                curveToRelative(16.8f, -2.4f, 40.6f, -8.7f, 45.3f, -12.9f)
                moveToRelative(0.0f, -9.7f)
                verticalLineToRelative(2.8f)
                curveToRelative(-3.0f, 3.5f, -21.6f, 8.9f, -44.9f, 12.6f)
                arcToRelative(463.2f, 463.2f, 0.0f, false, true, -69.7f, 4.8f)
                curveToRelative(-27.4f, 0.0f, -49.2f, -2.0f, -66.1f, -4.5f)
                arcTo(165.0f, 165.0f, 0.0f, false, true, 90.6f, 254.0f)
                verticalLineToRelative(-3.2f)
                curveToRelative(10.4f, 6.9f, 38.3f, 12.0f, 47.7f, 13.4f)
                curveToRelative(16.9f, 2.6f, 38.6f, 4.6f, 65.8f, 4.6f)
                curveToRelative(28.7f, 0.0f, 51.7f, -2.0f, 69.3f, -4.8f)
                curveToRelative(16.8f, -2.4f, 40.6f, -8.7f, 45.3f, -12.9f)
                moveToRelative(-114.2f, 73.5f)
                curveToRelative(-48.6f, -0.3f, -90.3f, -13.2f, -99.1f, -15.4f)
                lineToRelative(6.4f, 10.0f)
                curveToRelative(15.5f, 6.6f, 56.2f, 16.4f, 93.2f, 15.3f)
                reflectiveCurveToRelative(69.3f, -4.0f, 92.0f, -15.0f)
                lineToRelative(6.6f, -10.5f)
                curveToRelative(-15.5f, 7.3f, -68.3f, 15.6f, -99.0f, 15.6f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.6f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(299.5f, 317.1f)
                arcToRelative(152.6f, 152.6f, 0.0f, false, true, -3.0f, 4.4f)
                arcToRelative(258.9f, 258.9f, 0.0f, false, true, -34.7f, 8.9f)
                arcToRelative(315.0f, 315.0f, 0.0f, false, true, -57.2f, 5.2f)
                curveToRelative(-43.1f, -0.6f, -78.4f, -9.0f, -95.0f, -16.2f)
                lineToRelative(-1.4f, -2.3f)
                lineToRelative(0.3f, -0.4f)
                lineToRelative(2.2f, 0.9f)
                arcToRelative(305.5f, 305.5f, 0.0f, false, false, 94.1f, 15.5f)
                curveToRelative(20.0f, 0.1f, 40.0f, -2.3f, 56.1f, -5.1f)
                curveToRelative(24.8f, -5.0f, 34.8f, -8.7f, 37.9f, -10.4f)
                close()
                moveTo(305.2f, 307.7f)
                horizontalLineToRelative(0.1f)
                arcToRelative(302.0f, 302.0f, 0.0f, false, true, -2.2f, 3.8f)
                curveToRelative(-5.7f, 2.0f, -21.3f, 6.6f, -44.0f, 9.8f)
                curveToRelative(-15.0f, 2.0f, -24.3f, 4.0f, -54.0f, 4.6f)
                arcToRelative(371.0f, 371.0f, 0.0f, false, true, -100.5f, -15.0f)
                lineToRelative(-1.2f, -2.5f)
                arcToRelative(424.4f, 424.4f, 0.0f, false, false, 101.7f, 15.0f)
                curveToRelative(27.2f, -0.6f, 38.8f, -2.6f, 53.6f, -4.7f)
                curveToRelative(26.5f, -4.0f, 39.8f, -8.4f, 43.8f, -9.7f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, false, -0.1f, -0.2f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(305.8f, 253.4f)
                curveToRelative(0.2f, 32.0f, -16.2f, 60.8f, -29.4f, 73.5f)
                arcToRelative(106.0f, 106.0f, 0.0f, false, true, -72.3f, 30.0f)
                curveToRelative(-32.3f, 0.6f, -62.7f, -20.5f, -70.9f, -29.7f)
                curveToRelative(-16.0f, -18.1f, -29.0f, -41.0f, -29.4f, -72.0f)
                curveToRelative(2.0f, -35.0f, 15.7f, -59.3f, 35.6f, -76.0f)
                arcToRelative(106.3f, 106.3f, 0.0f, false, true, 68.4f, -24.2f)
                curveToRelative(25.4f, 0.7f, 55.2f, 13.2f, 75.7f, 38.0f)
                curveToRelative(13.4f, 16.2f, 19.3f, 33.8f, 22.3f, 60.5f)
                close()
                moveTo(204.4f, 143.8f)
                curveToRelative(62.0f, 0.0f, 113.0f, 50.5f, 113.0f, 112.3f)
                arcToRelative(113.0f, 113.0f, 0.0f, false, true, -113.0f, 112.3f)
                curveToRelative(-62.0f, 0.0f, -112.6f, -50.4f, -112.6f, -112.3f)
                reflectiveCurveToRelative(50.6f, -112.3f, 112.6f, -112.3f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(204.7f, 143.4f)
                curveToRelative(62.0f, 0.0f, 112.6f, 50.7f, 112.6f, 112.7f)
                reflectiveCurveToRelative(-50.6f, 112.7f, -112.6f, 112.7f)
                arcTo(113.0f, 113.0f, 0.0f, false, true, 92.0f, 256.0f)
                arcToRelative(113.0f, 113.0f, 0.0f, false, true, 112.7f, -112.7f)
                close()
                moveTo(94.5f, 256.1f)
                curveToRelative(0.0f, 60.6f, 49.8f, 110.2f, 110.2f, 110.2f)
                curveToRelative(60.4f, 0.0f, 110.1f, -49.6f, 110.1f, -110.2f)
                reflectiveCurveTo(265.1f, 146.0f, 204.7f, 146.0f)
                arcTo(110.6f, 110.6f, 0.0f, false, false, 94.5f, 256.1f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(204.8f, 152.8f)
                curveToRelative(56.5f, 0.0f, 103.1f, 46.5f, 103.1f, 103.2f)
                curveToRelative(0.0f, 56.8f, -46.6f, 103.2f, -103.1f, 103.2f)
                arcTo(103.6f, 103.6f, 0.0f, false, true, 101.5f, 256.0f)
                curveToRelative(0.0f, -56.7f, 46.6f, -103.2f, 103.2f, -103.2f)
                close()
                moveTo(104.0f, 256.0f)
                curveToRelative(0.0f, 55.4f, 45.5f, 100.7f, 100.8f, 100.7f)
                curveToRelative(55.2f, 0.0f, 100.7f, -45.3f, 100.7f, -100.7f)
                curveToRelative(0.0f, -55.4f, -45.5f, -100.7f, -100.8f, -100.7f)
                reflectiveCurveTo(104.0f, 200.6f, 104.0f, 256.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(209.3f, 143.0f)
                horizontalLineToRelative(-9.7f)
                verticalLineToRelative(226.4f)
                horizontalLineToRelative(9.7f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(208.3f, 141.7f)
                horizontalLineToRelative(2.5f)
                verticalLineToRelative(229.0f)
                horizontalLineToRelative(-2.5f)
                close()
                moveTo(198.7f, 141.7f)
                horizontalLineToRelative(2.5f)
                verticalLineToRelative(229.0f)
                horizontalLineToRelative(-2.5f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(317.4f, 260.5f)
                verticalLineTo(252.0f)
                lineToRelative(-6.8f, -6.3f)
                lineToRelative(-38.7f, -10.3f)
                lineToRelative(-55.8f, -5.7f)
                lineToRelative(-67.0f, 3.4f)
                lineToRelative(-47.9f, 11.4f)
                lineToRelative(-9.6f, 7.2f)
                verticalLineToRelative(8.3f)
                lineToRelative(24.4f, -11.0f)
                lineToRelative(58.0f, -9.0f)
                horizontalLineToRelative(55.8f)
                lineToRelative(41.0f, 4.5f)
                lineToRelative(28.4f, 6.9f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(204.7f, 238.7f)
                arcToRelative(394.0f, 394.0f, 0.0f, false, true, 72.9f, 6.5f)
                curveToRelative(21.1f, 4.2f, 36.0f, 9.5f, 41.0f, 15.4f)
                verticalLineToRelative(3.0f)
                curveToRelative(-6.0f, -7.4f, -26.0f, -12.8f, -41.5f, -16.0f)
                curveToRelative(-20.3f, -3.9f, -46.0f, -6.4f, -72.4f, -6.4f)
                curveToRelative(-28.0f, 0.0f, -54.0f, 2.7f, -74.0f, 6.6f)
                curveToRelative(-16.0f, 3.2f, -37.4f, 9.5f, -40.1f, 15.8f)
                verticalLineToRelative(-3.0f)
                curveToRelative(1.5f, -4.4f, 17.4f, -10.9f, 39.8f, -15.4f)
                arcToRelative(405.0f, 405.0f, 0.0f, false, true, 74.3f, -6.5f)
                moveToRelative(0.0f, -9.7f)
                arcToRelative(396.0f, 396.0f, 0.0f, false, true, 73.0f, 6.5f)
                curveToRelative(21.0f, 4.2f, 36.0f, 9.5f, 41.0f, 15.5f)
                verticalLineToRelative(2.9f)
                curveToRelative(-6.1f, -7.4f, -26.2f, -12.8f, -41.6f, -15.9f)
                curveToRelative(-20.3f, -4.0f, -46.0f, -6.5f, -72.4f, -6.5f)
                arcToRelative(402.0f, 402.0f, 0.0f, false, false, -73.8f, 6.6f)
                curveToRelative(-15.5f, 3.0f, -37.8f, 9.6f, -40.3f, 15.8f)
                verticalLineToRelative(-3.0f)
                curveToRelative(1.5f, -4.3f, 17.8f, -11.0f, 39.8f, -15.3f)
                arcToRelative(405.0f, 405.0f, 0.0f, false, true, 74.3f, -6.6f)
                moveToRelative(-0.5f, -49.3f)
                curveToRelative(41.9f, -0.2f, 78.5f, 5.9f, 95.2f, 14.5f)
                lineToRelative(6.1f, 10.5f)
                curveToRelative(-14.5f, -7.8f, -54.0f, -16.0f, -101.3f, -14.7f)
                curveToRelative(-38.5f, 0.2f, -79.6f, 4.2f, -100.3f, 15.2f)
                lineToRelative(7.3f, -12.2f)
                curveToRelative(17.0f, -8.8f, 57.0f, -13.2f, 93.0f, -13.3f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(204.7f, 188.4f)
                curveToRelative(24.0f, 0.0f, 47.0f, 1.3f, 65.4f, 4.6f)
                curveToRelative(17.1f, 3.2f, 33.5f, 8.0f, 35.8f, 10.6f)
                lineToRelative(1.8f, 3.2f)
                curveToRelative(-5.7f, -3.7f, -19.8f, -7.9f, -38.0f, -11.3f)
                arcToRelative(367.2f, 367.2f, 0.0f, false, false, -65.0f, -4.5f)
                curveToRelative(-27.0f, 0.0f, -48.0f, 1.3f, -66.0f, 4.5f)
                curveToRelative(-19.0f, 3.5f, -32.2f, 8.6f, -35.5f, 11.0f)
                lineToRelative(1.8f, -3.3f)
                curveToRelative(6.3f, -3.3f, 16.4f, -7.2f, 33.3f, -10.2f)
                curveToRelative(18.6f, -3.4f, 39.7f, -4.4f, 66.4f, -4.6f)
                moveToRelative(0.0f, -9.6f)
                arcToRelative(371.0f, 371.0f, 0.0f, false, true, 63.1f, 4.4f)
                curveToRelative(13.9f, 2.7f, 27.6f, 6.9f, 32.7f, 10.6f)
                lineToRelative(2.6f, 4.2f)
                curveToRelative(-4.5f, -5.0f, -21.4f, -9.7f, -36.4f, -12.3f)
                curveToRelative(-17.4f, -3.0f, -39.1f, -4.2f, -62.0f, -4.4f)
                curveToRelative(-24.0f, 0.0f, -46.2f, 1.5f, -63.4f, 4.6f)
                curveToRelative(-16.4f, 3.2f, -27.0f, 6.9f, -31.5f, 9.8f)
                lineToRelative(2.3f, -3.6f)
                curveToRelative(6.2f, -3.2f, 16.2f, -6.2f, 28.8f, -8.7f)
                curveToRelative(17.3f, -3.1f, 39.7f, -4.6f, 63.8f, -4.6f)
                moveToRelative(56.0f, 124.1f)
                arcToRelative(293.3f, 293.3f, 0.0f, false, false, -56.0f, -4.2f)
                curveToRelative(-69.9f, 0.8f, -92.4f, 14.3f, -95.2f, 18.4f)
                lineToRelative(-5.2f, -8.5f)
                curveTo(122.0f, 295.7f, 160.0f, 288.5f, 205.0f, 289.2f)
                curveToRelative(23.3f, 0.4f, 43.4f, 2.0f, 60.4f, 5.2f)
                lineToRelative(-4.8f, 8.5f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.6f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(204.3f, 297.5f)
                curveToRelative(19.4f, 0.3f, 38.5f, 1.0f, 57.0f, 4.5f)
                lineToRelative(-1.4f, 2.4f)
                arcToRelative(297.6f, 297.6f, 0.0f, false, false, -55.5f, -4.3f)
                curveToRelative(-25.8f, -0.2f, -52.0f, 2.2f, -74.6f, 8.7f)
                curveToRelative(-7.2f, 2.0f, -19.0f, 6.6f, -20.3f, 10.4f)
                lineToRelative(-1.3f, -2.2f)
                curveToRelative(0.4f, -2.2f, 7.6f, -6.9f, 21.0f, -10.6f)
                curveToRelative(26.0f, -7.5f, 50.3f, -8.7f, 75.0f, -9.0f)
                close()
                moveTo(205.2f, 287.7f)
                arcToRelative(351.0f, 351.0f, 0.0f, false, true, 61.1f, 5.3f)
                lineToRelative(-1.4f, 2.5f)
                arcToRelative(319.0f, 319.0f, 0.0f, false, false, -59.6f, -5.2f)
                curveToRelative(-25.9f, 0.0f, -53.3f, 1.9f, -78.3f, 9.1f)
                curveToRelative(-8.0f, 2.4f, -22.0f, 7.4f, -22.4f, 11.4f)
                lineToRelative(-1.3f, -2.3f)
                curveToRelative(0.3f, -3.6f, 12.3f, -8.4f, 23.2f, -11.6f)
                arcToRelative(285.0f, 285.0f, 0.0f, false, true, 78.7f, -9.2f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveToRelative(304.4f, 309.9f)
                lineToRelative(-8.4f, 13.0f)
                lineToRelative(-24.1f, -21.4f)
                lineToRelative(-62.6f, -42.0f)
                lineToRelative(-70.5f, -38.8f)
                lineToRelative(-36.7f, -12.5f)
                lineToRelative(7.8f, -14.5f)
                lineToRelative(2.7f, -1.4f)
                lineToRelative(22.8f, 5.7f)
                lineToRelative(75.0f, 38.7f)
                lineToRelative(43.3f, 27.3f)
                lineToRelative(36.3f, 26.0f)
                lineToRelative(14.8f, 17.1f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.7f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(100.8f, 208.5f)
                curveToRelative(6.5f, -4.3f, 53.7f, 16.7f, 103.0f, 46.5f)
                curveToRelative(49.3f, 29.9f, 96.4f, 63.6f, 92.2f, 70.0f)
                lineToRelative(-1.4f, 2.1f)
                lineToRelative(-0.7f, 0.5f)
                curveToRelative(0.2f, 0.0f, 0.9f, -1.0f, 0.0f, -3.3f)
                curveToRelative(-2.2f, -6.9f, -35.5f, -33.5f, -91.0f, -67.0f)
                curveToRelative(-54.0f, -32.2f, -99.0f, -51.6f, -103.5f, -46.0f)
                close()
                moveTo(306.1f, 309.7f)
                curveToRelative(4.0f, -8.0f, -39.7f, -41.0f, -94.0f, -73.1f)
                curveToRelative(-55.5f, -31.5f, -95.6f, -50.0f, -102.9f, -44.5f)
                lineToRelative(-1.6f, 3.0f)
                curveToRelative(0.0f, 0.1f, 0.0f, -0.2f, 0.4f, -0.5f)
                curveToRelative(1.3f, -1.1f, 3.5f, -1.0f, 4.5f, -1.0f)
                curveToRelative(12.6f, 0.1f, 48.6f, 16.6f, 99.0f, 45.6f)
                curveToRelative(22.1f, 12.8f, 93.4f, 58.6f, 93.1f, 71.4f)
                curveToRelative(0.0f, 1.1f, 0.1f, 1.3f, -0.3f, 1.9f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.8f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(137.18f, 268.47f)
                arcToRelative(66.79f, 66.79f, 45.0f, false, false, 19.91f, 47.45f)
                arcToRelative(67.13f, 67.13f, 102.4f, false, false, 47.56f, 20.02f)
                arcToRelative(67.58f, 67.58f, 0.0f, false, false, 47.79f, -19.8f)
                arcToRelative(67.13f, 67.13f, 0.0f, false, false, 19.8f, -47.56f)
                lineToRelative(0.0f, -90.11f)
                lineToRelative(-135.05f, -0.23f)
                close()
            }
            path(fill = SolidColor(Color(0xFFff0000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(139.91f, 268.59f)
                arcToRelative(64.17f, 64.17f, 45.0f, false, false, 19.11f, 45.51f)
                arcToRelative(64.85f, 64.85f, 0.0f, false, false, 45.74f, 19.11f)
                arcToRelative(64.74f, 64.74f, 0.0f, false, false, 45.74f, -18.89f)
                arcToRelative(64.17f, 64.17f, 45.0f, false, false, 19.0f, -45.51f)
                lineToRelative(0.0f, -87.61f)
                lineTo(139.91f, 181.2f)
                lineToRelative(0.0f, 87.38f)
                moveToRelative(103.54f, -61.1f)
                lineToRelative(0.0f, 55.64f)
                lineToRelative(-0.11f, 5.8f)
                arcToRelative(37.77f, 37.77f, 0.0f, false, true, -11.38f, 27.31f)
                arcToRelative(38.68f, 38.68f, 97.95f, false, true, -27.31f, 11.38f)
                curveToRelative(-10.7f, 0.0f, -20.14f, -4.55f, -27.19f, -11.61f)
                arcToRelative(38.68f, 38.68f, 87.96f, false, true, -11.38f, -27.31f)
                lineToRelative(0.0f, -61.44f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(148.11f, 204.07f)
                curveToRelative(0.11f, -6.26f, 4.55f, -7.74f, 4.55f, -7.74f)
                curveToRelative(0.11f, 0.0f, 4.89f, 1.59f, 4.89f, 7.85f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveToRelative(144.24f, 196.45f)
                lineToRelative(-0.8f, 7.17f)
                lineToRelative(4.78f, 0.0f)
                curveToRelative(0.0f, -5.92f, 4.55f, -6.83f, 4.55f, -6.83f)
                curveToRelative(0.11f, 0.0f, 4.55f, 1.25f, 4.66f, 6.83f)
                lineToRelative(4.78f, 0.0f)
                lineToRelative(-0.91f, -7.28f)
                close()
                moveTo(143.1f, 203.73f)
                lineToRelative(19.34f, 0.0f)
                curveToRelative(0.34f, 0.0f, 0.68f, 0.34f, 0.68f, 0.8f)
                curveToRelative(0.0f, 0.57f, -0.34f, 0.91f, -0.68f, 0.91f)
                lineToRelative(-19.34f, 0.0f)
                curveToRelative(-0.34f, 0.0f, -0.68f, -0.34f, -0.68f, -0.91f)
                curveToRelative(0.0f, -0.46f, 0.34f, -0.8f, 0.8f, -0.8f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(150.15f, 203.62f)
                curveToRelative(0.0f, -3.75f, 2.62f, -4.78f, 2.62f, -4.78f)
                reflectiveCurveToRelative(2.62f, 1.14f, 2.62f, 4.78f)
                lineTo(150.15f, 203.62f)
                moveToRelative(-6.6f, -10.24f)
                lineToRelative(18.55f, 0.0f)
                curveToRelative(0.34f, 0.0f, 0.68f, 0.46f, 0.68f, 0.91f)
                curveToRelative(0.0f, 0.34f, -0.34f, 0.68f, -0.68f, 0.68f)
                lineToRelative(-18.55f, 0.0f)
                curveToRelative(-0.34f, 0.0f, -0.68f, -0.34f, -0.68f, -0.8f)
                curveToRelative(0.0f, -0.34f, 0.34f, -0.68f, 0.68f, -0.68f)
                close()
                moveTo(144.01f, 195.08f)
                lineTo(161.53f, 195.08f)
                curveToRelative(0.34f, 0.0f, 0.68f, 0.34f, 0.68f, 0.8f)
                curveToRelative(0.0f, 0.46f, -0.34f, 0.8f, -0.68f, 0.8f)
                lineToRelative(-17.64f, 0.0f)
                curveToRelative(-0.46f, 0.0f, -0.68f, -0.34f, -0.68f, -0.8f)
                curveToRelative(0.0f, -0.46f, 0.23f, -0.8f, 0.68f, -0.8f)
                close()
                moveTo(149.7f, 183.02f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 0.91f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -0.91f)
                lineToRelative(1.48f, 0.0f)
                lineToRelative(0.0f, 1.02f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -1.14f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 2.28f)
                curveToRelative(0.0f, 0.46f, -0.23f, 0.68f, -0.57f, 0.68f)
                lineToRelative(-5.01f, 0.0f)
                curveToRelative(-0.34f, 0.0f, -0.68f, -0.23f, -0.68f, -0.57f)
                close()
                moveTo(154.93f, 186.1f)
                lineTo(155.27f, 193.38f)
                lineToRelative(-4.89f, 0.0f)
                lineToRelative(0.34f, -7.4f)
                lineToRelative(4.21f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(149.02f, 189.51f)
                lineToRelative(0.0f, 3.87f)
                lineToRelative(-4.55f, 0.0f)
                lineToRelative(0.0f, -3.87f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(161.08f, 189.51f)
                lineToRelative(0.0f, 3.87f)
                lineToRelative(-4.55f, 0.0f)
                lineToRelative(0.0f, -3.87f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(143.67f, 186.55f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 1.14f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -1.14f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 1.14f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -1.14f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 2.28f)
                curveToRelative(0.0f, 0.46f, -0.23f, 0.68f, -0.57f, 0.68f)
                lineToRelative(-4.89f, 0.0f)
                arcToRelative(0.68f, 0.68f, 0.0f, false, true, -0.68f, -0.68f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(155.73f, 186.55f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 1.14f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -1.14f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 1.14f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -1.14f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 2.28f)
                curveToRelative(0.0f, 0.46f, -0.23f, 0.68f, -0.57f, 0.68f)
                lineToRelative(-4.89f, 0.0f)
                arcToRelative(0.68f, 0.68f, 0.0f, false, true, -0.68f, -0.68f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(152.32f, 188.37f)
                curveToRelative(0.0f, -0.68f, 1.02f, -0.68f, 1.02f, 0.0f)
                lineToRelative(0.0f, 1.82f)
                lineToRelative(-1.02f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(146.29f, 190.87f)
                curveToRelative(0.0f, -0.68f, 0.91f, -0.68f, 0.91f, 0.0f)
                lineToRelative(0.0f, 1.37f)
                lineToRelative(-0.91f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(158.35f, 190.87f)
                curveToRelative(0.0f, -0.68f, 0.91f, -0.68f, 0.91f, 0.0f)
                lineToRelative(0.0f, 1.37f)
                lineToRelative(-0.91f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(148.11f, 256.75f)
                curveToRelative(0.11f, -6.26f, 4.55f, -7.74f, 4.55f, -7.74f)
                curveToRelative(0.11f, 0.0f, 4.89f, 1.59f, 4.89f, 7.85f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveToRelative(144.24f, 249.13f)
                lineToRelative(-0.8f, 7.17f)
                lineToRelative(4.78f, 0.0f)
                curveToRelative(0.0f, -5.92f, 4.55f, -6.83f, 4.55f, -6.83f)
                curveToRelative(0.11f, 0.0f, 4.55f, 1.25f, 4.66f, 6.83f)
                lineToRelative(4.78f, 0.0f)
                lineToRelative(-0.91f, -7.28f)
                close()
                moveTo(143.1f, 256.41f)
                lineToRelative(19.34f, 0.0f)
                curveToRelative(0.34f, 0.0f, 0.68f, 0.34f, 0.68f, 0.8f)
                curveToRelative(0.0f, 0.57f, -0.34f, 0.91f, -0.68f, 0.91f)
                lineToRelative(-19.34f, 0.0f)
                curveToRelative(-0.34f, 0.0f, -0.68f, -0.34f, -0.68f, -0.91f)
                curveToRelative(0.0f, -0.46f, 0.34f, -0.8f, 0.8f, -0.8f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(150.15f, 256.3f)
                curveToRelative(0.0f, -3.75f, 2.62f, -4.78f, 2.62f, -4.78f)
                reflectiveCurveToRelative(2.62f, 1.14f, 2.62f, 4.78f)
                lineTo(150.15f, 256.3f)
                moveToRelative(-6.6f, -10.24f)
                lineToRelative(18.55f, 0.0f)
                curveToRelative(0.34f, 0.0f, 0.68f, 0.46f, 0.68f, 0.91f)
                curveToRelative(0.0f, 0.34f, -0.34f, 0.68f, -0.68f, 0.68f)
                lineToRelative(-18.55f, 0.0f)
                curveToRelative(-0.34f, 0.0f, -0.68f, -0.34f, -0.68f, -0.8f)
                curveToRelative(0.0f, -0.34f, 0.34f, -0.68f, 0.68f, -0.68f)
                close()
                moveTo(144.01f, 247.76f)
                lineTo(161.53f, 247.76f)
                curveToRelative(0.34f, 0.0f, 0.68f, 0.34f, 0.68f, 0.8f)
                curveToRelative(0.0f, 0.46f, -0.34f, 0.8f, -0.68f, 0.8f)
                lineToRelative(-17.64f, 0.0f)
                curveToRelative(-0.46f, 0.0f, -0.68f, -0.34f, -0.68f, -0.8f)
                curveToRelative(0.0f, -0.46f, 0.23f, -0.8f, 0.68f, -0.8f)
                close()
                moveTo(149.7f, 235.7f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 0.91f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -0.91f)
                lineToRelative(1.48f, 0.0f)
                lineToRelative(0.0f, 1.02f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -1.14f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 2.28f)
                curveToRelative(0.0f, 0.46f, -0.23f, 0.68f, -0.57f, 0.68f)
                lineToRelative(-5.01f, 0.0f)
                curveToRelative(-0.34f, 0.0f, -0.68f, -0.23f, -0.68f, -0.57f)
                close()
                moveTo(154.93f, 238.78f)
                lineTo(155.27f, 246.06f)
                lineToRelative(-4.89f, 0.0f)
                lineToRelative(0.34f, -7.4f)
                lineToRelative(4.21f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(149.02f, 242.19f)
                lineToRelative(0.0f, 3.87f)
                lineToRelative(-4.55f, 0.0f)
                lineToRelative(0.0f, -3.87f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(143.67f, 239.23f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 1.14f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -1.14f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 1.14f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -1.14f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 2.28f)
                curveToRelative(0.0f, 0.46f, -0.23f, 0.68f, -0.57f, 0.68f)
                lineToRelative(-4.89f, 0.0f)
                arcToRelative(0.68f, 0.68f, 0.0f, false, true, -0.68f, -0.68f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(155.73f, 239.23f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 1.14f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -1.14f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 1.14f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -1.14f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 2.28f)
                curveToRelative(0.0f, 0.46f, -0.23f, 0.68f, -0.57f, 0.68f)
                lineToRelative(-4.89f, 0.0f)
                arcToRelative(0.68f, 0.68f, 0.0f, false, true, -0.68f, -0.68f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(152.32f, 241.05f)
                curveToRelative(0.0f, -0.68f, 1.02f, -0.68f, 1.02f, 0.0f)
                lineToRelative(0.0f, 1.82f)
                lineToRelative(-1.02f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(146.29f, 243.55f)
                curveToRelative(0.0f, -0.68f, 0.91f, -0.68f, 0.91f, 0.0f)
                lineToRelative(0.0f, 1.37f)
                lineToRelative(-0.91f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(158.35f, 243.55f)
                curveToRelative(0.0f, -0.68f, 0.91f, -0.68f, 0.91f, 0.0f)
                lineToRelative(0.0f, 1.37f)
                lineToRelative(-0.91f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(168.64f, 311.66f)
                curveToRelative(-4.36f, -4.49f, -2.28f, -8.68f, -2.28f, -8.68f)
                curveToRelative(0.08f, -0.08f, 4.58f, -2.35f, 9.02f, 2.06f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveToRelative(160.5f, 309.03f)
                lineToRelative(4.53f, 5.62f)
                lineToRelative(3.37f, -3.39f)
                curveToRelative(-4.2f, -4.17f, -1.64f, -8.04f, -1.64f, -8.04f)
                curveToRelative(0.08f, -0.08f, 4.09f, -2.35f, 8.13f, 1.5f)
                lineToRelative(3.37f, -3.39f)
                lineToRelative(-5.81f, -4.49f)
                close()
                moveTo(164.87f, 314.97f)
                lineToRelative(13.63f, -13.72f)
                curveToRelative(0.24f, -0.24f, 0.72f, -0.24f, 1.05f, 0.08f)
                curveToRelative(0.4f, 0.4f, 0.41f, 0.88f, 0.16f, 1.13f)
                lineToRelative(-13.63f, 13.72f)
                curveToRelative(-0.24f, 0.24f, -0.72f, 0.24f, -1.13f, -0.16f)
                curveToRelative(-0.32f, -0.32f, -0.32f, -0.8f, -0.0f, -1.13f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(169.76f, 309.88f)
                curveToRelative(-2.66f, -2.65f, -1.55f, -5.22f, -1.55f, -5.22f)
                reflectiveCurveToRelative(2.65f, -1.06f, 5.23f, 1.51f)
                lineTo(169.76f, 309.88f)
                moveToRelative(-11.92f, -2.53f)
                lineToRelative(13.07f, -13.16f)
                curveToRelative(0.24f, -0.24f, 0.8f, -0.16f, 1.13f, 0.16f)
                curveToRelative(0.24f, 0.24f, 0.24f, 0.72f, 0.0f, 0.97f)
                lineToRelative(-13.07f, 13.16f)
                curveToRelative(-0.24f, 0.24f, -0.72f, 0.24f, -1.05f, -0.08f)
                curveToRelative(-0.24f, -0.24f, -0.24f, -0.72f, -0.0f, -0.97f)
                close()
                moveTo(159.37f, 308.23f)
                lineTo(171.72f, 295.8f)
                curveToRelative(0.24f, -0.24f, 0.72f, -0.24f, 1.05f, 0.08f)
                curveToRelative(0.32f, 0.32f, 0.32f, 0.8f, 0.08f, 1.05f)
                lineToRelative(-12.43f, 12.51f)
                curveToRelative(-0.32f, 0.32f, -0.72f, 0.24f, -1.05f, -0.08f)
                curveToRelative(-0.32f, -0.32f, -0.4f, -0.72f, -0.08f, -1.05f)
                close()
                moveTo(154.83f, 295.69f)
                lineToRelative(0.96f, -0.97f)
                lineToRelative(0.65f, 0.64f)
                lineToRelative(0.72f, -0.73f)
                lineToRelative(-0.65f, -0.64f)
                lineToRelative(1.04f, -1.05f)
                lineToRelative(0.73f, 0.72f)
                lineToRelative(0.72f, -0.73f)
                lineToRelative(-0.81f, -0.8f)
                lineToRelative(0.96f, -0.97f)
                lineToRelative(1.61f, 1.6f)
                curveToRelative(0.32f, 0.32f, 0.32f, 0.64f, 0.08f, 0.88f)
                lineToRelative(-3.53f, 3.55f)
                curveToRelative(-0.24f, 0.24f, -0.64f, 0.32f, -0.88f, 0.08f)
                close()
                moveTo(160.69f, 294.14f)
                lineTo(166.1f, 299.03f)
                lineToRelative(-3.45f, 3.47f)
                lineToRelative(-5.01f, -5.45f)
                lineToRelative(2.97f, -2.99f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(158.95f, 300.75f)
                lineToRelative(2.74f, 2.73f)
                lineToRelative(-3.21f, 3.23f)
                lineToRelative(-2.74f, -2.73f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(153.08f, 302.46f)
                lineToRelative(0.96f, -0.97f)
                lineToRelative(0.81f, 0.8f)
                lineToRelative(0.72f, -0.73f)
                lineToRelative(-0.81f, -0.8f)
                lineToRelative(0.96f, -0.97f)
                lineToRelative(0.81f, 0.8f)
                lineToRelative(0.72f, -0.73f)
                lineToRelative(-0.81f, -0.8f)
                lineToRelative(0.96f, -0.97f)
                lineToRelative(1.61f, 1.6f)
                curveToRelative(0.32f, 0.32f, 0.32f, 0.64f, 0.08f, 0.88f)
                lineToRelative(-3.45f, 3.47f)
                arcToRelative(0.68f, 0.68f, 89.8f, false, true, -0.97f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(161.58f, 293.9f)
                lineToRelative(0.96f, -0.97f)
                lineToRelative(0.81f, 0.8f)
                lineToRelative(0.72f, -0.73f)
                lineToRelative(-0.81f, -0.8f)
                lineToRelative(0.96f, -0.97f)
                lineToRelative(0.81f, 0.8f)
                lineToRelative(0.72f, -0.73f)
                lineToRelative(-0.81f, -0.8f)
                lineToRelative(0.96f, -0.97f)
                lineToRelative(1.61f, 1.6f)
                curveToRelative(0.32f, 0.32f, 0.32f, 0.64f, 0.08f, 0.88f)
                lineToRelative(-3.45f, 3.47f)
                arcToRelative(0.68f, 0.68f, 89.8f, false, true, -0.97f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(160.46f, 297.6f)
                curveToRelative(-0.48f, -0.48f, 0.24f, -1.21f, 0.72f, -0.73f)
                lineToRelative(1.29f, 1.28f)
                lineToRelative(-0.72f, 0.73f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(157.99f, 303.65f)
                curveToRelative(-0.48f, -0.48f, 0.16f, -1.13f, 0.64f, -0.65f)
                lineToRelative(0.97f, 0.96f)
                lineToRelative(-0.64f, 0.65f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(166.49f, 295.09f)
                curveToRelative(-0.48f, -0.48f, 0.16f, -1.13f, 0.64f, -0.65f)
                lineToRelative(0.97f, 0.96f)
                lineToRelative(-0.64f, 0.65f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(200.1f, 204.07f)
                curveToRelative(0.11f, -6.26f, 4.55f, -7.74f, 4.55f, -7.74f)
                curveToRelative(0.11f, 0.0f, 4.89f, 1.59f, 4.89f, 7.85f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveToRelative(196.23f, 196.45f)
                lineToRelative(-0.8f, 7.17f)
                lineToRelative(4.78f, 0.0f)
                curveToRelative(0.0f, -5.92f, 4.55f, -6.83f, 4.55f, -6.83f)
                curveToRelative(0.11f, 0.0f, 4.55f, 1.25f, 4.66f, 6.83f)
                lineToRelative(4.78f, 0.0f)
                lineToRelative(-0.91f, -7.28f)
                close()
                moveTo(195.1f, 203.73f)
                lineToRelative(19.34f, 0.0f)
                curveToRelative(0.34f, 0.0f, 0.68f, 0.34f, 0.68f, 0.8f)
                curveToRelative(0.0f, 0.57f, -0.34f, 0.91f, -0.68f, 0.91f)
                lineToRelative(-19.34f, 0.0f)
                curveToRelative(-0.34f, 0.0f, -0.68f, -0.34f, -0.68f, -0.91f)
                curveToRelative(0.0f, -0.46f, 0.34f, -0.8f, 0.8f, -0.8f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(202.15f, 203.62f)
                curveToRelative(0.0f, -3.75f, 2.62f, -4.78f, 2.62f, -4.78f)
                reflectiveCurveToRelative(2.62f, 1.14f, 2.62f, 4.78f)
                lineTo(202.15f, 203.62f)
                moveToRelative(-6.6f, -10.24f)
                lineToRelative(18.55f, 0.0f)
                curveToRelative(0.34f, 0.0f, 0.68f, 0.46f, 0.68f, 0.91f)
                curveToRelative(0.0f, 0.34f, -0.34f, 0.68f, -0.68f, 0.68f)
                lineToRelative(-18.55f, 0.0f)
                curveToRelative(-0.34f, 0.0f, -0.68f, -0.34f, -0.68f, -0.8f)
                curveToRelative(0.0f, -0.34f, 0.34f, -0.68f, 0.68f, -0.68f)
                close()
                moveTo(196.01f, 195.08f)
                lineTo(213.53f, 195.08f)
                curveToRelative(0.34f, 0.0f, 0.68f, 0.34f, 0.68f, 0.8f)
                curveToRelative(0.0f, 0.46f, -0.34f, 0.8f, -0.68f, 0.8f)
                lineToRelative(-17.64f, 0.0f)
                curveToRelative(-0.46f, 0.0f, -0.68f, -0.34f, -0.68f, -0.8f)
                curveToRelative(0.0f, -0.46f, 0.23f, -0.8f, 0.68f, -0.8f)
                close()
                moveTo(201.7f, 183.02f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 0.91f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -0.91f)
                lineToRelative(1.48f, 0.0f)
                lineToRelative(0.0f, 1.02f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -1.14f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 2.28f)
                curveToRelative(0.0f, 0.46f, -0.23f, 0.68f, -0.57f, 0.68f)
                lineToRelative(-5.01f, 0.0f)
                curveToRelative(-0.34f, 0.0f, -0.68f, -0.23f, -0.68f, -0.57f)
                close()
                moveTo(206.93f, 186.1f)
                lineTo(207.27f, 193.38f)
                lineToRelative(-4.89f, 0.0f)
                lineToRelative(0.34f, -7.4f)
                lineToRelative(4.21f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(201.01f, 189.51f)
                lineToRelative(0.0f, 3.87f)
                lineToRelative(-4.55f, 0.0f)
                lineToRelative(0.0f, -3.87f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(213.07f, 189.51f)
                lineToRelative(0.0f, 3.87f)
                lineToRelative(-4.55f, 0.0f)
                lineToRelative(0.0f, -3.87f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(195.66f, 186.55f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 1.14f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -1.14f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 1.14f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -1.14f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 2.28f)
                curveToRelative(0.0f, 0.46f, -0.23f, 0.68f, -0.57f, 0.68f)
                lineToRelative(-4.89f, 0.0f)
                arcToRelative(0.68f, 0.68f, 45.0f, false, true, -0.68f, -0.68f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(207.73f, 186.55f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 1.14f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -1.14f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 1.14f)
                lineToRelative(1.02f, 0.0f)
                lineToRelative(0.0f, -1.14f)
                lineToRelative(1.37f, 0.0f)
                lineToRelative(0.0f, 2.28f)
                curveToRelative(0.0f, 0.46f, -0.23f, 0.68f, -0.57f, 0.68f)
                lineToRelative(-4.89f, 0.0f)
                arcToRelative(0.68f, 0.68f, 45.0f, false, true, -0.68f, -0.68f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(204.31f, 188.37f)
                curveToRelative(0.0f, -0.68f, 1.02f, -0.68f, 1.02f, 0.0f)
                lineToRelative(0.0f, 1.82f)
                lineToRelative(-1.02f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(198.28f, 190.87f)
                curveToRelative(0.0f, -0.68f, 0.91f, -0.68f, 0.91f, 0.0f)
                lineToRelative(0.0f, 1.37f)
                lineToRelative(-0.91f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(210.34f, 190.87f)
                curveToRelative(0.0f, -0.68f, 0.91f, -0.68f, 0.91f, 0.0f)
                lineToRelative(0.0f, 1.37f)
                lineToRelative(-0.91f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(261.2f, 204.07f)
                curveToRelative(-0.11f, -6.26f, -4.55f, -7.74f, -4.55f, -7.74f)
                curveToRelative(-0.11f, 0.0f, -4.89f, 1.59f, -4.89f, 7.85f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveToRelative(265.07f, 196.45f)
                lineToRelative(0.8f, 7.17f)
                lineToRelative(-4.78f, 0.0f)
                curveToRelative(-0.0f, -5.92f, -4.55f, -6.83f, -4.55f, -6.83f)
                curveToRelative(-0.11f, 0.0f, -4.55f, 1.25f, -4.66f, 6.83f)
                lineToRelative(-4.78f, 0.0f)
                lineToRelative(0.91f, -7.28f)
                close()
                moveTo(266.21f, 203.73f)
                lineToRelative(-19.34f, 0.0f)
                curveToRelative(-0.34f, 0.0f, -0.68f, 0.34f, -0.68f, 0.8f)
                curveToRelative(-0.0f, 0.57f, 0.34f, 0.91f, 0.68f, 0.91f)
                lineToRelative(19.34f, 0.0f)
                curveToRelative(0.34f, 0.0f, 0.68f, -0.34f, 0.68f, -0.91f)
                curveToRelative(-0.0f, -0.46f, -0.34f, -0.8f, -0.8f, -0.8f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(259.15f, 203.62f)
                curveToRelative(-0.0f, -3.75f, -2.62f, -4.78f, -2.62f, -4.78f)
                reflectiveCurveToRelative(-2.62f, 1.14f, -2.62f, 4.78f)
                lineTo(259.15f, 203.62f)
                moveToRelative(6.6f, -10.24f)
                lineToRelative(-18.55f, 0.0f)
                curveToRelative(-0.34f, 0.0f, -0.68f, 0.46f, -0.68f, 0.91f)
                curveToRelative(-0.0f, 0.34f, 0.34f, 0.68f, 0.68f, 0.68f)
                lineToRelative(18.55f, 0.0f)
                curveToRelative(0.34f, 0.0f, 0.68f, -0.34f, 0.68f, -0.8f)
                curveToRelative(-0.0f, -0.34f, -0.34f, -0.68f, -0.68f, -0.68f)
                close()
                moveTo(265.3f, 195.08f)
                lineTo(247.78f, 195.08f)
                curveToRelative(-0.34f, 0.0f, -0.68f, 0.34f, -0.68f, 0.8f)
                curveToRelative(-0.0f, 0.46f, 0.34f, 0.8f, 0.68f, 0.8f)
                lineToRelative(17.64f, 0.0f)
                curveToRelative(0.46f, 0.0f, 0.68f, -0.34f, 0.68f, -0.8f)
                curveToRelative(-0.0f, -0.46f, -0.23f, -0.8f, -0.68f, -0.8f)
                close()
                moveTo(259.61f, 183.02f)
                lineToRelative(-1.37f, 0.0f)
                lineToRelative(-0.0f, 0.91f)
                lineToRelative(-1.02f, 0.0f)
                lineToRelative(-0.0f, -0.91f)
                lineToRelative(-1.48f, 0.0f)
                lineToRelative(-0.0f, 1.02f)
                lineToRelative(-1.02f, 0.0f)
                lineToRelative(-0.0f, -1.14f)
                lineToRelative(-1.37f, 0.0f)
                lineToRelative(-0.0f, 2.28f)
                curveToRelative(-0.0f, 0.46f, 0.23f, 0.68f, 0.57f, 0.68f)
                lineToRelative(5.01f, 0.0f)
                curveToRelative(0.34f, 0.0f, 0.68f, -0.23f, 0.68f, -0.57f)
                close()
                moveTo(254.37f, 186.1f)
                lineTo(254.03f, 193.38f)
                lineToRelative(4.89f, 0.0f)
                lineToRelative(-0.34f, -7.4f)
                lineToRelative(-4.21f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(260.29f, 189.51f)
                lineToRelative(-0.0f, 3.87f)
                lineToRelative(4.55f, 0.0f)
                lineToRelative(-0.0f, -3.87f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(265.64f, 186.55f)
                lineToRelative(-1.37f, 0.0f)
                lineToRelative(-0.0f, 1.14f)
                lineToRelative(-1.02f, 0.0f)
                lineToRelative(-0.0f, -1.14f)
                lineToRelative(-1.37f, 0.0f)
                lineToRelative(-0.0f, 1.14f)
                lineToRelative(-1.02f, 0.0f)
                lineToRelative(-0.0f, -1.14f)
                lineToRelative(-1.37f, 0.0f)
                lineToRelative(-0.0f, 2.28f)
                curveToRelative(-0.0f, 0.46f, 0.23f, 0.68f, 0.57f, 0.68f)
                lineToRelative(4.89f, 0.0f)
                arcToRelative(0.68f, 0.68f, 45.0f, false, false, 0.68f, -0.68f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(253.58f, 186.55f)
                lineToRelative(-1.37f, 0.0f)
                lineToRelative(-0.0f, 1.14f)
                lineToRelative(-1.02f, 0.0f)
                lineToRelative(-0.0f, -1.14f)
                lineToRelative(-1.37f, 0.0f)
                lineToRelative(-0.0f, 1.14f)
                lineToRelative(-1.02f, 0.0f)
                lineToRelative(-0.0f, -1.14f)
                lineToRelative(-1.37f, 0.0f)
                lineToRelative(-0.0f, 2.28f)
                curveToRelative(-0.0f, 0.46f, 0.23f, 0.68f, 0.57f, 0.68f)
                lineToRelative(4.89f, 0.0f)
                arcToRelative(0.68f, 0.68f, 0.0f, false, false, 0.68f, -0.68f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(256.99f, 188.37f)
                curveToRelative(-0.0f, -0.68f, -1.02f, -0.68f, -1.02f, 0.0f)
                lineToRelative(-0.0f, 1.82f)
                lineToRelative(1.02f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(263.02f, 190.87f)
                curveToRelative(-0.0f, -0.68f, -0.91f, -0.68f, -0.91f, 0.0f)
                lineToRelative(-0.0f, 1.37f)
                lineToRelative(0.91f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(250.96f, 190.87f)
                curveToRelative(-0.0f, -0.68f, -0.91f, -0.68f, -0.91f, 0.0f)
                lineToRelative(-0.0f, 1.37f)
                lineToRelative(0.91f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(261.2f, 256.75f)
                curveToRelative(-0.11f, -6.26f, -4.55f, -7.74f, -4.55f, -7.74f)
                curveToRelative(-0.11f, 0.0f, -4.89f, 1.59f, -4.89f, 7.85f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveToRelative(265.07f, 249.13f)
                lineToRelative(0.8f, 7.17f)
                lineToRelative(-4.78f, 0.0f)
                curveToRelative(-0.0f, -5.92f, -4.55f, -6.83f, -4.55f, -6.83f)
                curveToRelative(-0.11f, 0.0f, -4.55f, 1.25f, -4.66f, 6.83f)
                lineToRelative(-4.78f, 0.0f)
                lineToRelative(0.91f, -7.28f)
                close()
                moveTo(266.21f, 256.41f)
                lineToRelative(-19.34f, 0.0f)
                curveToRelative(-0.34f, 0.0f, -0.68f, 0.34f, -0.68f, 0.8f)
                curveToRelative(-0.0f, 0.57f, 0.34f, 0.91f, 0.68f, 0.91f)
                lineToRelative(19.34f, 0.0f)
                curveToRelative(0.34f, 0.0f, 0.68f, -0.34f, 0.68f, -0.91f)
                curveToRelative(-0.0f, -0.46f, -0.34f, -0.8f, -0.8f, -0.8f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(259.15f, 256.3f)
                curveToRelative(-0.0f, -3.75f, -2.62f, -4.78f, -2.62f, -4.78f)
                reflectiveCurveToRelative(-2.62f, 1.14f, -2.62f, 4.78f)
                lineTo(259.15f, 256.3f)
                moveToRelative(6.6f, -10.24f)
                lineToRelative(-18.55f, 0.0f)
                curveToRelative(-0.34f, 0.0f, -0.68f, 0.46f, -0.68f, 0.91f)
                curveToRelative(-0.0f, 0.34f, 0.34f, 0.68f, 0.68f, 0.68f)
                lineToRelative(18.55f, 0.0f)
                curveToRelative(0.34f, 0.0f, 0.68f, -0.34f, 0.68f, -0.8f)
                curveToRelative(-0.0f, -0.34f, -0.34f, -0.68f, -0.68f, -0.68f)
                close()
                moveTo(265.3f, 247.76f)
                lineTo(247.78f, 247.76f)
                curveToRelative(-0.34f, 0.0f, -0.68f, 0.34f, -0.68f, 0.8f)
                curveToRelative(-0.0f, 0.46f, 0.34f, 0.8f, 0.68f, 0.8f)
                lineToRelative(17.64f, 0.0f)
                curveToRelative(0.46f, 0.0f, 0.68f, -0.34f, 0.68f, -0.8f)
                curveToRelative(-0.0f, -0.46f, -0.23f, -0.8f, -0.68f, -0.8f)
                close()
                moveTo(259.61f, 235.7f)
                lineToRelative(-1.37f, 0.0f)
                lineToRelative(-0.0f, 0.91f)
                lineToRelative(-1.02f, 0.0f)
                lineToRelative(-0.0f, -0.91f)
                lineToRelative(-1.48f, 0.0f)
                lineToRelative(-0.0f, 1.02f)
                lineToRelative(-1.02f, 0.0f)
                lineToRelative(-0.0f, -1.14f)
                lineToRelative(-1.37f, 0.0f)
                lineToRelative(-0.0f, 2.28f)
                curveToRelative(-0.0f, 0.46f, 0.23f, 0.68f, 0.57f, 0.68f)
                lineToRelative(5.01f, 0.0f)
                curveToRelative(0.34f, 0.0f, 0.68f, -0.23f, 0.68f, -0.57f)
                close()
                moveTo(254.37f, 238.78f)
                lineTo(254.03f, 246.06f)
                lineToRelative(4.89f, 0.0f)
                lineToRelative(-0.34f, -7.4f)
                lineToRelative(-4.21f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(260.29f, 242.19f)
                lineToRelative(-0.0f, 3.87f)
                lineToRelative(4.55f, 0.0f)
                lineToRelative(-0.0f, -3.87f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(265.64f, 239.23f)
                lineToRelative(-1.37f, 0.0f)
                lineToRelative(-0.0f, 1.14f)
                lineToRelative(-1.02f, 0.0f)
                lineToRelative(-0.0f, -1.14f)
                lineToRelative(-1.37f, 0.0f)
                lineToRelative(-0.0f, 1.14f)
                lineToRelative(-1.02f, 0.0f)
                lineToRelative(-0.0f, -1.14f)
                lineToRelative(-1.37f, 0.0f)
                lineToRelative(-0.0f, 2.28f)
                curveToRelative(-0.0f, 0.46f, 0.23f, 0.68f, 0.57f, 0.68f)
                lineToRelative(4.89f, 0.0f)
                arcToRelative(0.68f, 0.68f, 45.0f, false, false, 0.68f, -0.68f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(253.58f, 239.23f)
                lineToRelative(-1.37f, 0.0f)
                lineToRelative(-0.0f, 1.14f)
                lineToRelative(-1.02f, 0.0f)
                lineToRelative(-0.0f, -1.14f)
                lineToRelative(-1.37f, 0.0f)
                lineToRelative(-0.0f, 1.14f)
                lineToRelative(-1.02f, 0.0f)
                lineToRelative(-0.0f, -1.14f)
                lineToRelative(-1.37f, 0.0f)
                lineToRelative(-0.0f, 2.28f)
                curveToRelative(-0.0f, 0.46f, 0.23f, 0.68f, 0.57f, 0.68f)
                lineToRelative(4.89f, 0.0f)
                arcToRelative(0.68f, 0.68f, 0.0f, false, false, 0.68f, -0.68f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(256.99f, 241.05f)
                curveToRelative(-0.0f, -0.68f, -1.02f, -0.68f, -1.02f, 0.0f)
                lineToRelative(-0.0f, 1.82f)
                lineToRelative(1.02f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(263.02f, 243.55f)
                curveToRelative(-0.0f, -0.68f, -0.91f, -0.68f, -0.91f, 0.0f)
                lineToRelative(-0.0f, 1.37f)
                lineToRelative(0.91f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000001)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 0.57f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(250.96f, 243.55f)
                curveToRelative(-0.0f, -0.68f, -0.91f, -0.68f, -0.91f, 0.0f)
                lineToRelative(-0.0f, 1.37f)
                lineToRelative(0.91f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(196.35f, 258.69f)
                arcToRelative(9.44f, 9.44f, 0.0f, false, false, 2.5f, 6.49f)
                arcToRelative(8.19f, 8.19f, 0.0f, false, false, 6.03f, 2.73f)
                curveToRelative(2.39f, 0.0f, 4.55f, -1.14f, 6.03f, -2.73f)
                arcToRelative(9.44f, 9.44f, 0.0f, false, false, 2.5f, -6.49f)
                lineToRelative(0.0f, -12.29f)
                lineToRelative(-17.07f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(200.33f, 251.06f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(209.77f, 251.06f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(204.99f, 255.61f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(200.33f, 260.39f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(209.77f, 260.39f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(196.35f, 229.1f)
                arcToRelative(9.44f, 9.44f, 0.0f, false, false, 2.5f, 6.49f)
                arcToRelative(8.19f, 8.19f, 0.0f, false, false, 6.03f, 2.73f)
                curveToRelative(2.39f, 0.0f, 4.55f, -1.14f, 6.03f, -2.73f)
                arcToRelative(9.44f, 9.44f, 0.0f, false, false, 2.5f, -6.49f)
                lineToRelative(0.0f, -12.29f)
                lineToRelative(-17.07f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(200.33f, 221.48f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(209.77f, 221.48f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(204.99f, 226.03f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(200.33f, 230.81f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(209.77f, 230.81f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(172.68f, 258.69f)
                arcToRelative(9.44f, 9.44f, 135.0f, false, false, 2.5f, 6.49f)
                arcToRelative(8.19f, 8.19f, 0.0f, false, false, 6.03f, 2.73f)
                curveToRelative(2.39f, 0.0f, 4.55f, -1.14f, 6.03f, -2.73f)
                arcToRelative(9.44f, 9.44f, 45.0f, false, false, 2.5f, -6.49f)
                lineToRelative(0.0f, -12.29f)
                lineToRelative(-17.07f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(176.66f, 251.06f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(186.11f, 251.06f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(181.33f, 255.61f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(176.66f, 260.39f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(186.11f, 260.39f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(220.01f, 258.69f)
                arcToRelative(9.44f, 9.44f, 0.0f, false, false, 2.5f, 6.49f)
                arcToRelative(8.19f, 8.19f, 0.0f, false, false, 6.03f, 2.73f)
                curveToRelative(2.39f, 0.0f, 4.55f, -1.14f, 6.03f, -2.73f)
                arcToRelative(9.44f, 9.44f, 0.0f, false, false, 2.5f, -6.49f)
                lineToRelative(0.0f, -12.29f)
                lineToRelative(-17.07f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(224.0f, 251.06f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(233.44f, 251.06f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(228.66f, 255.61f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(224.0f, 260.39f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(233.44f, 260.39f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(196.35f, 288.04f)
                arcToRelative(9.44f, 9.44f, 0.0f, false, false, 2.5f, 6.49f)
                arcToRelative(8.19f, 8.19f, 0.0f, false, false, 6.03f, 2.73f)
                curveToRelative(2.39f, 0.0f, 4.55f, -1.14f, 6.03f, -2.73f)
                arcToRelative(9.44f, 9.44f, 0.0f, false, false, 2.5f, -6.49f)
                lineToRelative(0.0f, -12.29f)
                lineToRelative(-17.07f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(200.33f, 280.42f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(209.77f, 280.42f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(204.99f, 284.97f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(200.33f, 289.75f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(209.77f, 289.75f)
                moveToRelative(-1.71f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, 3.41f, 0.0f)
                arcToRelative(1.71f, 1.71f, 0.0f, true, true, -3.41f, 0.0f)
            }
        }
        .build()
        return _pt!!
    }

private var _pt: ImageVector? = null
