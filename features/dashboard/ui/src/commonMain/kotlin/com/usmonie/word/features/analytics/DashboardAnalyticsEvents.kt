package com.usmonie.word.features.analytics

import com.usmonie.word.features.new.models.WordCombinedUi
import wtf.word.core.domain.models.AnalyticsEvent

sealed class DashboardAnalyticsEvents(key: String, data: EventData) : AnalyticsEvent(key, data) {
    data class Search(private val query: String) :
        DashboardAnalyticsEvents("Perform Search", DashboardEventData.Search(query))

    data class OpenWord(private val wordUi: WordCombinedUi) :
        DashboardAnalyticsEvents("View Content", DashboardEventData.WordData(wordUi.word))

    data object OpenFavourites :
        DashboardAnalyticsEvents("View Favorites", DashboardEventData.Favourites)

    data object OpenSettings : DashboardAnalyticsEvents("View Settings", DashboardEventData.Settings)
}

sealed class DashboardEventData : AnalyticsEvent.EventData {
    data class WordData(val word: String) : DashboardEventData()
    data class Search(val query: String) : DashboardEventData()

    data object Favourites : DashboardEventData()
    data object Settings : DashboardEventData()
}