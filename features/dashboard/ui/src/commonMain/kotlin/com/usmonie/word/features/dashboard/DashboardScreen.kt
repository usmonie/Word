package com.usmonie.word.features.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
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

        val listState = rememberLazyGridState()
        DashboardEffects(dashboardViewModel)
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { DashboardTopBar(dashboardViewModel) { scrollBehavior } },
            floatingActionButton = {
                val expand by remember(listState.firstVisibleItemIndex) {
                    derivedStateOf {
                        listState.firstVisibleItemIndex < 1
                    }
                }
                val state by dashboardViewModel.state.collectAsState()

                LaunchedEffect(state) {
                    println(state.toString())
                }
                StartLearningButton(dashboardViewModel::onBackClick, state, expand)
            },
            content = { insets -> DashboardContent(listState, dashboardViewModel, adMob, insets) }
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

@Composable
private fun StartLearningButton(onClick: () -> Unit, state: DashboardState, expand: Boolean) {
    val showButton by remember(state) {
        mutableStateOf(
            state is DashboardState.Success
        )
    }

    if (showButton) {
        FloatingActionButton(onClick) {
            Row(
                Modifier.padding(horizontal = if (expand) 24.dp else 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(expand) {
                    Text("Start Training", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.width(8.dp))
                }
                Icon(
                    Icons.Default.ArrowForwardIos,
                    contentDescription = null,
                    Modifier.size(24.dp)
                )
            }
        }
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