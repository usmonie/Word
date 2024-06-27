package com.usmonie.word.features.dashboard.ui.screen

import FireworksSimulation
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.compass.viewmodel.ContentState
import com.usmonie.compass.viewmodel.StateScreen
import com.usmonie.core.kit.composables.base.buttons.TextButton
import com.usmonie.core.kit.composables.word.HeaderWordScaffold
import com.usmonie.core.kit.tools.add
import com.usmonie.word.features.ads.ui.LocalAdsManager
import com.usmonie.word.features.games.ui.RandomWordExpandedState
import com.usmonie.word.features.games.ui.WordCardLarge
import com.usmonie.word.features.games.ui.WordCardSmall
import com.usmonie.word.features.games.ui.WordOfTheDayState
import com.usmonie.word.features.games.ui.models.WordCombinedUi
import com.usmonie.word.features.quotes.kit.di.QuoteLargeCard
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionPage
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionScreenState
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.dashboard.ui.generated.resources.Res
import word.shared.feature.dashboard.ui.generated.resources.dashboard_search_history_title
import word.shared.feature.dashboard.ui.generated.resources.favorites_subtitle
import word.shared.feature.dashboard.ui.generated.resources.games_subtitle
import word.shared.feature.dashboard.ui.generated.resources.network_connection_description
import word.shared.feature.dashboard.ui.generated.resources.network_connection_error
import word.shared.feature.dashboard.ui.generated.resources.network_connection_try_again
import word.shared.feature.dashboard.ui.generated.resources.search_title
import word.shared.feature.dashboard.ui.generated.resources.settings_subtitle

internal class DashboardScreen(
	viewModel: DashboardViewModel,
	private val subscriptionsViewModel: SubscriptionViewModel,
	private val onOpenWord: (wordCombined: WordCombinedUi) -> Unit,
	private val openDashboardMenuItem: (DashboardMenuItem) -> Unit
) : StateScreen<DashboardState, DashboardAction, DashboardEvent, DashboardEffect, DashboardViewModel>(
	viewModel
) {
	override val id: ScreenId = DashboardScreenFactory.ID

	@OptIn(ExperimentalMaterial3Api::class)
	@Composable
	override fun Content() {
		val state: DashboardState by viewModel.state.collectAsState()
		val subscriptionAdState by subscriptionsViewModel.state.collectAsState()
		val searchPlaceholder = stringResource(Res.string.search_title)

		val coroutineScope = rememberCoroutineScope()
		val lazyListState = rememberLazyListState()
		DashboardEffect(
			{ coroutineScope.launch { lazyListState.scrollToItem(0) } },
			onOpenWord,
			openDashboardMenuItem,
			viewModel
		)

		LaunchedEffect(state.randomQuote) {
			coroutineScope.launch { lazyListState.animateScrollToItem(0) }
		}

		HeaderWordScaffold(
			query = { state.searchFieldState.searchFieldValue },
			onQueryChanged = viewModel::inputQuery,
			placeholder = { searchPlaceholder },
			header = if (subscriptionAdState is SubscriptionScreenState.Empty) {
				null
			} else {
				{ SubscriptionPage(subscriptionsViewModel) }
			},
			bottomAdBanner = if (state.subscriptionStatus is SubscriptionStatus.None) {
				{
					Box(Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)) {
						val adMob = LocalAdsManager.current

						adMob.Banner(Modifier.fillMaxWidth().navigationBarsPadding())
					}
				}
			} else {
				null
			},
			getShowBackButton = { state.searchFieldState.searchFieldValue.text.isNotEmpty() },
			onBackClicked = viewModel::onBack,
			hasSearchFieldFocus = { state.searchFieldState.hasFocus },
			updateSearchFieldFocus = viewModel::queryFieldFocusChanged,
		) { insets ->
			val newInsets = remember(insets, state.searchFieldState) {
				insets.add(top = 16.dp)
			}

			LazyColumn(
				state = lazyListState,
				contentPadding = newInsets,
				verticalArrangement = Arrangement.spacedBy(16.dp),
			) {
				when (val foundWordState = state.foundWords) {
					is ContentState.Error<*, *> -> defaultState(state, false)

					is ContentState.Loading -> item {
						LinearProgressIndicator(Modifier.fillParentMaxWidth().padding(32.dp))
					}

					is ContentState.Success -> {
						if (state.searchFieldState.searchFieldValue.text.isBlank()) {
							defaultState(state, false)
						} else {
							items(foundWordState.data) {
								WordCardLarge(
									viewModel::openSearchWord,
									viewModel::favoriteWord,
									it,
									Modifier.fillParentMaxWidth().padding(horizontal = 16.dp)
								)
							}
						}
					}
				}
			}
		}
	}

	private fun LazyListScope.defaultState(
		state: DashboardState,
		saleSubscriptionExpanded: Boolean
	) {
		if (state.recentSearch.isNotEmpty()) {
			item {
				SearchHistoryState(state, saleSubscriptionExpanded)
			}
		}

		if (state.randomQuote != null) {
			item {
				QuoteLargeCard(
					state.randomQuote,
					viewModel::favoriteQuote,
					viewModel::nextRandomQuote,
					modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
				)
			}
		}


		item(
			key = DashboardMenuItem.FAVORITES,
			contentType = DashboardMenuItem::class
		) {
			MenuItem(
				viewModel::openFavorites,
				stringResource(Res.string.favorites_subtitle),
				menuItemModifier
			)
		}

		item(key = DashboardMenuItem.GAMES, contentType = DashboardMenuItem::class) {
			MenuItem(
				viewModel::openGames,
				stringResource(Res.string.games_subtitle),
				menuItemModifier
			)
		}

		item(key = DashboardMenuItem.SETTINGS, contentType = DashboardMenuItem::class) {
			MenuItem(
				viewModel::openSettings,
				stringResource(Res.string.settings_subtitle),
				menuItemModifier
			)
		}

		if (state.wordOfTheDay is ContentState.Error<*, *> &&
			state.randomWord is ContentState.Error<*, *>
		) {
			item { NetworkConnectionError() }
		}

		if (state.wordOfTheDay !is ContentState.Error<*, *>) {
			item(key = "WordOfTheDay", contentType = "WordOfTheDay") {
				WordOfTheDayState({ state.wordOfTheDay }, {}, {})
			}
		}

		item(key = "RandomWord", contentType = "RandomWord") {
			RandomWordState(state)
		}
	}

	@Composable
	private fun SearchHistoryState(
		state: DashboardState,
		saleSubscriptionExpanded: Boolean
	) {
		AnimatedVisibility(
			state.searchFieldState.hasFocus && state.searchFieldState.searchFieldValue.text.isBlank(),
			enter = slideInVertically(spring(stiffness = Spring.StiffnessHigh)) { -it } + fadeIn(),
			exit = slideOutVertically(spring(stiffness = Spring.StiffnessHigh)) { -it } + fadeOut(),
			content = { RecentSearchHistory(state, saleSubscriptionExpanded) }
		)
	}

	@Composable
	private fun RandomWordState(state: DashboardState) {
		AnimatedContent(
			state.randomWord,
			contentKey = { it.item?.word },
			transitionSpec = {
				slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start)
					.togetherWith(
						slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start)
					)
			}
		) {
			RandomWordExpandedState(
				{ it },
				viewModel::openWord,
				viewModel::favoriteWord,
				viewModel::nextRandomWord,
				Modifier.padding(horizontal = 16.dp)
			)
		}
	}

	@Composable
	private fun NetworkConnectionError() {
		Column(
			Modifier.padding(horizontal = 32.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp)
		) {
			Text(
				stringResource(Res.string.network_connection_error),
				style = MaterialTheme.typography.headlineMedium
			)
			Text(
				stringResource(Res.string.network_connection_description),
				style = MaterialTheme.typography.bodyLarge
			)

			TextButton(
				stringResource(Res.string.network_connection_try_again),
				viewModel::tryAgain,
				contentPadding = PaddingValues(0.dp)
			)
		}
	}

	@Composable
	private fun RecentSearchHistory(state: DashboardState, saleSubscriptionExpanded: Boolean) {
		Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
			Text(
				stringResource(Res.string.dashboard_search_history_title),
				style = MaterialTheme.typography.labelMedium,
				modifier = Modifier.padding(horizontal = 32.dp)
			)

			LazyRow(
				contentPadding = PaddingValues(horizontal = 16.dp),
				userScrollEnabled = !saleSubscriptionExpanded,
				horizontalArrangement = Arrangement.spacedBy(16.dp)
			) {
				items(state.recentSearch) { historyWord ->
					WordCardSmall(
						viewModel::openWord,
						historyWord,
						Modifier.fillParentMaxWidth(fraction = 0.6f)
					)
				}
			}
		}
	}
}

@Composable
internal fun DashboardEffect(
	onInit: () -> Unit,
	onOpenWord: (wordCombined: WordCombinedUi) -> Unit,
	openDashboardMenuItem: (DashboardMenuItem) -> Unit,
	viewModel: DashboardViewModel
) {
	val effect by viewModel.effect.collectAsState(null)
	LaunchedEffect(effect) {
		when (val e = effect) {
			is DashboardEffect.OnMenuItemClicked -> openDashboardMenuItem(e.menuItem)
			is DashboardEffect.OpenWord -> onOpenWord(e.word)
			is DashboardEffect.Init -> onInit()
			null -> Unit
		}
	}
}

private val menuItemTitleModifier =
	Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
private val menuItemModifier = Modifier.padding(horizontal = 16.dp)

@Composable
fun MenuItem(onClick: () -> Unit, title: String, modifier: Modifier = Modifier) {
	Card(
		onClick,
		modifier,
		shape = MaterialTheme.shapes.medium,
		colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
	) {
		Box(contentAlignment = Alignment.CenterStart) {
			Text(
				title,
				style = MaterialTheme.typography.titleLarge,
				modifier = menuItemTitleModifier
			)
		}
	}
}
