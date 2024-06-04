package com.usmonie.word.features.details.ui.word

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.usmonie.compass.core.LocalRouteManager
import com.usmonie.compass.core.gesture.ScreenGestureHandler
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.compass.viewmodel.StateScreen
import com.usmonie.core.kit.composables.word.HeaderWordScaffold
import com.usmonie.word.features.ads.ui.LocalAdsManager
import com.usmonie.word.features.details.ui.mobile.WordDetailsContent
import com.usmonie.word.features.details.ui.pos.PosDetailsScreenFactory
import com.usmonie.word.features.details.ui.word.WordDetailsScreenFactory.Companion.ID
import com.usmonie.word.features.dictionary.ui.IconFavoriteButton
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionPage
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionScreenState
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.details.ui.generated.resources.Res
import word.shared.feature.details.ui.generated.resources.details_root_title

internal class WordDetailsScreen(
    viewModel: WordDetailsViewModel,
    private val subscriptionsViewModel: SubscriptionViewModel
) : StateScreen<WordDetailsState, WordDetailsAction, WordDetailsEvent, WordDetailsEffect, WordDetailsViewModel>(
    viewModel
) {
    private var defaultHandlingPadding = 32.dp.value
    override val id: ScreenId = ID
    override val screenGestureHandler: ScreenGestureHandler
        get() = ScreenGestureHandler.Handling(defaultHandlingPadding)

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current
        val state by viewModel.state.collectAsState()
        val subscriptionState by subscriptionsViewModel.state.collectAsState()

        val etymologiesPagerState = rememberPagerState { state.word.wordEtymology.size }

        val effect by viewModel.effect.collectAsState(null)

        WordDetailsEffects(effect)

        val density = LocalDensity.current
        LaunchedEffect(Unit) {
            defaultHandlingPadding = with(density) { 32.dp.toPx() }
        }

        HeaderWordScaffold(
            { state.word.word },
            routeManager::popBackstack,
            header = if (subscriptionState is SubscriptionScreenState.Empty) {
                null
            } else {
                { SubscriptionPage(subscriptionsViewModel) }
            },
            topBarBottom = {
                val coroutineScope = rememberCoroutineScope()

                if (etymologiesPagerState.pageCount > 1) {
                    PrimaryScrollableTabRow(
                        etymologiesPagerState.currentPage,
                        edgePadding = 16.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        repeat(etymologiesPagerState.pageCount) {
                            Tab(
                                selected = it == etymologiesPagerState.currentPage,
                                onClick = {
                                    coroutineScope.launch {
                                        etymologiesPagerState.animateScrollToPage(it)
                                    }
                                },
                            ) {
                                Text(
                                    stringResource(Res.string.details_root_title, it + 1),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                                )
                            }
                        }
                    }
                }
            },
            actions = {
                IconFavoriteButton({ state.word }, viewModel::onFavorite)
                Spacer(Modifier.width(24.dp))
            },
            bottomAdBanner = {
                Box(
                    Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    val adMob = LocalAdsManager.current

                    adMob.Banner(Modifier.fillMaxWidth().navigationBarsPadding())
                }
            }
        ) {
            WordDetailsContent(viewModel, it, etymologiesPagerState)
        }
    }
}

@Composable
private fun WordDetailsEffects(effect: WordDetailsEffect?) {
    val routeModel = LocalRouteManager.current
    LaunchedEffect(effect) {
        when (effect) {
            is WordDetailsEffect.OpenPos -> routeModel.navigateTo(
                PosDetailsScreenFactory.ID,
                extras = PosDetailsScreenFactory.ScreenExtra(effect.wordUi)
            )

            else -> Unit
        }
    }
}
