package com.usmonie.word.features.dashboard.data.repository

import com.liftric.kvault.KVault
import com.usmonie.word.features.dashboard.domain.models.Language
import com.usmonie.word.features.dashboard.domain.models.LanguageLevel
import com.usmonie.word.features.dashboard.domain.models.NotificationTime
import com.usmonie.word.features.dashboard.domain.models.UserLearningStatus
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserRepositoryImpl(
    private val kVault: KVault,
) : UserRepository {

    override var wordsPerDayCount: Int
        get() = kVault.int(WORDS_PER_DAY_COUNT) ?: 0
        set(value) {
            kVault.set(WORDS_PER_DAY_COUNT, value)
        }

    override var nativeLanguage: Language
        get() = Language.valueOf(kVault.string(NATIVE_LANGUAGE) ?: Language.ES.name)
        set(value) {
            kVault.set(NATIVE_LANGUAGE, value.name)
        }

    override var notificationsTime: NotificationTime
        get() = NotificationTime.valueOf(
            kVault.string(NOTIFICATIONS_TIME) ?: NotificationTime.MORNING.name
        )
        set(value) {
            kVault.set(NOTIFICATIONS_TIME, value.name)
        }

    override var languageLevel: LanguageLevel
        get() = LanguageLevel.valueOf(kVault.string(USER_LANGUAGE_LEVEL) ?: LanguageLevel.A1.name)
        set(value) {
            kVault.set(USER_LANGUAGE_LEVEL, value.name)
        }
    override var lastPracticeTime: Long
        get() = kVault.long(LAST_PRACTICE_TIME) ?: 0L
        set(value) {
            kVault.set(LAST_PRACTICE_TIME, value)
        }

    override val learningStatusFlow: StateFlow<UserLearningStatus> by lazy {
//        val userStatus = firestore.collection("users").document(currentUser.uid)
        val state = MutableStateFlow<UserLearningStatus>(UserLearningStatus.NotStarted)

        state
    }
    override var hintsCount: Int
        get() = kVault.int(CURRENT_USER_HINTS_COUNT_KEY) ?: 5
        set(value) {
            if (value >= 0) kVault.set(CURRENT_USER_HINTS_COUNT_KEY, value)
        }
    override var livesCount: Int
        get() = kVault.int(CURRENT_USER_LIVES_COUNT_KEY) ?: 3
        set(value) {
            kVault.set(CURRENT_USER_LIVES_COUNT_KEY, value)
        }

//    private val currentUser: FirebaseUser by lazy {
//        authentication.currentUser ?: throw IllegalStateException("User is not authenticated")
//    }

    companion object {
        private const val CURRENT_USER_HINTS_COUNT_KEY: String = "CURRENT_USER_HINTS_COUNT"
        private const val CURRENT_USER_LIVES_COUNT_KEY: String = "CURRENT_USER_LIVES_COUNT"

        private const val WORDS_PER_DAY_COUNT = "WORDS_PER_DAY_COUNT"
        private const val NATIVE_LANGUAGE = "NATIVE_LANGUAGE"
        private const val NOTIFICATIONS_TIME = "NOTIFICATIONS_TIME"

        private const val LAST_PRACTICE_TIME = "LAST_PRACTICE_TIME"
        private const val USER_LANGUAGE_LEVEL = "USER_LANGUAGE_LEVEL"
    }
}
