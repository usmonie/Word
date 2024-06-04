package com.usmonie.word.features.dictionary.data.usecases

import com.usmonie.word.features.dictionary.data.db.room.models.SearchHistoryDb
import com.usmonie.word.features.dictionary.data.db.room.models.WordFavorite
import com.usmonie.word.features.dictionary.data.repository.RealmWordsRepositoryImpl
import com.usmonie.word.features.dictionary.data.repository.RoomWordsRepositoryImpl
import com.usmonie.word.features.dictionary.domain.usecases.MoveToNewDatabaseUseCase
import wtf.word.core.domain.tools.fastMap

internal class MoveToNewDatabaseUseCaseImpl(
    private val realmWordsRepository: RealmWordsRepositoryImpl,
    private val roomWordsRepository: RoomWordsRepositoryImpl
) : MoveToNewDatabaseUseCase {
    override suspend fun invoke(input: Unit) {
        val oldSearchHistory = realmWordsRepository.getOldSearchHistory()
            .fastMap {
                SearchHistoryDb(
                    it.word,
                    it.date
                )
            }
        val oldFavorites = realmWordsRepository.getOldFavorites()
            .fastMap {
                WordFavorite(
                    it.word,
                    it.date
                )
            }

        roomWordsRepository.addSearchHistory(oldSearchHistory)
        roomWordsRepository.addFavorites(oldFavorites)
        realmWordsRepository.realmDeleteAndClose()
    }
}