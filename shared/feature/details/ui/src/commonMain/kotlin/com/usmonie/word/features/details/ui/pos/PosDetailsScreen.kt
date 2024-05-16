package com.usmonie.word.features.details.ui.pos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.usmonie.core.kit.composables.word.HeaderState
import com.usmonie.core.kit.composables.word.HeaderWordScaffold
import com.usmonie.core.kit.tools.add
import com.usmonie.word.features.dictionary.ui.WordDetailed

class PosDetailsScreen(
    viewModel: PosDetailsViewModel
) : StateScreen<PosDetailsState, PosDetailAction, PosDetailEvent, PosDetailEffect, PosDetailsViewModel>(
    viewModel
) {
    override val id: ScreenId = PosDetailsScreenFactory.ID

    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current

        val state by viewModel.state.collectAsState()
        HeaderWordScaffold(
            { state.word.word },
            routeManager::popBackstack,
            headerState = { HeaderState.Close },
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
