package com.usmonie.word.features.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.models.SynonymUi
import com.usmonie.word.features.models.WordUi
import com.usmonie.word.features.new.models.WordCombinedUi
import wtf.speech.core.ui.ContentState

@Composable
fun BaseCard(
    word: WordUi,
    onClick: (WordUi) -> Unit,
    onAddToFavouritePressed: (WordUi) -> Unit,
    onSharePressed: (WordUi) -> Unit,
    findSynonym: (SynonymUi) -> Unit,
    modifier: Modifier,
    maxDefinitionsCount: Int = Int.MAX_VALUE
) {
    BaseCard({ onClick(word) }, elevation = 2.dp, modifier = modifier) {
        Spacer(Modifier.height(20.dp))
        WordMediumTitle(word.word, Modifier.padding(horizontal = 20.dp))
        PartOfSpeech(word.partOfSpeech, Modifier.padding(horizontal = 20.dp))
        Spacer(Modifier.height(8.dp))
        Definitions(word.definitions.take(maxDefinitionsCount))
        if (word.synonyms.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Synonyms(findSynonym, word.synonyms)
        }
        Spacer(Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth()) {
            Spacer(Modifier.width(4.dp))
//            ShareButton({ onSharePressed(word) })

            AddToFavouriteButton(
                { onAddToFavouritePressed(word) },
                word.isFavourite,
            )
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun WordDetailsCard(
    word: WordUi,
    onAddToFavouritePressed: (WordUi) -> Unit,
    onSharePressed: (WordUi) -> Unit,
    findSynonym: (SynonymUi) -> Unit,
    modifier: Modifier,
) {
    Spacer(Modifier.height(32.dp))
    BaseCard({}, enabled = true, elevation = 8.dp, modifier = modifier) {
        Spacer(Modifier.height(20.dp))
        WordLargeResizableTitle(word.word, Modifier.padding(horizontal = 20.dp))
        PartOfSpeech(word.partOfSpeech, Modifier.padding(horizontal = 20.dp))
        Spacer(Modifier.height(8.dp))
        Definitions(word.definitions, true)
        if (word.synonyms.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Synonyms(findSynonym, word.synonyms)
        }
        Spacer(Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth()) {
            Spacer(Modifier.width(4.dp))
//            ShareButton({ onSharePressed(word) })

            AddToFavouriteButton(
                { onAddToFavouritePressed(word) },
                word.isFavourite,
            )
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun WordDetailsCard(
    word: ContentState<WordUi>,
    onAddToFavouritePressed: (WordUi) -> Unit,
    onSharePressed: (WordUi) -> Unit,
    findSynonym: (SynonymUi) -> Unit,
    modifier: Modifier,
) {
    when (word) {
        is ContentState.Error<*, *> -> Unit
        is ContentState.Loading -> Unit
        is ContentState.Success -> WordDetailsCard(
            word.data,
            onAddToFavouritePressed,
            onSharePressed,
            findSynonym,
            modifier,
        )
    }
}

@Composable
fun WordOfTheDayCard(
    word: WordUi,
    onClick: (WordUi) -> Unit,
    onAddToFavouritePressed: (WordUi) -> Unit,
    onSharePressed: (WordUi) -> Unit,
    onUpdatePressed: () -> Unit,
    modifier: Modifier
) {

    BaseCard({ onClick(word) }, elevation = 8.dp, modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(Modifier.height(20.dp))

            Text("Random Word", style = MaterialTheme.typography.labelMedium)

            WordLargeResizableTitle(
                word.word,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            )
            PartOfSpeech(word.partOfSpeech, modifier = Modifier.padding(horizontal = 20.dp))

            Spacer(Modifier.height(16.dp))

            word.definitions.firstOrNull()?.let { definition ->
                Definition(
                    definition,
                    Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    expanded = false
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth()) {
                Spacer(Modifier.width(8.dp))
//                ShareButton({ onSharePressed(word) })

                AddToFavouriteButton(
                    { onAddToFavouritePressed(word) },
                    word.isFavourite,
                )

                UpdateButton(onUpdatePressed)
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun WordRecentCard(word: WordCombinedUi, onClick: (WordCombinedUi) -> Unit, modifier: Modifier = Modifier) {
    BaseCard({ onClick(word) }, elevation = 2.dp, modifier) {
        Spacer(Modifier.height(8.dp))
        WordSmallTitle(word.word, Modifier.padding(horizontal = 20.dp))
//        Spacer(Modifier.height(4.dp))
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
fun WordOfTheDayCard(
    onClick: (WordUi) -> Unit,
    onAddFavouritePressed: (WordUi) -> Unit,
    onSharePressed: (WordUi) -> Unit,
    onUpdatePressed: () -> Unit,
    word: ContentState<WordUi>,
    modifier: Modifier = Modifier
) {
    when (word) {
        is ContentState.Error<*, *> -> Unit
        is ContentState.Loading -> WordOfTheDayCardLoading(modifier)
        is ContentState.Success -> WordOfTheDayCard(
            word.data,
            onClick,
            onAddFavouritePressed,
            onSharePressed,
            onUpdatePressed,
            modifier,
        )
    }
}

@Composable
fun WordOfTheDayCardLoading(modifier: Modifier) {
    Card(
        modifier = modifier.fillMaxWidth().height(128.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }
}
