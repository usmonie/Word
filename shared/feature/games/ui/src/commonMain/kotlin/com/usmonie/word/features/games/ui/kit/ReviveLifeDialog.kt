package com.usmonie.word.features.games.ui.kit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.games.ui.generated.resources.Res
import word.shared.feature.games.ui.generated.resources.games_lost_revive_free_title
import word.shared.feature.games.ui.generated.resources.games_lost_revive_title
import word.shared.feature.games.ui.generated.resources.games_lost_title

@Composable
fun ReviveLifeDialog(
	onDismissRequest: () -> Unit,
	onAddLifeClick: () -> Unit,
	onNextPhraseClick: () -> Unit,
	isReviveAvailable: () -> Boolean,
	title: String = stringResource(Res.string.games_lost_title),
	reviveTitle: String = stringResource(Res.string.games_lost_revive_title),
	nextTitle: String
) {
	Dialog(
		onDismissRequest = onDismissRequest,
		properties = DialogProperties(
			dismissOnBackPress = true,
			dismissOnClickOutside = false
		)
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
			modifier = Modifier.fillMaxWidth()
				.background(
					MaterialTheme.colorScheme.surfaceContainer,
					MaterialTheme.shapes.extraLarge
				)
                .padding(16.dp)
		) {
			Text(
				text = title,
				style = MaterialTheme.typography.titleLarge,
				textAlign = TextAlign.Center,
				modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
			)

			Button(
				onAddLifeClick,
				Modifier.padding(horizontal = 16.dp),
				enabled = isReviveAvailable()
			) {
				Box {
					Text(
						text = reviveTitle,
						textAlign = TextAlign.Center,
						style = MaterialTheme.typography.titleMedium,
						modifier = Modifier
							.fillMaxWidth()
							.padding(horizontal = 16.dp, vertical = 4.dp)
					)

					Badge(Modifier.align(Alignment.CenterEnd)) {
						Text(
							text = stringResource(Res.string.games_lost_revive_free_title),
							textAlign = TextAlign.Center,
							style = MaterialTheme.typography.labelMedium,
							modifier = Modifier.padding(4.dp)
						)
					}
				}
			}

			OutlinedButton(onNextPhraseClick, Modifier.padding(horizontal = 16.dp)) {
				Text(
					text = nextTitle,
					textAlign = TextAlign.Center,
					style = MaterialTheme.typography.titleMedium,
					modifier = Modifier.weight(1f).padding(horizontal = 16.dp, vertical = 4.dp)
				)
			}
		}
	}
}
