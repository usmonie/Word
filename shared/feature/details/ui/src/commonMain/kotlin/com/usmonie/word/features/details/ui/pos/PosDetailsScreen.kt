package com.usmonie.word.features.details.ui.pos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.compass.core.LocalRouteManager
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.compass.viewmodel.StateScreen
import com.usmonie.core.kit.composables.word.HeaderWordScaffold
import com.usmonie.core.kit.tools.add
import com.usmonie.word.features.dictionary.ui.WordDetailed
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionPage
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionScreenState
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel

class PosDetailsScreen(
    viewModel: PosDetailsViewModel,
    private val subscriptionsViewModel: SubscriptionViewModel
) : StateScreen<PosDetailsState, PosDetailAction, PosDetailEvent, PosDetailEffect, PosDetailsViewModel>(
    viewModel
) {
    override val id: ScreenId = PosDetailsScreenFactory.ID

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current

        val state by viewModel.state.collectAsState()
        val subscriptionState by subscriptionsViewModel.state.collectAsState()

        HeaderWordScaffold(
            placeholder = { state.word.word },
            header = if (subscriptionState is SubscriptionScreenState.Empty) {
                null
            } else {
                { SubscriptionPage(subscriptionsViewModel) }
            },
            onBackClicked = routeManager::popBackstack,
        ) {
            LazyColumn(contentPadding = it.add(32.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
                item {
                    WordDetailed(
                        { state.word },
                        Modifier.fillParentMaxWidth(),
                    )
                }

                items(state.word.thesaurusFlatted) {
                    Column(
                        Modifier.fillParentMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            it.first,
                            style = MaterialTheme.typography.titleSmall,
                        )

                        Text(it.second, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}
