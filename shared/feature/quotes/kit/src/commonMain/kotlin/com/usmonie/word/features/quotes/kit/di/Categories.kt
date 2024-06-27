package com.usmonie.word.features.quotes.kit.di

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.quotes.domain.models.Quote

@Immutable
data class Categories(val categories: List<String>)

@Immutable
data class Quotes(val quotes: List<Quote>)

@Composable
fun QuotesFilterContent(
    quotes: Quotes,
    onFavoriteClicked: (Quote) -> Unit,
    modifier: Modifier = Modifier,
    insets: PaddingValues = PaddingValues(),
    showCategories: Boolean = true,
    selectedCategory: String? = null,
    categories: Categories = Categories(emptyList()),
    onCategorySelected: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = insets,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (showCategories) {
            item {
                LazyRow {
                    items(categories.categories) { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { onCategorySelected(category) },
                            label = {
                                Text(category)
                            }
                        )
                    }
                }
            }
        }

        items(quotes.quotes) {
            QuoteLargeCard(it, onFavoriteClicked)
        }
    }
}
