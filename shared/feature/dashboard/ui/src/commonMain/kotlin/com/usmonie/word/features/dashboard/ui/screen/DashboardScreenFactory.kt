package com.usmonie.word.features.dashboard.ui.screen

import androidx.collection.ScatterMap
import com.usmonie.compass.core.Extra
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenFactory
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel

@Suppress("LongParameterList")
class DashboardScreenFactory internal constructor(
    private val dashboardViewModel: DashboardViewModel,
    private val subscriptionViewModel: SubscriptionViewModel,
    private val openWord: (WordCombinedUi) -> Unit,
    private val openDashboardMenuItem: (DashboardMenuItem) -> Unit
) : ScreenFactory {
    override val id: ScreenId = ID

    override fun invoke(params: ScatterMap<String, String>?, extra: Extra?): Screen {
        return DashboardScreen(
            dashboardViewModel,
            subscriptionViewModel,
            openWord,
            openDashboardMenuItem
        )
    }

    companion object {
        val ID = ScreenId("DashboardScreen")
    }
}
