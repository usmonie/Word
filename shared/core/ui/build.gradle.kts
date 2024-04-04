import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.core.get().pluginId)
    alias(libs.plugins.compose)
}

android.namespace = "com.usmonie.word.shared.core.ui"

commonDependencies {
    api(projects.shared.core.domain)
    api(projects.shared.core.design)
    api(projects.compass.core)
//    api(libs.appyx.navigation)
//    api(libs.appyx.interactions)
//    api(libs.appyx.backstack)
    api("io.github.theapache64:rebugger:1.0.0-rc02")
}

android {
    buildFeatures.compose = true

    composeOptions.kotlinCompilerExtensionVersion = "1.5.11"
}

task("testClasses")
