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
import word.features.onboarding.ui.generated.resources.above_50_years
import word.features.onboarding.ui.generated.resources.between_25_31_years
import word.features.onboarding.ui.generated.resources.between_31_35_years
import word.features.onboarding.ui.generated.resources.how_old_are_user_description
import word.features.onboarding.ui.generated.resources.how_old_are_user_title
import word.features.onboarding.ui.generated.resources.up_to_25_years
import wtf.speech.core.ui.BaseCard
import wtf.word.core.domain.tools.fastForEach

@ExperimentalResourceApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HowOldAreUserPage(
    onSelectYearsCount: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    val words = remember {
        listOf(
            Res.string.up_to_25_years,
            Res.string.between_25_31_years,
            Res.string.between_31_35_years,
            Res.string.above_50_years
        )
    }
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(Res.string.how_old_are_user_title)) },
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
                    stringResource(Res.string.how_old_are_user_description),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth().padding(24.dp)
                )

                words.fastForEach { years ->
                    val title = stringResource(years)
                    Row(Modifier.fillMaxWidth().clickable { onSelectYearsCount(title) }) {
//                    RadioButton(selected = selectedWords == count, { onSelectWordsCount(count) })
                        Text(
                            title,
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
