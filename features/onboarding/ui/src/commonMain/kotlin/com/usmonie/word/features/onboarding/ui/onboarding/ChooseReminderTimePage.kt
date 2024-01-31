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
import com.usmonie.word.features.dashboard.domain.models.NotificationTime
import wtf.speech.core.ui.BaseCard
import wtf.word.core.domain.tools.fastForEach


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseReminderTimePage(
    onSelectTime: (NotificationTime) -> Unit,
    onBackClick: () -> Unit
) {
    val times = remember {
        listOf(
            Pair(NotificationTime.MORNING, "Morning (08:00-10:00)"),
            Pair(NotificationTime.DAY, "Day (12:00-14:00)"),
            Pair(NotificationTime.EVENING, "Evening (18:00-20:00)")
        )
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        "[C]hoose reminder time",
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
                    "To help you remember to repeat the words, we will send you reminders. You can choose what time you want to receive them.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth().padding(24.dp)
                )

                times.fastForEach { time ->
                    Row(
                        Modifier.fillMaxWidth()
                            .clickable { onSelectTime(time.first) }
                            .padding(horizontal = 24.dp, vertical = 12.dp)
                    ) {
                        Text(
                            time.second,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}
