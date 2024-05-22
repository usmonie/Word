package com.usmonie.core.kit.composables.base.bar

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.IntSize
import com.usmonie.core.kit.composables.base.device.isKeyboardOpen
import com.usmonie.core.kit.composables.base.text.ResizableBaseTextInputField

private const val THRESHOLD = 0.5f

@Composable
fun SearchFieldTopBar(
    placeholder: () -> String,
    query: () -> TextFieldValue,
    onQueryChanged: (TextFieldValue) -> Unit,
    hasFocus: () -> Boolean,
    onFocusChange: (Boolean) -> Unit,
    getFraction: () -> Float,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.displayMedium,
    enabledGesture: Boolean = true,
    enabled: Boolean = true,
    focusRequester: FocusRequester = remember { FocusRequester() },
) {
    val focusManager = LocalFocusManager.current

    var textFieldSize = remember { IntSize.Zero }
    val threshold: Float = remember(textFieldSize) { textFieldSize.width * THRESHOLD }
    val keyboard = LocalSoftwareKeyboardController.current

    val isKeyboardVisible = isKeyboardOpen()
    LaunchedEffect(isKeyboardVisible) {
        if (!isKeyboardVisible) {
            onFocusChange(false)
            focusManager.clearFocus(true)
            focusRequester.freeFocus()
        }
    }

    ResizableBaseTextInputField(
        textStyle,
        onQueryChanged,
        placeholder,
        query,
        hasFocus,
        modifier
            .focusRequester(focusRequester)
            .onFocusChanged { onFocusChange(it.hasFocus) }
            .onGloballyPositioned { coordinates -> textFieldSize = coordinates.size }
            .pointerInput(Unit) {
                if (enabledGesture) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        if (dragAmount < threshold) {
                            onQueryChanged(TextFieldValue())
                        }
                    }
                }
            },
        getFraction,
        enabled,
        keyboardActions = KeyboardActions {
            focusRequester.freeFocus()
            focusManager.clearFocus(true)
            keyboard?.hide()
            onFocusChange(false)
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    )
}
