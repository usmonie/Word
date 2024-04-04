package com.usmonie.word.features.main.ui

import com.usmonie.word.features.dashboard.ui.DASHBOARD_GRAPH_ID
import wtf.speech.core.ui.ScreenState

enum class MainScreenState(val graphId: String): ScreenState {
    DASHBOARD(DASHBOARD_GRAPH_ID),
    DICTIONARY(DASHBOARD_GRAPH_ID),
    PROFILE(DASHBOARD_GRAPH_ID)
}