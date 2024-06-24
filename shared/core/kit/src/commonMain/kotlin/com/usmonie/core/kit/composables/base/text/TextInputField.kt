package com.usmonie.core.kit.composables.base.text

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun BaseTextInputField(
	value: TextFieldValue,
	onValueChange: (TextFieldValue) -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	readOnly: Boolean = false,
	textStyle: TextStyle = LocalTextStyle.current,
	label: @Composable (() -> Unit)? = null,
	placeholder: @Composable (() -> Unit)? = null,
	leadingIcon: @Composable (() -> Unit)? = null,
	trailingIcon: @Composable (() -> Unit)? = null,
	prefix: @Composable (() -> Unit)? = null,
	suffix: @Composable (() -> Unit)? = null,
	supportingText: @Composable (() -> Unit)? = null,
	isError: Boolean = false,
	visualTransformation: VisualTransformation = VisualTransformation.None,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	keyboardActions: KeyboardActions = KeyboardActions.Default,
	singleLine: Boolean = false,
	maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
	minLines: Int = 1,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	shape: Shape = OutlinedTextFieldDefaults.shape,
	colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
		unfocusedBorderColor = Color.Transparent,
		focusedBorderColor = Color.Transparent,
		disabledBorderColor = Color.Transparent,
		errorBorderColor = Color.Transparent,
		cursorColor = MaterialTheme.colorScheme.onBackground,
		focusedTextColor = MaterialTheme.colorScheme.onBackground,
		unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
	)
) {
	OutlinedTextField(
		value,
		onValueChange,
		modifier,
		enabled,
		readOnly,
		textStyle,
		label,
		placeholder,
		leadingIcon,
		trailingIcon,
		prefix,
		suffix,
		supportingText,
		isError,
		visualTransformation,
		keyboardOptions,
		keyboardActions,
		singleLine,
		maxLines,
		minLines,
		interactionSource,
		shape,
		colors
	)
}

@Composable
fun ResizableBaseTextInputField(
	textStyle: TextStyle,
	onTextChanged: (TextFieldValue) -> Unit,
	placeholder: () -> String,
	text: () -> TextFieldValue,
	hasFocus: () -> Boolean,
	modifier: Modifier = Modifier,
	getFraction: () -> Float,
	enabled: Boolean = true,
	keyboardActions: KeyboardActions = KeyboardActions.Default,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	minTextSize: Float = RESIZEABLE_TEXT_MIN_FRACTION,
	colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
		focusedTextColor = MaterialTheme.colorScheme.onBackground,
		unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
		focusedBorderColor = Color.Transparent,
		unfocusedBorderColor = Color.Transparent,
		disabledBorderColor = Color.Transparent,
	),
) {
	val fraction = getFraction()

	val customTextStyle = remember(fraction) {
		textStyle.copy(
			fontSize = textStyle.fontSize * (1 - fraction).coerceIn(minTextSize, 1f)
		)
	}

	BaseTextInputField(
		text(),
		onTextChanged,
		enabled = enabled,
		readOnly = !enabled,
		modifier = modifier,
		colors = colors,
		textStyle = customTextStyle,
		placeholder = {
			PlaceholderTextField(
				hasFocus,
				placeholder,
				textStyle = { customTextStyle }
			)
		},
		keyboardActions = keyboardActions,
		keyboardOptions = keyboardOptions,
	)
}

@Composable
private fun PlaceholderTextField(
	hasFocus: () -> Boolean,
	placeholder: () -> String,
	textStyle: () -> TextStyle
) {
	val color = MaterialTheme.colorScheme.onBackground
	val placeholderAlphaAnimation by animateFloatAsState(if (hasFocus()) PLACEHOLDER_FOCUSED_ALPHA else 1f)

	AutoSizeText(
		placeholder(),
		style = textStyle(),
		color = color,
		maxLines = 3,
		minLines = 1,
		maxTextSize = textStyle().fontSize,
		minTextSize = MaterialTheme.typography.bodyLarge.fontSize,
		modifier = Modifier
			.fillMaxWidth()
			.graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
			.drawWithContent {
				drawContent()

				drawRect(
					color = color.copy(alpha = placeholderAlphaAnimation),
					blendMode = BlendMode.SrcIn
				)
			}
	)
}

const val PLACEHOLDER_FOCUSED_ALPHA = .5f
const val THRESHOLD_SIZE = .5f
const val RESIZEABLE_TEXT_MIN_FRACTION = .55f
