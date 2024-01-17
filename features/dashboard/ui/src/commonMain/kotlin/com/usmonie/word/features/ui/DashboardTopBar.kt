package com.usmonie.word.features.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DashboardTopBar(
    onBackClick: () -> Unit,
    onQueryChanged: (TextFieldValue) -> Unit,
    enabled: Boolean,
    placeholder: String,
    query: TextFieldValue,
    hasFocus: MutableState<Boolean>,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val searchBarFontSizeMax = MaterialTheme.typography.displayLarge.fontSize
    val fontSize by remember(scrollBehavior.state.collapsedFraction) {
        derivedStateOf {
            searchBarFontSizeMax * (1 - scrollBehavior.state.collapsedFraction).coerceIn(0.6f, 1f)
        }
    }
    LargeTopAppBar(
        title = {
            SearchTopBar(
                query,
                placeholder,
                enabled,
                { fontSize },
                onQueryChanged,
                hasFocus
            )
        },
        navigationIcon = { NavigationBack({ query.text.isNotEmpty() }, onBackClick) },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.Transparent
        ),
        scrollBehavior = scrollBehavior
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DashboardTopBar(
    onBackClick: () -> Unit,
    placeholder: String,
    showNavigationBack: () -> Boolean,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val query = remember { TextFieldValue() }
    val searchBarFontSizeMax = MaterialTheme.typography.displayLarge.fontSize
    val fontSize by remember(scrollBehavior.state.collapsedFraction) {
        derivedStateOf {
            searchBarFontSizeMax * (1 - scrollBehavior.state.collapsedFraction).coerceIn(0.6f, 1f)
        }
    }
    LargeTopAppBar(
        title = {
            SearchTopBar(
                query,
                placeholder,
                false,
                { fontSize },
                { },
                remember { mutableStateOf(false) }
            )
        },
        navigationIcon = { NavigationBack(showNavigationBack, onBackClick) },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.Transparent
        ),
        scrollBehavior = scrollBehavior
    )
}


@Composable
private fun SearchTopBar(
    query: TextFieldValue,
    placeholder: String,
    enabled: Boolean,
    fontSize: () -> TextUnit,
    onQueryChanged: (TextFieldValue) -> Unit,
    hasFocus: MutableState<Boolean>,
) {
    TopBar(
        query,
        placeholder,
        onQueryChanged,
        enabled,
        hasFocus.value,
        fontSize,
        onFocusChange = { hasFocus.value = it }
    )
}

@Composable
private fun TopBar(
    query: TextFieldValue,
    placeholder: String,
    onQueryChanged: (TextFieldValue) -> Unit,
    enabled: Boolean,
    hasFocus: Boolean,
    fontSize: () -> TextUnit,
    onFocusChange: (Boolean) -> Unit
) {
    SearchBar(
        onQueryChanged,
        placeholder = placeholder,
        query = query,
        modifier = Modifier.offset(x = (-16).dp).fillMaxWidth(),
        hasFocus = { hasFocus },
        enabled = enabled,
        fontSize = fontSize,
        onFocusChange = onFocusChange,
    )
}

@Composable
private fun NavigationBack(showBackButton: () -> Boolean, onClick: () -> Unit) {
    AnimatedVisibility(showBackButton()) {
        IconButton(onClick) {
            NavigationBackIcon()
        }
    }
}

@Composable
private fun NavigationBackIcon() {
    Icon(
        Icons.Default.ArrowBack,
        contentDescription = Icons.Default.ArrowBack.name,
        tint = MaterialTheme.colorScheme.onPrimary
    )
}