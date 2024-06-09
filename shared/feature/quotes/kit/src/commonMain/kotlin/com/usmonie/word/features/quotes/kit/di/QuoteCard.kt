package com.usmonie.word.features.quotes.kit.di

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.usmonie.core.kit.composables.base.buttons.TextButton
import com.usmonie.core.kit.composables.base.text.AutoSizeText
import com.usmonie.word.features.qutoes.domain.models.Quote

private const val QUOTE_STARS = "“”"

@Composable
fun QuoteCard(
    quote: Quote,
    onFavoriteClicked: (Quote) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
    ) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
//            Text(
//                text = QUOTE_STARS,
//                modifier = Modifier.fillMaxWidth(),
//                textAlign = TextAlign.Center,
//                style = MaterialTheme.typography.labelSmall,
//            )

            SelectionContainer {
                AutoSizeText(
                    text = "“${quote.text}”",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    maxLines = 6,
                    modifier = Modifier.fillMaxWidth().padding()
                )
            }

            val author = quote.author
            if (author != null) {
                Spacer(Modifier.height(4.dp))
                SelectionContainer {
                    Text(
                        text = author,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )
                }
            } else {
                Text(
                    text = QUOTE_STARS,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                FavoriteButton(quote, onFavoriteClicked)
            }
        }
    }
}

@Composable
fun QuoteLargeCard(
    quote: Quote,
    onFavoriteClicked: (Quote) -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
    ) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                text = QUOTE_STARS,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
            )

            SelectionContainer {
                AutoSizeText(
                    text = quote.text.trim(),
                    minTextSize = MaterialTheme.typography.bodySmall.fontSize,
                    maxLines = 6,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding()
                )
            }

            val author = quote.author
            if (author != null) {
                Spacer(Modifier.height(4.dp))
                SelectionContainer {
                    Text(
                        text = author,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )
                }
            } else {
                Text(
                    text = QUOTE_STARS,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                FavoriteButton(quote, onFavoriteClicked)
                NextButton(onNextClicked)
            }
        }
    }
}

@Composable
fun QuoteLargeCard(
    quote: Quote,
    onFavoriteClicked: (Quote) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
    ) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                text = QUOTE_STARS,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
            )

            SelectionContainer {
                AutoSizeText(
                    text = quote.text.trim(),
                    minTextSize = MaterialTheme.typography.bodySmall.fontSize,
                    maxLines = 6,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding()
                )
            }

            val author = quote.author
            if (author != null) {
                Spacer(Modifier.height(4.dp))
                SelectionContainer {
                    Text(
                        text = author,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )
                }
            } else {
                Text(
                    text = QUOTE_STARS,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                FavoriteButton(quote, onFavoriteClicked)
            }
        }
    }
}

@Composable
fun QuoteCardExpand(title: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier,
        onClick = onClick
    ) {
        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = QUOTE_STARS,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun QuoteCardCollapse(title: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier,
        onClick = onClick
    ) {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun FavoriteButton(
    quote: Quote,
    onClick: (Quote) -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        if (quote.favorite) "Unfavorite" else "Favorite",
        { onClick(quote) },
        modifier,
        contentPadding = PaddingValues(0.dp)
    )
}

@Composable
fun NextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        "Next quote",
        onClick,
        modifier,
        contentPadding = PaddingValues(0.dp)
    )
}
