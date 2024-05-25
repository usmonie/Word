import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    id(libs.plugins.usmonie.multiplatform.core.get().pluginId)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

android.namespace = "com.usmonie.compass.core"

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )
}

commonDependencies {
    implementation(projects.shared.core.domain)
    implementation(projects.shared.core.kit)
    implementation("io.github.theapache64:rebugger:1.0.0-rc03")
}

androidDependencies {
    implementation(libs.androidx.activity.compose)
}

android {
    buildFeatures.compose = true

    composeOptions.kotlinCompilerExtensionVersion = "1.5.14"
}

task("testClasses")
