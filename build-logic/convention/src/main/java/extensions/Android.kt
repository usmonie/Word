package extensions

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

/**
 * Configures Android-specific settings for a Gradle project using the provided `LibraryExtension` extension.
 *
 * @param extension The `LibraryExtension` extension to configure Android settings for.
 */
internal fun Project.configureAndroid(extension: LibraryExtension) {
    with(extension) {
        // Set the compile SDK version based on the configured version in the project.
        compileSdk = 34 /*config.findVersion("android-compileSdk")
            .get()
            .requiredVersion.toInt()*/

        // Configure default settings for the Android application.
        defaultConfig {
            // Set the minimum SDK version based on the configured version in the project.
            minSdk = 24 /*config.findVersion("android-minSdk")
                .get()
                .requiredVersion.toInt()*/
        }

        // Configure Java source compatibility to version 11.
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        // Configure Compose options with a specific Kotlin compiler extension version.
        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.4"
        }

        // Configure source sets for the Android module.
        sourceSets {
            named("main") {
                // Specify the AndroidManifest.xml file location.
                manifest.srcFile("src/androidMain/AndroidManifest.xml")

                // Specify resource directories for the Android module.
                res.srcDirs("src/androidMain/res", "src/commonMain/resources")
            }
        }
    }
}