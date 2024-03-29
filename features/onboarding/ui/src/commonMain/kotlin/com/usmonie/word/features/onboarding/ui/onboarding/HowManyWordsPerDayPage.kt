package com.usmonie.word.features.onboarding.ui.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import word.features.onboarding.ui.generated.resources.Res
import word.features.onboarding.ui.generated.resources.how_many_words_description
import word.features.onboarding.ui.generated.resources.how_many_words_title
import word.features.onboarding.ui.generated.resources.words_count
import wtf.speech.core.ui.BaseCard
import wtf.word.core.domain.tools.fastForEach

@ExperimentalResourceApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HowManyWordsPerDayPage(
    onSelectWordsCount: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    val words = remember {
        listOf(5, 10, 15, 20, 30)
    }
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(Res.string.how_many_words_title)) },
                navigationIcon = {
                    IconButton(onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
            )
        }
    ) {
        Box(Modifier.fillMaxSize().padding(it)) {

            BaseCard(
                Modifier.fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    stringResource(Res.string.how_many_words_description),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth().padding(24.dp)
                )

                words.fastForEach { count ->
                    Row(Modifier.fillMaxWidth().clickable { onSelectWordsCount(count) }) {
//                    RadioButton(selected = selectedWords == count, { onSelectWordsCount(count) })
                        Text(
                            stringResource(Res.string.words_count, count),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 12.dp)
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}
