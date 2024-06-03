package com.usmonie.word.features.games.ui.analytics

import com.usmonie.word.core.analytics.models.AnalyticsEvent

sealed class GameEvents(key: String, data: EventData) : AnalyticsEvent(key, data) {

    data class OpenGame(val openGame: GameEventData.OpenGame) : GameEvents("GAME_EVENT_OPEN_GAME", openGame)

}

sealed class GameEventData : AnalyticsEvent.EventData {
    sealed class OpenGame : GameEventData() {
        data object Hangman : OpenGame()
        data object Enigma : OpenGame()
    }

    sealed class GameData : GameEventData() {
        data class NextHangmanWord(val word: String) : GameData()
        data class NextEnigmaPhrase(val phrase: String) : GameData()

        data object UseHint : GameData()
        data object UseHintAd : GameData()
        data object ReviveLife : GameData()
        data object InterstitialAd : GameData()
        data object RewardedAd : GameData()
    }
}
