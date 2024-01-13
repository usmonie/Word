package com.usmonie.word.features.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger

@Composable
fun BaseLazyColumn(
    listState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(0.dp),
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) {
    Rebugger(
        trackMap = mapOf(
            "listState" to listState,
            "contentPadding" to contentPadding,
            "verticalArrangement" to verticalArrangement,
            "modifier" to modifier,
            "content" to content,
        ),
        composableName = "BaseLazyList"
    )
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .testTag("DASHBOARD_LAZY_COLUMN"),
        verticalArrangement = verticalArrangement,
        state = listState,
        contentPadding = contentPadding,
        content = content
    )
}
