package com.usmonie.core.kit.composables.base.bar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.usmonie.core.kit.composables.base.buttons.NavigationBack
import com.usmonie.core.kit.composables.base.device.isKeyboardOpen

private val TopBarSearchFieldModifier = Modifier.offset(x = (-12).dp).fillMaxWidth()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
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

    TopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior.value,
        navigationIcon = { NavigationBack(getShowBackButton, onBackClicked) },
        title = {
            SearchFieldTopBar(
                placeholder,
                query,
                onQueryChanged,
                hasFocus,
                onFocusChanged,
                { scrollBehavior.value.state.collapsedFraction },
                TopBarSearchFieldModifier,
                focusRequester = focusRequester
            )
        },
        colors = colors
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
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

    TopAppBar(
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
                TopBarSearchFieldModifier,
                focusRequester = focusRequester
            )
        },
        colors = colors
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextTopBar(
    onBackClicked: () -> Unit,
    placeholder: () -> String,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.largeTopAppBarColors(),
    textStyle: TextStyle = MaterialTheme.typography.displayMedium,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = { NavigationBack(onBackClicked) },
        title = {
            Text(
                placeholder(),
                style = textStyle,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
            )
        },
        colors = colors,
        actions = actions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextTopBar(
    placeholder: () -> String,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.largeTopAppBarColors(),
    textStyle: TextStyle = MaterialTheme.typography.displayMedium,
) {
    TopAppBar(
        modifier = modifier,
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
