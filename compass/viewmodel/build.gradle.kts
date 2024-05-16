import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    id(libs.plugins.usmonie.multiplatform.core.get().pluginId)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
}

android.namespace = "com.usmonie.compass.core"

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Compass"
            isStatic = true
            version = "1.0.0"
        }
    }
}

commonDependencies {
    api(projects.shared.core.domain)
    api(projects.shared.core.kit)
    api(projects.compass.core)
    implementation("io.github.theapache64:rebugger:1.0.0-rc02")
}

android {
    buildFeatures.compose = true

    composeOptions.kotlinCompilerExtensionVersion = "1.5.12"
}

task("testClasses")