package com.usmonie.word.features.games.data.usecases

import com.usmonie.core.domain.tools.fastMap
import com.usmonie.word.features.games.data.db.room.models.SearchHistoryDb
import com.usmonie.word.features.games.data.db.room.models.WordFavorite
import com.usmonie.word.features.games.data.repository.RealmWordsRepositoryImpl
import com.usmonie.word.features.games.data.repository.RoomWordsRepositoryImpl
import com.usmonie.word.features.games.domain.usecases.MoveToNewDatabaseUseCase

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
