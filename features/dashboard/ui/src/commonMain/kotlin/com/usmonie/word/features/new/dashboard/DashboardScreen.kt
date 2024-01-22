package com.usmonie.word.features.new.dashboard

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.TextFieldValue
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.GetSearchHistoryUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.GetWordOfTheDayUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.SearchWordsUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCaseImpl
import com.usmonie.word.features.ui.AdMob
import com.usmonie.word.features.ui.WordTopBar
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.word.core.domain.Analytics

class DashboardScreen private constructor(
    private val dashboardViewModel: DashboardViewModel,
    private val adMob: AdMob
) : Screen(dashboardViewModel) {
    override val id: String
        get() = ID

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        DashboardEffects(dashboardViewModel)
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { DashboardTopBar(dashboardViewModel) { scrollBehavior } },
            content = { insets -> DashboardContent(dashboardViewModel, adMob, insets) }
        )
    }

    companion object {
        const val ID = "DASHBOARD_SCREEN"
    }

    class Builder(
        private val wordRepository: WordRepository,
        private val adMob: AdMob,
        private val analytics: Analytics
    ) : ScreenBuilder {
        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?) = DashboardScreen(
            DashboardViewModel(
                SearchWordsUseCaseImpl(wordRepository),
                GetSearchHistoryUseCaseImpl(wordRepository),
                GetWordOfTheDayUseCaseImpl(wordRepository),
                UpdateFavouriteUseCaseImpl(wordRepository),
                RandomWordUseCaseImpl(wordRepository),
                analytics
            ), adMob
        )
    }
}

@ExperimentalMaterial3Api
@Composable
internal fun DashboardTopBar(
    dashboardViewModel: DashboardViewModel,
    getScrollBehavior: () -> TopAppBarScrollBehavior
) {
    val state by dashboardViewModel.state.collectAsState()

    val currentState = state
    val query =
        if (currentState is DashboardState.Success) currentState.query else TextFieldValue()
    val hasFocus = if (currentState is DashboardState.Success) currentState.hasFocus else false

    WordTopBar(
        dashboardViewModel::onBackClick,
        dashboardViewModel::onQueryChanged,
        true,
        "[S]earch",
        { query },
        { hasFocus },
        dashboardViewModel::onQueryFieldFocusChanged,
        getScrollBehavior
    )
}