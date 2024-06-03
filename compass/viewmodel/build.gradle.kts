import extensions.commonDependencies

plugins {
    id(libs.plugins.usmonie.multiplatform.core.get().pluginId)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

android.namespace = "com.usmonie.compass.viewmodel"

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )
}

commonDependencies {
    api(projects.shared.core.domain)
    api(projects.shared.core.kit)
    api(projects.compass.core)
    implementation("io.github.theapache64:rebugger:1.0.0-rc03")
}

android {
    buildFeatures.compose = true
}

composeCompiler {
    enableIntrinsicRemember = true
    enableStrongSkippingMode = true
}

task("testClasses")
