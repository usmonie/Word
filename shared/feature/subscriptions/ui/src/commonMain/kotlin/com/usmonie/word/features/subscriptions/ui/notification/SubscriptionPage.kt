package com.usmonie.word.features.subscriptions.ui.notification

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.usmonie.word.features.dictionary.ui.modifier

@Composable
fun SubscriptionPage(viewModel: SubscriptionViewModel) {
    val state by viewModel.state.collectAsState()
    when (val s = state) {
        is SubscriptionScreenState.Base -> TODO()
        is SubscriptionScreenState.Empty -> TODO()
        is SubscriptionScreenState.Sale -> {
            Column(modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
                AnimatedContent(s.subscriptionAdState) {
                    when (it) {
                        SubscriptionAdState.EXPANDED -> {
                            SaleNotificationExpanded(
                                { subscription ->
                                    viewModel.handleAction(
                                        SubscriptionScreenAction.Subscribe(
                                            subscription
                                        )
                                    )
                                },
                                {},
                                { viewModel.handleAction(SubscriptionScreenAction.Collapse) },
                                { "23:32" }
                            )
                        }

                        SubscriptionAdState.COLLAPSED -> {
                            SaleSubscriptionAdCollapsed(
                                { viewModel.handleAction(SubscriptionScreenAction.Expand) },
                                { viewModel.handleAction(SubscriptionScreenAction.Minify) },
                                { "23:32" }
                            )
                        }

                        else -> {
                            SaleSubscriptionAdMinified({
                                viewModel.handleAction(SubscriptionScreenAction.Expand)
                            }, { "23:32" })
                        }
                    }
                }
            }
        }
    }
}