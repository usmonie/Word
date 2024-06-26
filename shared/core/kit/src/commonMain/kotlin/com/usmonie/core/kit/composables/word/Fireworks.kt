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
import androidx.compose.ui.util.fastAll
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import com.usmonie.core.kit.tools.getScreenSize
import kotlinx.coroutines.*
import kotlinx.datetime.Clock
import kotlin.math.*
import kotlin.random.Random

data class Particle(
	var position: Offset,
	var velocity: Offset,
	var color: Color,
	var size: Float,
	var alpha: Float = 1f,
	var rotationAngle: Float = 0f,
	var shouldRotate: Boolean = false,
	var trail: MutableList<Offset> = mutableListOf(),
	var isSmoke: Boolean = false,
	var isLaunchTrail: Boolean = false
)

class ParticlePool(private val poolSize: Int) {
	private val particles = MutableList(poolSize) { createParticle() }
	private var currentIndex = 0

	fun getParticle(): Particle {
		val particle = particles[currentIndex]
		currentIndex = (currentIndex + 1) % poolSize
		return particle.apply { reset() }
	}

	private fun createParticle() = Particle(Offset.Zero, Offset.Zero, Color.White, 0f)

	private fun Particle.reset() {
		position = Offset.Zero
		velocity = Offset.Zero
		color = Color.White
		size = 0f
		alpha = 1f
		rotationAngle = 0f
		shouldRotate = false
		trail.clear()
		isSmoke = false
	}
}

class Firework(
	private val initialPosition: Offset,
	private val explosionPosition: Offset,
	private val particleCount: Int,
	private val fireworkType: FireworkType,
	private val particlePool: ParticlePool
) {
	var particles = mutableListOf<Particle>()
	var hasExploded = false
	var explosionTime = 0L
	var explosionCount = 0
	private val maxExplosions = 3

	val wasEnded: Boolean
		get() = explosionCount >= maxExplosions && particles.fastAll { it.alpha <= 0.1f }

	init {
		particles.add(
			particlePool.getParticle()
				.apply {
					position = initialPosition
					velocity = calculateLaunchVelocity()
					color = Color.White
					size = 5f
				}
		)
	}

	fun update(currentTime: Long) {
		if (!hasExploded) {
			val launchParticle = particles[0]
			launchParticle.position += launchParticle.velocity
			if (launchParticle.position.y <= explosionPosition.y) {
				explode(currentTime)
			}
		} else {
			updateParticles(currentTime)
			if (currentTime - explosionTime > 1000 && explosionCount < maxExplosions) {
				explode(currentTime)
			}
		}
	}

	private fun explode(currentTime: Long) {
		hasExploded = true
		explosionTime = currentTime
		explosionCount++
		particles.clear()
		for (i in 0 until particleCount) {
			particles.add(createExplosionParticle())
		}
		// Add smoke particles
//		for (i in 0 until particleCount / 2) {
//			particles.add(createSmokeParticle())
//		}
	}

	private fun updateParticles(currentTime: Long) {
		particles.fastForEach { particle ->
			// Update trail
			particle.trail.add(particle.position)
			if (particle.trail.size > 10) particle.trail.removeAt(0)

			// Apply gravity
			particle.velocity += Offset(0f, if (particle.isSmoke) 0.5f else 9.8f * 0.016f)

			// Apply velocity decay
			particle.velocity *= if (particle.isSmoke) 0.98f else 0.95f

			// Update position
			particle.position += particle.velocity

			// Decrease brightness
			particle.alpha *= if (particle.isSmoke) 0.97f else 0.95f

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
		return particlePool.getParticle().apply {
			position = explosionPosition
			velocity = Offset(cos(angle) * speed, sin(angle) * speed)
			color = getRandomColor()
			size = Random.nextFloat() * 9f + 1f
			shouldRotate = Random.nextFloat() < 0.3f
		}
	}

	private fun createSmokeParticle(): Particle {
		val angle = Random.nextFloat() * 2 * PI.toFloat()
		val speed = Random.nextFloat() * 2f + 0.5f
		return particlePool.getParticle().apply {
			position = explosionPosition
			velocity = Offset(cos(angle) * speed, sin(angle) * speed - 2f)
			color = Color.Gray
			size = Random.nextFloat() * 15f + 5f
			alpha = 0.7f
			isSmoke = true
		}
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
			// Draw trail
			particle.trail.fastForEachIndexed { index, trailPosition ->
				val trailAlpha = particle.alpha * (index.toFloat() / particle.trail.size)
				drawScope.drawCircle(
					color = particle.color,
					radius = particle.size * 0.5f,
					center = trailPosition,
					alpha = trailAlpha
				)
			}

			// Draw particle
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
fun FireworksSimulation(fireworksCount: Int = Int.MAX_VALUE) {
	val fireworks = remember { mutableStateListOf<Firework>() }
	var frameCount by remember { mutableStateOf(0L) }
	val particlePool = remember { ParticlePool(10000) }

	val size = getScreenSize()
	val width = with(LocalDensity.current) { size.first.toPx() }
	val height = with(LocalDensity.current) { size.second.toPx() }

	LaunchedEffect(Unit) {
		while (true) {
			if (fireworks.size < fireworksCount) {
				launchFirework(fireworks, width, height, particlePool)
			}
			delay(120)
		}
	}

	LaunchedEffect(Unit) {
		while (true) {
			delay(12) // Targeting approximately 60 FPS
			val currentTime = Clock.System.now().epochSeconds

			fireworks.fastForEach { it.update(currentTime) }
			fireworks.removeAll { it.wasEnded }

			// Realistic timing for automatic firework launches
			if (Random.nextFloat() < 0.05f && fireworks.size < 10) {
				launchFirework(fireworks, width, height, particlePool)
			}

			frameCount++  // Increment frame count to force recomposition
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

private fun launchFirework(fireworks: MutableList<Firework>, width: Float, height: Float, particlePool: ParticlePool) {
	fireworks.add(
		Firework(
			initialPosition = Offset(width * Random.nextFloat(), height),
			explosionPosition = Offset(
				width * Random.nextFloat(),
				height * Random.nextFloat() * 0.6f
			),
			particleCount = 100,
			fireworkType = FireworkType.entries.toTypedArray().random(),
			particlePool = particlePool
		)
	)
}