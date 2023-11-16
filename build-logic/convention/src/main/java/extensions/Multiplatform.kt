package extensions

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Configures targets for a Kotlin Multiplatform project using the provided `KotlinMultiplatformExtension` extension.
 */
internal fun KotlinMultiplatformExtension.configureTarget() {
    // Configure Android target.
    androidTarget()

    // Configure iOS x64 target.
    iosX64()

    // Configure iOS ARM64 target.
    iosArm64()

    // Configure iOS Simulator ARM64 target.
    iosSimulatorArm64()
}

/**
 * Configures the JVM toolchain for a Kotlin Multiplatform project using the provided `KotlinMultiplatformExtension` extension.
 */
internal fun KotlinMultiplatformExtension.configureJvmToolchain() {
    // Configure JVM toolchain with the specified Java version.
    jvmToolchain(11)
}
