package com.usmonie.word.features.games.ui.kit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.usmonie.core.kit.composables.base.scaffold.BaseHeaderScaffold
import com.usmonie.word.features.ads.ui.LocalAdMob
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionPage
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionScreenState
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameBoard(
    onBackClick: () -> Unit,
    subscriptionViewModel: SubscriptionViewModel,
    actions: @Composable (RowScope.() -> Unit),
    content: @Composable (PaddingValues) -> Unit
) {
    val subscriptionAdState by subscriptionViewModel.state.collectAsState()

    val showSubscriptionAd = subscriptionAdState !is SubscriptionScreenState.Empty
    BaseHeaderScaffold(
        header = {
            if (showSubscriptionAd) {
                SubscriptionPage(subscriptionViewModel)
            }
        }
    ) {
        Column {
            Scaffold(
                modifier = Modifier.fillMaxWidth().weight(1f),
                topBar = {
                    val insets = if (showSubscriptionAd) {
                        TopAppBarDefaults.windowInsets.only(WindowInsetsSides.Horizontal)
                    } else {
                        TopAppBarDefaults.windowInsets
                    }

                    GameBoardTopAppBar(
                        onBackClick,
                        true,
                        actions,
                        insets
                    )
                },
                content = content
            )

            Box(
                Modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                val adMob = LocalAdMob.current

                adMob.Banner(
                    Modifier.fillMaxWidth()
                        .navigationBarsPadding()
                )
            }
        }
    }
}
