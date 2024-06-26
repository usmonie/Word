import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.usmonie.core.kit.tools.getScreenSize
import kotlinx.coroutines.*
import kotlinx.datetime.Clock
import kotlin.math.*
import kotlin.random.Random


// Particle class to represent individual firework particles
@Stable
data class Particle(
	var position: Offset,
	var velocity: Offset,
	var color: Color,
	var size: Float,
	var alpha: Float = 1f,
	var rotationAngle: Float = 0f,
	var shouldRotate: Boolean = false
)

// Firework class to manage a group of particles
@Stable
class Firework(
	private val initialPosition: Offset,
	private val explosionPosition: Offset,
	private val particleCount: Int,
	private val fireworkType: FireworkType
) {
	var particles = mutableListOf<Particle>()
	var hasExploded = false
	var explosionTime = 0L

	init {
		// Initialize launch particle
		particles.add(
			Particle(
				position = initialPosition,
				velocity = calculateLaunchVelocity(),
				color = Color.White,
				size = 5f
			)
		)
	}

	fun update(currentTime: Long) {
		if (!hasExploded) {
			val launchParticle = particles[0]
			launchParticle.position += launchParticle.velocity
			if (launchParticle.position.y <= explosionPosition.y) {
				explode()
				explosionTime = currentTime
			}
		} else {
			updateParticles(currentTime)
		}
	}

	private fun explode() {
		hasExploded = true
		particles.clear()
		for (i in 0 until particleCount) {
			particles.add(createExplosionParticle())
		}
	}

	private fun updateParticles(currentTime: Long) {
		particles.forEachIndexed { index, particle ->
			// Apply gravity
			particle.velocity += Offset(0f, 9.8f * 0.016f) // Assuming 60 FPS

			val explosionDiff = (currentTime - explosionTime)
			// Apply velocity decay
			if (explosionDiff % 100 == 0L) {
				particle.velocity *= 0.95f
			}

			// Update position
			particle.position += particle.velocity

			// Decrease brightness
			if (explosionDiff % 500 == 0L) {
				particle.alpha *= 0.9f
			}

			// Apply rotation
			if (particle.shouldRotate) {
				particle.rotationAngle += 45f * 0.016f // 45 degrees per second
			}
		}

		particles.removeAll { it.alpha < 0.1f }
	}

	private fun calculateLaunchVelocity(): Offset {
		val dx = explosionPosition.x - initialPosition.x
		val dy = explosionPosition.y - initialPosition.y
		val time = sqrt(2 * abs(dy) / 9.8f)
		return Offset(dx / time, -sqrt(2 * 9.8f * abs(dy)))
	}

	private fun createExplosionParticle(): Particle {
		val angle = Random.nextFloat() * 2 * PI.toFloat()
		val speed = Random.nextFloat() * 5f + 2f
		return Particle(
			position = explosionPosition,
			velocity = Offset(cos(angle) * speed, sin(angle) * speed),
			color = getRandomColor(),
			size = Random.nextFloat() * 9f + 1f,
			shouldRotate = Random.nextFloat() < 0.3f
		)
	}

	private fun getRandomColor(): Color {
		return when (fireworkType) {
			FireworkType.CHRYSANTHEMUM -> Color(Random.nextInt(256), Random.nextInt(256), 0)
			FireworkType.PALM -> Color(0, Random.nextInt(256), 0)
			FireworkType.FOUNTAIN -> Color(Random.nextInt(256), 0, Random.nextInt(256))
			FireworkType.WILLOW -> Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
			FireworkType.PEONY -> Color(Random.nextInt(256), 0, 0)
		}
	}

	fun draw(drawScope: DrawScope) {
		particles.fastForEach { particle ->
			drawScope.drawCircle(
				color = particle.color,
				radius = particle.size,
				center = particle.position,
				alpha = particle.alpha
			)

			// Draw glow
			drawScope.drawCircle(
				color = particle.color,
				radius = particle.size + 3f,
				center = particle.position,
				alpha = particle.alpha * 0.3f
			)
		}
	}
}

enum class FireworkType {
	CHRYSANTHEMUM, PALM, FOUNTAIN, WILLOW, PEONY
}

@Composable
fun FireworksSimulation(content: @Composable () -> Unit) {
	var lastUpdateTime by remember { mutableStateOf(0L) }

	val fireworks = remember { mutableStateListOf<Firework>() }
	var frameCount by remember { mutableStateOf(0L) }

	val size = getScreenSize()
	val width = with(LocalDensity.current) { size.first.toPx() }
	val height = with(LocalDensity.current) { size.second.toPx() }

	LaunchedEffect(Unit) {
		while (true) {
			delay(12) // Targeting approximately 60 FPS
			val currentTime = Clock.System.now().epochSeconds

			fireworks.fastForEach { it.update(currentTime) }
			fireworks.removeAll { it.particles.isEmpty() }

			lastUpdateTime = currentTime
			frameCount++  // Increment frame count to force recomposition
		}
	}

	LaunchedEffect(Unit) {
		while (true) {
			if (fireworks.size < 25) {
				fireworks.add(
					Firework(
						initialPosition = Offset(width * Random.nextFloat(), height),
						explosionPosition = Offset(
							width * Random.nextFloat(),
							height * Random.nextFloat() * 0.6f
						),
						particleCount = 50,
						fireworkType = FireworkType.entries.toTypedArray().random()
					)
				)
			} else {
				fireworks.removeFirst()
				println("FIREWORK exceeded")
			}
			delay(100)
		}
	}

	Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		Canvas(modifier = Modifier.fillMaxSize()) {
			fireworks.fastForEach {
				frameCount
				it.draw(this)
			}
		}
	}
}