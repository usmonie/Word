package com.usmonie.core.kit.composables.word

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.TextFieldValue
import com.usmonie.core.kit.composables.base.bar.SearchLargeTopBar
import com.usmonie.core.kit.composables.base.bar.SearchTopBar

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
            Column {
                AnimatedVisibility(header != null) {
                    header?.invoke()
                }

                if (header != null) {
                    SearchTopBar(
                        onBackClicked,
                        getShowBackButton,
                        placeholder,
                        query,
                        onQueryChanged,
                        hasSearchFieldFocus,
                        updateSearchFieldFocus,
                        { topAppBarScrollBehavior },
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
            }
        }
    )
}
