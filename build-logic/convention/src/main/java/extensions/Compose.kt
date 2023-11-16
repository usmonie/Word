package extensions

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project

/**
 * Configures Compose-specific settings for a Gradle project using the provided `LibraryExtension` extension.
 *
 * @param extension The `LibraryExtension` extension to configure Compose settings for.
 */
internal fun Project.configureCompose(extension: LibraryExtension) {
    with(extension) {
        // Enable Compose build features.
        buildFeatures.compose = true

        // Set the Kotlin compiler extension version for Compose based on the configured version in the project.
        composeOptions.kotlinCompilerExtensionVersion = "1.5.4"
    }
}