package com.usmonie.compass.core.ui

import androidx.collection.ScatterMap
import androidx.collection.mutableScatterMapOf
import com.usmonie.compass.core.Extra

/**
 * Interface defining the core functionalities of a screen builder.
 */
interface ScreenFactory {
    /**
     * Unique identifier for the screen.
     */
    val id: ScreenId

    /**
     * Constructs a [Screen] instance using the provided parameters and extra data.
     *
     * @param params Optional parameters to pass to the screen.
     * @param extra Optional extra data to pass to the screen.
     * @return Constructed [Screen] instance.
     */
    operator fun invoke(params: ScatterMap<String, String>?, extra: Extra?): Screen
}

/**
 * Interface defining the functionalities of a screen builder that supports deep linking.
 */
interface DeepLinkScreenFactory : ScreenFactory {
    /**
     * Pattern used to match deep links for this screen.
     */
    val deepLinkPattern: String

    /**
     * Checks if the provided deep link matches the pattern for this screen.
     *
     * @param deepLink The deep link URI to check.
     * @return True if the deep link matches, false otherwise.
     */
    fun matches(deepLink: String): Boolean

    /**
     * Extracts parameters from the provided deep link based on the pattern.
     *
     * @param deepLink The deep link URI to extract parameters from.
     * @return A map of extracted parameters.
     */
    fun extractParameters(deepLink: String): ScatterMap<String, String>
}

/**
 * Abstract base implementation of [DeepLinkScreenFactory].
 *
 * Provides default implementations for matching deep links and extracting parameters.
 */
abstract class BaseDeepLinkScreenFactory : DeepLinkScreenFactory {
    override fun matches(deepLink: String): Boolean {
        // Convert {parameter} format to regex-friendly format
        val regexPattern = deepLinkPattern.replace(Regex("\\{[^}]+\\}"), ".+")
        return Regex(regexPattern).matches(deepLink)
    }

    override fun extractParameters(deepLink: String): ScatterMap<String, String> {
        val patternParts = deepLinkPattern.split("/")
        val deepLinkParts = deepLink.split("/")

        val parameters = mutableScatterMapOf<String, String>()
        for (index in patternParts.indices) {
            if (patternParts[index].startsWith("{") && patternParts[index].endsWith("}")) {
                val key = patternParts[index].trim('{', '}')
                parameters[key] = deepLinkParts.getOrNull(index) ?: ""
            }
        }

        return parameters
    }
}
