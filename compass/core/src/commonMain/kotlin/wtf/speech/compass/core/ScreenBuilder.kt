package wtf.speech.compass.core

/**
 * Interface defining the core functionalities of a screen builder.
 */
interface ScreenBuilder {
    /**
     * Unique identifier for the screen.
     */
    val id: String

    /**
     * Constructs a [Screen] instance using the provided parameters and extra data.
     *
     * @param params Optional parameters to pass to the screen.
     * @param extra Optional extra data to pass to the screen.
     * @return Constructed [Screen] instance.
     */
    fun build(params: Map<String, String>?, extra: Extra?): Screen
}

/**
 * Interface defining the functionalities of a screen builder that supports deep linking.
 */
interface DeepLinkScreenBuilder: ScreenBuilder {
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
    fun extractParameters(deepLink: String): Map<String, String>
}

/**
 * Abstract base implementation of [DeepLinkScreenBuilder].
 *
 * Provides default implementations for matching deep links and extracting parameters.
 */
abstract class BaseDeepLinkScreenBuilder : DeepLinkScreenBuilder {
    override fun matches(deepLink: String): Boolean {
        // Convert {parameter} format to regex-friendly format
        val regexPattern = deepLinkPattern.replace(Regex("\\{[^}]+\\}"), ".+")
        return Regex(regexPattern).matches(deepLink)
    }

    override fun extractParameters(deepLink: String): Map<String, String> {
        val patternParts = deepLinkPattern.split("/")
        val deepLinkParts = deepLink.split("/")

        val parameters = mutableMapOf<String, String>()
        for (index in patternParts.indices) {
            if (patternParts[index].startsWith("{") && patternParts[index].endsWith("}")) {
                val key = patternParts[index].trim('{', '}')
                parameters[key] = deepLinkParts.getOrNull(index) ?: ""
            }
        }

        return parameters
    }
}
