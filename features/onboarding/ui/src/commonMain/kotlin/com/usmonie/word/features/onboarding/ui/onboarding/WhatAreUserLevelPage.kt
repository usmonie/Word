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
import com.usmonie.word.features.dashboard.domain.models.LanguageLevel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import word.features.onboarding.ui.generated.resources.Res
import word.features.onboarding.ui.generated.resources.what_is_user_english_level_title
import wtf.speech.core.ui.BaseCard
import wtf.word.core.domain.tools.fastForEach

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun WhatAreUserLevelPage(
    onSelectLevel: (LanguageLevel) -> Unit,
    onBackClick: () -> Unit,
) {
    val levels = remember { LanguageLevel.entries.toList() }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(Res.string.what_is_user_english_level_title)) },
                navigationIcon = {
                    IconButton(onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(MaterialTheme.colorScheme.background),
            )
        }
    ) { insets ->
        Box(Modifier.fillMaxSize().padding(insets)) {
            BaseCard(Modifier.fillMaxWidth().padding(24.dp)) {

                levels.fastForEach { level ->
                    Row(Modifier.fillMaxWidth().clickable { onSelectLevel(level) }) {
                        Text(
                            level.name,
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
