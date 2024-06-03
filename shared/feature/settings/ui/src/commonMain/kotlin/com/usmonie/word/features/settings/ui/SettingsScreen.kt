package com.usmonie.word.features.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WorkspacePremium
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.usmonie.compass.core.LocalRouteManager
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.compass.viewmodel.StateScreen
import com.usmonie.core.kit.composables.word.HeaderWordScaffold
import com.usmonie.core.kit.design.themes.WordThemes
import com.usmonie.core.kit.design.themes.typographies.Friendly
import com.usmonie.core.kit.design.themes.typographies.ModernChic
import com.usmonie.core.kit.design.themes.typographies.TimelessElegant
import com.usmonie.core.kit.tools.add
import com.usmonie.word.features.ads.ui.LocalAdMob
import com.usmonie.word.features.settings.domain.models.DarkThemeMode
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionPage
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionScreenState
import com.usmonie.word.features.subscriptions.ui.notification.SubscriptionViewModel
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.settings.ui.generated.resources.Res
import word.shared.feature.settings.ui.generated.resources.settings_fonts_title
import word.shared.feature.settings.ui.generated.resources.settings_themes_daylight_always
import word.shared.feature.settings.ui.generated.resources.settings_themes_daylight_auto_title
import word.shared.feature.settings.ui.generated.resources.settings_themes_daylight_never
import word.shared.feature.settings.ui.generated.resources.settings_themes_daylight_title
import word.shared.feature.settings.ui.generated.resources.settings_themes_title
import word.shared.feature.settings.ui.generated.resources.settings_title

internal class SettingsScreen(
    viewModel: SettingsViewModel,
    private val subscriptionsViewModel: SubscriptionViewModel,
) : StateScreen<SettingsState, SettingsAction, SettingsEvent, SettingsEffect, SettingsViewModel>(
    viewModel
) {
    override val id: ScreenId = SettingsScreenFactory.ID

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current
        val favoritesPlaceholder = stringResource(Res.string.settings_title)
        val state by viewModel.state.collectAsState()
        val subscriptionsState by subscriptionsViewModel.state.collectAsState()

        val themes = remember {
            WordThemes.entries
        }

        val darkThemeModes = remember {
            DarkThemeMode.entries
        }

        val typographies = remember {
            listOf(ModernChic, Friendly, TimelessElegant)
        }

        HeaderWordScaffold(
            placeholder = { favoritesPlaceholder },
            onBackClicked = routeManager::popBackstack,
            header = if (subscriptionsState is SubscriptionScreenState.Empty) {
                null
            } else {
                { SubscriptionPage(subscriptionsViewModel) }
            },
            bottomAdBanner = {
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
        ) { insets ->
            val newInsets = remember(insets) { insets.add(top = 16.dp, bottom = 80.dp) }

            LazyColumn(contentPadding = newInsets, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                item {
                    Text(
                        stringResource(Res.string.settings_themes_daylight_title),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }

                items(darkThemeModes) { mode ->
                    SettingsSubscriptionItem(
                        onClick = { viewModel.onDarkModeChanged(mode) },
                        title = stringResource(
                            when (mode) {
                                DarkThemeMode.AUTO -> Res.string.settings_themes_daylight_auto_title
                                DarkThemeMode.ALWAYS -> Res.string.settings_themes_daylight_always
                                DarkThemeMode.NEVER -> Res.string.settings_themes_daylight_never
                            }
                        ),
                        selected = mode == state.darkThemeMode,
                        isEnabled = true,
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }

                item {
                    Text(
                        stringResource(Res.string.settings_themes_title),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }

                items(themes) { theme ->
                    SettingsSubscriptionItem(
                        onClick = { viewModel.onThemeChanged(theme) },
                        title = theme.title,
                        selected = theme == state.currentTheme,
                        isEnabled = !theme.paid || state.subscriptionStatus is SubscriptionStatus.Purchased,
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }

                item {
                    Text(
                        stringResource(Res.string.settings_fonts_title),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }

                items(typographies) { typography ->
                    SettingsSubscriptionItem(
                        onClick = { viewModel.onTypographyChanged(typography) },
                        title = typography.name,
                        selected = typography == state.currentTypography,
                        isEnabled = typography is ModernChic ||
                            state.subscriptionStatus is SubscriptionStatus.Purchased,
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
internal fun SettingsSubscriptionItem(
    onClick: () -> Unit,
    title: String,
    selected: Boolean,
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick,
        modifier = modifier,
        color = Color.Transparent,
        enabled = !selected && isEnabled
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected,
                onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unselectedColor = MaterialTheme.colorScheme.primary,
                    disabledSelectedColor = MaterialTheme.colorScheme.primary,
                    disabledUnselectedColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier.padding(start = 24.dp),
                enabled = isEnabled
            )
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding().weight(1f)
            )
            if (!isEnabled) {
                Icon(
                    Icons.Rounded.WorkspacePremium,
                    contentDescription = "$title only with subscription"
                )
                Spacer(Modifier.width(24.dp))
            }
        }
    }
}
