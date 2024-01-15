package com.usmonie.word.features.new.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.WorkspacePremium
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.gradientBackground
import com.usmonie.word.features.new.dashboard.SettingsItem
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.ui.AdMob
import com.usmonie.word.features.ui.BaseLazyColumn
import com.usmonie.word.features.ui.MenuItemText
import com.usmonie.word.features.ui.TitleBar
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.core.ui.AppKeys
import wtf.word.core.design.themes.WordColors
import wtf.word.core.design.themes.typographies.Friendly
import wtf.word.core.design.themes.typographies.ModernChic
import wtf.word.core.design.themes.typographies.TimelessElegant
import wtf.word.core.design.themes.typographies.WordTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreenContent(
    changeTheme: (WordColors) -> Unit,
    changeFont: (WordTypography) -> Unit,
    viewModel: SettingsViewModel,
    adMob: AdMob
) {
    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsState(null)

    SettingsScreenEffect(effect, changeTheme, changeFont)

    val isSubscribed by remember(state) {
        derivedStateOf { state.subscriptionStatus == SubscriptionStatus.PURCHASED }
    }

    val colors = remember { WordColors.entries.toList() }

    val typographies = remember {
        listOf(ModernChic, Friendly, TimelessElegant)
    }
    val routeManager = LocalRouteManager.current

    val listState = state.listState
    val appBarState = state.appBarState
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appBarState)


    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    TitleBar(
                        "[S]ettings",
                        MaterialTheme.typography.displayLarge.fontSize *
                                (1 - scrollBehavior.state.collapsedFraction).coerceIn(0.6f, 1f)
                    )
                },
                navigationIcon = {
                    IconButton(routeManager::navigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = Icons.Default.ArrowBack.name,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { insets ->
        Box(Modifier
            .gradientBackground()
            .padding(insets)) {
            BaseLazyColumn(listState) {

                item {
                    SettingsSubtitle("Theme", Modifier.fillParentMaxWidth())
                }

                items(colors) { color ->
                    SettingsSubscriptionItem(
                        onClick = { viewModel.handleAction(SettingsAction.OnThemeChanged(color)) },
                        title = color.title,
                        selected = if ((!isSubscribed && color.paid)) {
                            color == WordColors.RICH_MAROON
                        } else {
                            color == state.currentTheme
                        },
                        isSubscribed = !color.paid || isSubscribed,
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }

                item {
                    SettingsSubtitle("Typography", Modifier.fillParentMaxWidth())
                }

                items(typographies) { typography ->
                    SettingsSubscriptionItem(
                        onClick = {
                            viewModel.handleAction(SettingsAction.OnTypographyChanged(typography))
                        },
                        title = typography.name,
                        selected = if (isSubscribed) typography == state.currentTypography else typography is ModernChic,
                        isSubscribed = typography is ModernChic || isSubscribed,
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }

                item {
                    SettingsSubtitle(
                        "User Data",
                        Modifier.fillParentMaxWidth()
                    )
                }

                item {
                    SettingsItem(
                        onClick = viewModel::clearSearchHistory,
                        title = "Clear search history",
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }

                item { Spacer(Modifier.height(80.dp)) }
            }

            adMob.Banner(
                AppKeys.BANNER_ID,
                Modifier.fillMaxWidth().align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun SettingsScreenEffect(
    effect: SettingsEffect?,
    changeTheme: (WordColors) -> Unit,
    changeFont: (WordTypography) -> Unit
) {
    LaunchedEffect(effect) {
        when (effect) {
            is SettingsEffect.OnThemeChanged -> changeTheme(effect.newTheme)

            is SettingsEffect.OnTypographyChanged -> changeFont(effect.newTypography)

            null -> Unit
        }
    }
}

@Composable
internal fun SettingsSubtitle(title: String, modifier: Modifier) {
    MenuItemText(title, modifier)
}

@Composable
internal fun SettingsSubscriptionItem(
    onClick: () -> Unit,
    title: String,
    selected: Boolean,
    isSubscribed: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick,
        modifier = modifier,
        color = Color.Transparent,
        enabled = !selected && isSubscribed
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected,
                onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedColor = MaterialTheme.colorScheme.onPrimary,
                    disabledSelectedColor = MaterialTheme.colorScheme.onPrimary,
                    disabledUnselectedColor = MaterialTheme.colorScheme.onPrimary,
                ),
                modifier = Modifier.padding(start = 20.dp),
                enabled = isSubscribed
            )

            MenuItemText(title, Modifier.padding().weight(1f))
            if (!isSubscribed) {
                Icon(
                    Icons.Rounded.WorkspacePremium,
                    contentDescription = "$title only with subscription"
                )
                Spacer(Modifier.width(20.dp))
            }
        }
    }
}

@Composable
internal fun SettingsItem(
    onClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick,
        modifier = modifier,
        color = Color.Transparent,
    ) {
        MenuItemText(title, Modifier.padding(start = 36.dp, end = 20.dp))
    }
}