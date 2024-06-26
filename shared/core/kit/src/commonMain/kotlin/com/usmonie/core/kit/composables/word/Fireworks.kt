import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import kotlinx.coroutines.delay
import kotlin.math.*
import kotlin.random.Random

@Composable
fun Fireworks(modifier: Modifier = Modifier) {
	val rockets = remember { mutableStateListOf<Rocket>() }
	val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Magenta, Color.Cyan)

	LaunchedEffect(Unit) {
		while (true) {
			if (rockets.size < 25) {
				rockets.add(
					Rocket(
						startX = Random.nextFloat(),
						startY = 1f,
						targetX = Random.nextFloat(),
						targetY = Random.nextFloat() * 0.6f + 0.1f,
						color = colors.random(),
						launchSide = LaunchSide.entries.toTypedArray().random()
					)
				)
				delay(120)
			}
		}
	}

	Canvas(modifier = modifier.fillMaxSize()) {
		rockets.fastForEach { rocket ->
			drawRocket(rocket)
		}
	}

	LaunchedEffect(Unit) {
		while (true) {
			delay(12) // примерно 60 FPS
			rockets.removeAll { it.update() == null }
			rockets.fastForEach { it.update() }
		}
	}
}

enum class LaunchSide { LEFT, BOTTOM, RIGHT }

class Rocket(
	val startX: Float,
	val startY: Float,
	val targetX: Float,
	val targetY: Float,
	val color: Color,
	val launchSide: LaunchSide
) {
	var progress: Float = 0f
	var state: RocketState = RocketState.FLYING
	var explosionProgress: Float = 0f
	var velocity: Float = Random.nextFloat() * 0.02f + 0.01f
	private val gravity: Float = 0.0001f

	fun update(): Rocket? {
		return when (state) {
			RocketState.FLYING -> {
				progress += velocity
				velocity -= gravity
				if (progress >= 1f || velocity <= 0) {
					state = RocketState.EXPLODING
				}
				this
			}

			RocketState.EXPLODING -> {
				explosionProgress += 0.05f
				if (explosionProgress >= 1f) {
					state = RocketState.FADING
				}
				this
			}

			RocketState.FADING -> {
				explosionProgress -= 0.01f
				if (explosionProgress <= 0f) null else this
			}
		}
	}

	fun currentPosition(): Pair<Float, Float> {
		val t = progress
		return when (launchSide) {
			LaunchSide.BOTTOM -> {
				val x = startX + (targetX - startX) * t
				val y = startY + (targetY - startY) * t - sin(PI * t).toFloat() * 0.2f + 0.5f * gravity * t * t
				x to y
			}

			LaunchSide.LEFT -> {
				val x = -0.2f + (targetX + 0.2f) * t
				val y = 1f + (targetY - 1f) * t - sin(PI * t).toFloat() * 0.2f + 0.5f * gravity * t * t
				x to y
			}

			LaunchSide.RIGHT -> {
				val x = 1.2f + (targetX - 1.2f) * t
				val y = 1f + (targetY - 1f) * t - sin(PI * t).toFloat() * 0.2f + 0.5f * gravity * t * t
				x to y
			}
		}
	}
}

enum class RocketState { FLYING, EXPLODING, FADING }

fun DrawScope.drawRocket(rocket: Rocket) {
	when (rocket.state) {
		RocketState.FLYING -> {
			val (currentX, currentY) = rocket.currentPosition()
			val path = Path()
			path.moveTo(currentX * size.width, currentY * size.height)

			// Рисуем хвост ракеты
			for (i in 1..10) {
				val t = rocket.progress - i * 0.02f
				if (t < 0) break
				val (x, y) = when (rocket.launchSide) {
					LaunchSide.BOTTOM -> {
						val x = rocket.startX + (rocket.targetX - rocket.startX) * t
						val y =
							rocket.startY + (rocket.targetY - rocket.startY) * t - sin(PI * t).toFloat() * 0.2f + 0.5f * 0.0001f * t * t
						x to y
					}

					LaunchSide.LEFT -> {
						val x = -0.2f + (rocket.targetX + 0.2f) * t
						val y = 1f + (rocket.targetY - 1f) * t - sin(PI * t).toFloat() * 0.2f + 0.5f * 0.0001f * t * t
						x to y
					}

					LaunchSide.RIGHT -> {
						val x = 1.2f + (rocket.targetX - 1.2f) * t
						val y = 1f + (rocket.targetY - 1f) * t - sin(PI * t).toFloat() * 0.2f + 0.5f * 0.0001f * t * t
						x to y
					}
				}
				path.lineTo(x * size.width, y * size.height)
			}

			// Рисуем градиентный хвост
			for (i in 0..10) {
				val progress = i / 10f
				val width = (1 - progress) * 6.dp.toPx()
				val alpha = (1 - progress) * 0.5f
				drawPath(
					path = path,
					color = rocket.color.copy(alpha = alpha.coerceIn(0f, 1f)),
					style = androidx.compose.ui.graphics.drawscope.Stroke(width = width)
				)
			}

			drawCircle(
				color = rocket.color,
				radius = 5.dp.toPx(),
				center = Offset(currentX * size.width, currentY * size.height)
			)
		}

		RocketState.EXPLODING, RocketState.FADING -> {
			// Рисуем взрыв
			val maxRadius = 52.dp.toPx()
			val currentRadius = if (rocket.state == RocketState.EXPLODING) {
				maxRadius * rocket.explosionProgress
			} else {
				maxRadius * (1 - rocket.explosionProgress)
			}
			drawCircle(
				color = rocket.color.copy(alpha = (1 - rocket.explosionProgress).coerceIn(0f, 1f)),
				radius = currentRadius,
				center = Offset(rocket.targetX * size.width, rocket.targetY * size.height)
			)
		}
	}
}
