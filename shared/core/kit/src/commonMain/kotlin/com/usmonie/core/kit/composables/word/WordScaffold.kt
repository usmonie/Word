package com.usmonie.core.kit.composables.word

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.usmonie.core.kit.composables.base.bar.TextLargeTopBar
import com.usmonie.core.kit.composables.base.bar.TextTopBar
import com.usmonie.core.kit.composables.base.scaffold.BaseHeaderScaffold

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
                { topAppBarScrollBehavior }
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
    header: @Composable (() -> Unit)? = null,
    bottomBar: @Composable () -> Unit = {},
    bottomAdBanner: (@Composable (PaddingValues) -> Unit)? = null,
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    topBarBottom: @Composable ColumnScope.() -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    topAppBarScrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    BaseHeaderScaffold(
        header ?: {}
    ) { baseInsets ->
        Column {
            val topInsets = if (header != null) baseInsets.calculateTopPadding() else 0.dp
            Scaffold(
                modifier = modifier
                    .weight(1f)
                    .padding(top = topInsets)
                    .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
                content = content,
                snackbarHost = snackbarHost,
                floatingActionButton = floatingActionButton,
                floatingActionButtonPosition = floatingActionButtonPosition,
                containerColor = containerColor,
                contentColor = contentColor,
                contentWindowInsets = contentWindowInsets,
                bottomBar = bottomBar,
                topBar = {
                    Column(verticalArrangement = Arrangement.Center) {
                        if (header != null) {
                            TextTopBar(
                                onBackClicked,
                                placeholder,
                                { topAppBarScrollBehavior },
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

            if (bottomAdBanner != null) {
                Box(Modifier.fillMaxWidth()) {
                    bottomAdBanner(baseInsets)
                }
            }
        }
    }
}

enum class HeaderState {
    Expanded,
    Collapsed,
    Close
}
