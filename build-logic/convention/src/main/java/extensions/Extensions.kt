package extensions

import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

/**
 * Configures common dependencies for a Kotlin Multiplatform project in the "commonMain" source set using the provided block.
 *
 * @param block The block that configures dependencies using the `KotlinDependencyHandler`.
 */
fun Project.commonDependencies(block: KotlinDependencyHandler.() -> Unit) {
    extensions.getByType<KotlinMultiplatformExtension>()
        .sourceSets
        .getByName("commonMain")
        .dependencies(block)
}

/**
 * Configures Android-specific dependencies for a Kotlin Multiplatform project in the "androidMain" source set using the provided block.
 *
 * @param block The block that configures dependencies using the `KotlinDependencyHandler`.
 */
fun Project.androidDependencies(block: KotlinDependencyHandler.() -> Unit) {
    extensions.getByType<KotlinMultiplatformExtension>()
        .sourceSets
        .getByName("androidMain")
        .dependencies(block)
}

/**
 * Configures iOS-specific dependencies for a Kotlin Multiplatform project in the "iosMain" source set using the provided block.
 *
 * @param block The block that configures dependencies using the `KotlinDependencyHandler`.
 */
fun Project.iOSDependencies(block: KotlinDependencyHandler.() -> Unit) {
    extensions.getByType<KotlinMultiplatformExtension>()
        .sourceSets
        .getByName("iosMain")
        .dependencies(block)
}
