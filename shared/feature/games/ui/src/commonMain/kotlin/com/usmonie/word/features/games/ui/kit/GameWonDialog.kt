package com.usmonie.word.features.games.ui.kit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.usmonie.word.features.games.ui.models.WordCombinedUi
import com.usmonie.word.features.quotes.kit.di.QuoteCard
import com.usmonie.word.features.quotes.domain.models.Quote
import org.jetbrains.compose.resources.stringResource
import word.shared.feature.games.ui.generated.resources.Res
import word.shared.feature.games.ui.generated.resources.games_enigma_won_title
import word.shared.feature.games.ui.generated.resources.games_hangman_won_title

@Composable
fun GameWonDialog(
	onDismissRequest: () -> Unit,
	onNextPhraseClick: () -> Unit,
	nextTitle: String,
	wonTitle: @Composable ColumnScope.() -> Unit,
) {
	val dialogProperties = remember {
		DialogProperties(
			dismissOnBackPress = true,
			dismissOnClickOutside = true
		)
	}
	Dialog(
		onDismissRequest = onDismissRequest,
		properties = dialogProperties,
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
			modifier = Modifier.fillMaxWidth()
				.background(
					MaterialTheme.colorScheme.surfaceContainer,
					MaterialTheme.shapes.extraLarge
				)
				.padding(16.dp)
		) {
			wonTitle()

			OutlinedButton(onNextPhraseClick, Modifier.padding(horizontal = 16.dp)) {
				Text(
					text = nextTitle,
					textAlign = TextAlign.Center,
					style = MaterialTheme.typography.titleMedium,
					modifier = Modifier.weight(1f)
						.padding(horizontal = 16.dp, vertical = 4.dp)
				)
			}
		}
	}
}

@Composable
fun EnigmaGameWon(
	onDismissRequest: () -> Unit,
	onNextPhraseClick: () -> Unit,
	onFavoriteQuote: (Quote) -> Unit,
	nextTitle: String,
	quote: Quote
) {
	GameWonDialog(onDismissRequest, onNextPhraseClick, nextTitle) {
		Text(
			stringResource(Res.string.games_enigma_won_title),
			textAlign = TextAlign.Center,
			style = MaterialTheme.typography.headlineSmall.copy(fontSize = MaterialTheme.typography.titleLarge.fontSize),
			color = MaterialTheme.colorScheme.onSurface,
			modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
		)

		QuoteCard(
			quote,
			onFavoriteQuote,
			modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
		)
	}
}

@Composable
fun HangmanGameWon(
	onDismissRequest: () -> Unit,
	onNextPhraseClick: () -> Unit,
	nextTitle: String,
	word: WordCombinedUi,
) {
	GameWonDialog(onDismissRequest, onNextPhraseClick, nextTitle) {
		Text(
			stringResource(Res.string.games_hangman_won_title),
			textAlign = TextAlign.Center,
			style = MaterialTheme.typography.headlineSmall.copy(fontSize = MaterialTheme.typography.titleLarge.fontSize),
			color = MaterialTheme.colorScheme.onSurface,
			modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
		)
		Text(
			word.word,
			textAlign = TextAlign.Center,
			style = MaterialTheme.typography.displayMedium,
			color = MaterialTheme.colorScheme.primary,
			modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
		)
	}
}
