import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    id(libs.plugins.usmonie.multiplatform.core.get().pluginId)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    id(libs.plugins.usmonie.multiplatform.ui.get().pluginId)
}

android.namespace = "com.usmonie.core.tools"

kotlin {
    applyDefaultHierarchyTemplate()
}

commonDependencies {
    api(libs.koin.core)
    api(libs.koin.compose)
    api("co.touchlab:stately-common:2.0.6")
}

androidDependencies {
    api(libs.koin.android)
}

task("testClasses")
