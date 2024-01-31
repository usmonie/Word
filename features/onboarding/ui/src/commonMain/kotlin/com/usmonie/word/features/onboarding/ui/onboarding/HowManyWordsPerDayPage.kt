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
import wtf.speech.core.ui.BaseCard
import wtf.word.core.domain.tools.fastForEach

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
                title = {
                    Text(
                        "[H]ow many words do you want to learn per day?",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                },
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
                    "We will create a personalized learning plan for you, which will match your level and goals. You can choose how many words you want to learn per day.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth().padding(24.dp)
                )

                words.fastForEach { count ->
                    Row(Modifier.fillMaxWidth().clickable { onSelectWordsCount(count) }) {
//                    RadioButton(selected = selectedWords == count, { onSelectWordsCount(count) })
                        Text(
                            "$count Words",
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
