package com.usmonie.core.kit.composables.base.text

import androidx.compose.runtime.Immutable
import kotlin.reflect.KProperty

@Immutable
data class ImmutableWrapper<T>(val value: T)

fun <T> T.toImmutableWrapper() = ImmutableWrapper(this)

operator fun <T> ImmutableWrapper<T>.getValue(thisRef: Any?, property: KProperty<*>) = value