package com.usmonie.word.features.analytics

import com.usmonie.word.features.new.models.WordCombinedUi
import wtf.word.core.design.themes.WordColors
import wtf.word.core.design.themes.typographies.WordTypography
import wtf.word.core.domain.models.AnalyticsEvent

sealed class DashboardAnalyticsEvents(key: String, data: EventData) : AnalyticsEvent(key, data) {
    data class ChangeTheme(private val colors: WordColors, private val typography: WordTypography) :
        DashboardAnalyticsEvents("SEARCH", DashboardEventData.CurrentTheme(colors, typography))
    data class Search(private val query: String) :
        DashboardAnalyticsEvents("SEARCH", DashboardEventData.Search(query))

    data class OpenWord(private val wordUi: WordCombinedUi) :
        DashboardAnalyticsEvents("OPEN_WORD", DashboardEventData.WordData(wordUi.word))

    data object OpenFavourites :
        DashboardAnalyticsEvents("OPEN_FAVOURITES", DashboardEventData.Favourites)

    data object OpenSettings : DashboardAnalyticsEvents("OPEN_SETTINGS", DashboardEventData.Settings)

}

sealed class DashboardEventData : AnalyticsEvent.EventData {
    data class CurrentTheme(val colors: WordColors, val typography: WordTypography) : DashboardEventData()
    data class WordData(val word: String) : DashboardEventData()
    data class Search(val query: String) : DashboardEventData()

    data object Favourites : DashboardEventData()
    data object Settings : DashboardEventData()
}