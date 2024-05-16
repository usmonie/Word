package com.usmonie.core.kit.composables.word

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.usmonie.core.kit.composables.base.bar.TextLargeTopBar
import com.usmonie.core.kit.composables.base.bar.TextTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeWordScaffold(
    placeholder: () -> String,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        content = content,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
        bottomBar = bottomBar,
        topBar = {
            TextTopBar(
                onBackClicked,
                placeholder,
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderWordScaffold(
    placeholder: () -> String,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    headerState: () -> HeaderState,
    expandedHeader: @Composable () -> Unit = {},
    collapsedHeader: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    topBarBottom: @Composable ColumnScope.() -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        content = content,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
        bottomBar = bottomBar,
        topBar = {
            Column {
                val currentState = derivedStateOf { headerState() }

                AnimatedContent(
                    currentState.value,
                    transitionSpec = { expandVertically() togetherWith shrinkVertically() }
                ) { state ->
                    when (state) {
                        HeaderState.Expanded -> expandedHeader()
                        HeaderState.Collapsed -> collapsedHeader()
                        HeaderState.Close -> Unit
                    }
                }

                if (currentState.value != HeaderState.Close) {
                    TextTopBar(
                        onBackClicked,
                        placeholder,
                        actions = actions
                    )
                } else {
                    TextLargeTopBar(
                        onBackClicked,
                        placeholder,
                        { topAppBarScrollBehavior },
                        actions = actions,
                    )
                }

                topBarBottom()
            }
        }
    )
}

enum class HeaderState {
    Expanded,
    Collapsed,
    Close
}
