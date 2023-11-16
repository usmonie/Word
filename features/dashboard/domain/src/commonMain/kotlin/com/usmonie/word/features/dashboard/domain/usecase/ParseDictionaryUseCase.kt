package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.WordParcelable
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import kotlinx.serialization.json.Json
import wtf.word.core.domain.usecases.CoroutineUseCase

/**
 * Use case for parsing the dictionary from a JSON file.
 * This class is responsible for initializing the word database with data
 * from a local JSON file if the database is currently empty.
 *
 * @property wordRepository The repository responsible for data operations on words.
 */
class ParseDictionaryUseCase(
    private val wordRepository: WordRepository
) : CoroutineUseCase<Unit, Unit> {
    private val sharedFileReader = SharedFileReader()

    /**
     * Invokes the use case to parse the dictionary.
     * If the word database is not empty, the operation will not proceed.
     * Reads a JSON file using `SharedFileReader` and decodes it into a list of `WordParcelable`.
     * Finally, it populates the repository with the word data.
     *
     * @param input A Unit indicating no input needed for this use case.
     */
    override suspend fun invoke(input: Unit) {
        if (!wordRepository.isDatabaseEmpty) return
        try {
            val json = sharedFileReader.loadJsonFile("dictionary.json")

            val data = json?.let {
                Json.decodeFromString<List<WordParcelable>>(it)
            } ?: listOf()

            wordRepository.put(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}