package com.usmonie.core.kit.composables.base.bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.usmonie.core.kit.composables.base.buttons.NavigationBack
import com.usmonie.core.kit.composables.base.device.isKeyboardOpen
import com.usmonie.core.kit.composables.base.text.RESIZEABLE_TEXT_MIN_FRACTION

private val LargeTopBarSearchFieldModifier = Modifier.fillMaxWidth()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLargeTopBar(
    onBackClicked: () -> Unit,
    getShowBackButton: () -> Boolean,
    placeholder: () -> String,
    query: () -> TextFieldValue,
    onQueryChanged: (TextFieldValue) -> Unit,
    hasFocus: () -> Boolean,
    onFocusChanged: (Boolean) -> Unit,
    getScrollBehavior: () -> TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.largeTopAppBarColors()
) {
    val scrollBehavior by derivedStateOf(getScrollBehavior)
    val showBackButton by derivedStateOf(getShowBackButton)

    LargeTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            AnimatedVisibility(showBackButton) {
                NavigationBack({ query().text.isNotEmpty() }, onBackClicked)
            }
        },
        title = {
            SearchFieldTopBar(
                placeholder,
                query,
                onQueryChanged,
                hasFocus,
                onFocusChanged,
                { scrollBehavior.state.collapsedFraction },
                LargeTopBarSearchFieldModifier,
            )
        },
        colors = colors
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLargeTopBar(
    placeholder: () -> String,
    query: () -> TextFieldValue,
    onQueryChanged: (TextFieldValue) -> Unit,
    hasFocus: () -> Boolean,
    onFocusChanged: (Boolean) -> Unit,
    getScrollBehavior: () -> TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.largeTopAppBarColors()
) {
    val scrollBehavior = derivedStateOf { getScrollBehavior() }

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val isKeyboardVisible = isKeyboardOpen()
    LaunchedEffect(isKeyboardVisible) {
        if (!isKeyboardVisible) {
            focusManager.clearFocus(true)
            focusRequester.freeFocus()
        }
    }

    LargeTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior.value,
        title = {
            SearchFieldTopBar(
                placeholder,
                query,
                onQueryChanged,
                hasFocus,
                onFocusChanged,
                { scrollBehavior.value.state.collapsedFraction },
                LargeTopBarSearchFieldModifier,
                focusRequester = focusRequester
            )
        },
        colors = colors
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextLargeTopBar(
    placeholder: () -> String,
    getScrollBehavior: () -> TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.largeTopAppBarColors(),
    textStyle: TextStyle = MaterialTheme.typography.displayMedium,
) {
    val scrollBehavior = derivedStateOf { getScrollBehavior() }

    LargeTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior.value,
        title = {
            Text(
                placeholder(),
                style = textStyle,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        colors = colors
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextLargeTopBar(
    onBackClicked: () -> Unit,
    placeholder: () -> String,
    getScrollBehavior: () -> TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.largeTopAppBarColors(),
    titleTextStyle: TextStyle = MaterialTheme.typography.displayMedium,
    minTextSize: Float = RESIZEABLE_TEXT_MIN_FRACTION,
    actions: @Composable RowScope.() -> Unit = {},
) {
    val scrollBehavior = getScrollBehavior()

    LargeTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = { NavigationBack(onBackClicked, Modifier.padding(start = 12.dp)) },
        actions = actions,
        title = {
            Column {
                val fraction = scrollBehavior.state.collapsedFraction

                val titleStyle = remember(fraction) {
                    titleTextStyle.copy(fontSize = titleTextStyle.fontSize * (1 - fraction).coerceIn(minTextSize, 1f))
                }
                Text(
                    placeholder(),
                    style = titleStyle,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                )
            }
        },
        colors = colors
    )
}
