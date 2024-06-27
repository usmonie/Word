package com.usmonie.word.features.games.domain.usecases

import kotlinx.datetime.Clock
import androidx.collection.*
import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.quotes.domain.models.Quote
import com.usmonie.word.features.quotes.domain.repositories.QuotesRepository
import kotlin.math.roundToInt
import kotlin.random.Random

enum class DifficultyLevel {
	BEGINNER, EASY, NORMAL, CHALLENGING, HARD, EXPERT, MASTER
}


data class UserPerformanceMetrics(
	val averageSolveTime: Long,
	val averageHintsUsed: Double,
	val successRate: Double,
	val totalPuzzlesSolved: Int
)

interface GetCryptogramQuoteUseCase :
	CoroutineUseCase<GetCryptogramQuoteUseCase.Param, GetCryptogramQuoteUseCase.CryptogramEncryptedPhrase> {

	data class Param(val language: String = "en")

	data class CryptogramEncryptedPhrase(
		val encryptedPhrase: ObjectList<Word>,
		val encryptedPositions: MutableObjectList<Triple<Int, Int, Int>>,
		val quote: Quote,
		val encryptedPositionsCount: Int,
		val charsCount: MutableScatterMap<Char, Int>,
		val hints: List<Hint>,
		val timeStarted: Long,
		val difficultyLevel: DifficultyLevel
	)

	data class Word(val cells: List<Cell>) {
		val size: Int = cells.size
	}

	data class Cell(
		val symbol: Char,
		val number: Int,
		var state: CellState = CellState.Empty,
	)

	sealed class CellState {
		data object Empty : CellState()
		data object PartiallyGuessed : CellState()
		data object Found : CellState()
//		data object Hinted : CellState()
	}

	data class Hint(val position: Pair<Int, Int>, val letter: Char)
}

internal class GetCryptogramQuoteUseCaseImpl(
	private val quotesRepository: QuotesRepository,
	private val userProgressRepository: UserProgressRepository = UserProgressRepositoryImpl(),
	private val difficultyAdjuster: DifficultyAdjuster = DifficultyAdjuster()
) : GetCryptogramQuoteUseCase {

	override suspend fun invoke(input: GetCryptogramQuoteUseCase.Param): GetCryptogramQuoteUseCase.CryptogramEncryptedPhrase {
		val quote = quotesRepository.getRandomWasntPlayedQuote()
		val userProgress = userProgressRepository.getProgress()
		val difficultyLevel = difficultyAdjuster.calculateDifficultyLevel(userProgress.toUserPerformanceMetrics())
		return encryptQuote(quote, difficultyLevel, input.language)
	}

	private fun encryptQuote(
		quote: Quote,
		difficultyLevel: DifficultyLevel,
		language: String
	): GetCryptogramQuoteUseCase.CryptogramEncryptedPhrase {
		val alphabet = getAlphabet(language)
			.asSequence()
			.shuffled()
			.mapIndexed { index, c -> c to index }
			.associateBy({ it.first }, { it.second })

		val percentage = when (difficultyLevel) {
			DifficultyLevel.BEGINNER -> 0.2
			DifficultyLevel.EASY -> 0.3
			DifficultyLevel.NORMAL -> 0.4
			DifficultyLevel.CHALLENGING -> 0.5
			DifficultyLevel.HARD -> 0.6
			DifficultyLevel.EXPERT -> 0.7
			DifficultyLevel.MASTER -> 0.8
		}

		val letters = quote.text.filter { it.isLetter() }
		val hiddenLetters = (percentage * letters.length).roundToInt().coerceAtLeast(3)
		val hiddenIndices = generateSequence { Random.nextInt(letters.length) }
			.distinct()
			.take(hiddenLetters)
			.toSet()

		val hiddenIndicesMap = mutableScatterMapOf<Int, Char>()
		val hiddenLettersIndicesMap = mutableScatterMapOf<Char, MutableScatterSet<Int>>()
		val charsCount = MutableScatterMap<Char, Int>()
		val charsState = MutableScatterMap<Char, GetCryptogramQuoteUseCase.CellState>()
		val encryptedPhrase = mutableObjectListOf<GetCryptogramQuoteUseCase.Word>()
		val encryptedPositions = mutableObjectListOf<Triple<Int, Int, Int>>()
		val hints = mutableListOf<GetCryptogramQuoteUseCase.Hint>()

		quote.text.forEachIndexed { index, char ->
			if (char.isLetter()) {
				val charUppercase = char.uppercaseChar()
				charsCount[charUppercase] = charsCount.getOrElse(charUppercase) { 0 } + 1
				if (index in hiddenIndices) {
					hiddenIndicesMap[index] = char
					hiddenLettersIndicesMap[char] =
						(hiddenLettersIndicesMap[char] ?: mutableScatterSetOf()).apply { add(index) }
				}
			}
		}

		encryptedPhrase.addAll(
			quote.text.split(" ").mapIndexed { wordIndex, word ->
				val cells = word.mapIndexed { index, char ->
					val charUppercase = char.uppercaseChar()
					val alphabetIndex = alphabet[charUppercase]?.plus(1) ?: -1
					val globalIndex = quote.text.take(wordIndex + index).count { it.isLetter() }

					val state = when {
						!char.isLetter() -> GetCryptogramQuoteUseCase.CellState.Found
						globalIndex in hiddenIndices -> {
							encryptedPositions.add(Triple(alphabetIndex, wordIndex, index))
							hints.add(GetCryptogramQuoteUseCase.Hint(wordIndex to index, char))

							GetCryptogramQuoteUseCase.CellState.Empty
						}

						(hiddenLettersIndicesMap[char]?.size ?: 0) > 0 ->
							GetCryptogramQuoteUseCase.CellState.PartiallyGuessed

						else -> GetCryptogramQuoteUseCase.CellState.Found
					}

					GetCryptogramQuoteUseCase.Cell(char, alphabetIndex, state)
				}
				GetCryptogramQuoteUseCase.Word(cells)
			}
		)

		return GetCryptogramQuoteUseCase.CryptogramEncryptedPhrase(
			encryptedPhrase,
			encryptedPositions,
			quote,
			hiddenLetters,
			charsCount,
			hints.shuffled(),
			Clock.System.now().epochSeconds,
			difficultyLevel
		)
	}

	private fun getAlphabet(language: String = "en"): String {
		return when (language.lowercase()) {
			"en" -> "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			// Add more languages as needed
			else -> throw IllegalArgumentException("Unsupported language: $language")
		}
	}
}

class DifficultyAdjuster {
	fun calculateDifficultyLevel(metrics: UserPerformanceMetrics): DifficultyLevel {
		val score = calculatePerformanceScore(metrics)
		return when {
			score < 20 -> DifficultyLevel.BEGINNER
			score < 40 -> DifficultyLevel.EASY
			score < 60 -> DifficultyLevel.NORMAL
			score < 75 -> DifficultyLevel.CHALLENGING
			score < 85 -> DifficultyLevel.HARD
			score < 95 -> DifficultyLevel.EXPERT
			else -> DifficultyLevel.MASTER
		}
	}

	private fun calculatePerformanceScore(metrics: UserPerformanceMetrics): Double {
		val timeScore = (300000 - metrics.averageSolveTime.coerceAtMost(300000)) / 3000.0 // 0-100 scale, 5 minutes max
		val hintsScore = 100 - (metrics.averageHintsUsed * 10).coerceAtMost(100.0)
		val successScore = metrics.successRate * 100
		val experienceScore = (metrics.totalPuzzlesSolved.coerceAtMost(100) / 100.0) * 100

		return (timeScore * 0.3 + hintsScore * 0.3 + successScore * 0.3 + experienceScore * 0.1)
	}
}

class ScoreCalculator {
	fun calculateScore(timeTaken: Long, hintsUsed: Int, difficultyLevel: DifficultyLevel): Int {
		val baseScore = when (difficultyLevel) {
			DifficultyLevel.BEGINNER -> 50
			DifficultyLevel.EASY -> 100
			DifficultyLevel.NORMAL -> 150
			DifficultyLevel.CHALLENGING -> 200
			DifficultyLevel.HARD -> 250
			DifficultyLevel.EXPERT -> 300
			DifficultyLevel.MASTER -> 350
		}

		val timeMultiplier = 1.0 - (timeTaken / 300000.0).coerceAtMost(1.0) // 5 minutes max
		val hintPenalty = hintsUsed * 10

		return (baseScore * timeMultiplier - hintPenalty).toInt().coerceAtLeast(0)
	}
}

interface UserProgressRepository {
	suspend fun saveProgress(progress: UserProgress)
	suspend fun getProgress(): UserProgress
}

internal class UserProgressRepositoryImpl : UserProgressRepository {
	override suspend fun saveProgress(progress: UserProgress) {
		TODO("Not yet implemented")
	}

	override suspend fun getProgress(): UserProgress {
		return UserProgress(0, 0L, 0, 0, 0)
	}
}

data class UserProgress(
	val solvedPuzzles: Int,
	val averageTime: Long,
	val hintsUsed: Int,
	val successfulAttempts: Int,
	val totalAttempts: Int
) {
	fun toUserPerformanceMetrics(): UserPerformanceMetrics {
		return UserPerformanceMetrics(
			averageSolveTime = averageTime,
			averageHintsUsed = hintsUsed.toDouble() / solvedPuzzles.coerceAtLeast(1),
			successRate = successfulAttempts.toDouble() / totalAttempts.coerceAtLeast(1),
			totalPuzzlesSolved = solvedPuzzles
		)
	}
}