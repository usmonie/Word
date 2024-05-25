package com.usmonie.core.kit.composables.word

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.usmonie.core.kit.composables.base.bar.SearchLargeTopBar
import com.usmonie.core.kit.composables.base.bar.SearchTopBar
import com.usmonie.core.kit.composables.base.scaffold.BaseHeaderScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeWordScaffold(
    query: () -> TextFieldValue,
    onQueryChanged: (TextFieldValue) -> Unit,
    placeholder: () -> String,
    getShowBackButton: () -> Boolean,
    onBackClicked: () -> Unit,
    hasSearchFieldFocus: () -> Boolean,
    updateSearchFieldFocus: (Boolean) -> Unit,
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
            SearchLargeTopBar(
                onBackClicked,
                getShowBackButton,
                placeholder,
                query,
                onQueryChanged,
                hasSearchFieldFocus,
                updateSearchFieldFocus,
                { topAppBarScrollBehavior },
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderWordScaffold(
    query: () -> TextFieldValue,
    onQueryChanged: (TextFieldValue) -> Unit,
    getShowBackButton: () -> Boolean,
    placeholder: () -> String,
    onBackClicked: () -> Unit,
    hasSearchFieldFocus: () -> Boolean,
    updateSearchFieldFocus: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    header: (@Composable () -> Unit)? = null,
    bottomBar: @Composable () -> Unit = {},
    bottomAdBanner: (@Composable (PaddingValues) -> Unit)? = null,
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    topAppBarScrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
    content: @Composable (PaddingValues) -> Unit
) {
    BaseHeaderScaffold(
        header = header ?: {},
        content = { baseInsets ->
            Box {
                val topInsets = if (header != null) baseInsets.calculateTopPadding() else 0.dp
                Scaffold(
                    modifier = modifier.fillMaxSize()
                        .padding(top = topInsets)
                        .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
                    topBar = {
                        if (header != null) {
                            SearchTopBar(
                                onBackClicked = onBackClicked,
                                getShowBackButton = getShowBackButton,
                                placeholder = placeholder,
                                query = query,
                                onQueryChanged = onQueryChanged,
                                hasFocus = hasSearchFieldFocus,
                                onFocusChanged = updateSearchFieldFocus,
                                getScrollBehavior = { topAppBarScrollBehavior }
                            )
                        } else {
                            SearchLargeTopBar(
                                onBackClicked,
                                getShowBackButton,
                                placeholder,
                                query,
                                onQueryChanged,
                                hasSearchFieldFocus,
                                updateSearchFieldFocus,
                                { topAppBarScrollBehavior },
                            )
                        }
                    },
                    bottomBar = bottomBar,
                    snackbarHost = snackbarHost,
                    floatingActionButton = floatingActionButton,
                    floatingActionButtonPosition = floatingActionButtonPosition,
                    containerColor = containerColor,
                    contentColor = contentColor,
                    contentWindowInsets = contentWindowInsets,
                    content = content
                )

                if (bottomAdBanner != null) {
                    Box(Modifier.fillMaxWidth().align(Alignment.BottomCenter)) {
                        bottomAdBanner(baseInsets)
                    }
                }
            }
        }
    )
}
