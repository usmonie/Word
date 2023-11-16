package extensions

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configures base Kotlin options for JVM (non-Android) in a Gradle project.
 */
internal fun Project.configureKotlinJvm() {
    // Configure Kotlin source and target compatibility for JVM.
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

/**
 * Configures base Kotlin options in a Gradle project.
 */
internal fun Project.configureKotlin() {
    // Configure Kotlin options for all Kotlin compile tasks.
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            // Add any additional compiler arguments if needed.
            freeCompilerArgs = freeCompilerArgs + listOf()
        }
    }
}