import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    id(libs.plugins.usmonie.multiplatform.core.get().pluginId)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    id(libs.plugins.usmonie.multiplatform.ui.get().pluginId)
}

android.namespace = "com.usmonie.core.tools"

kotlin {
    applyDefaultHierarchyTemplate()
}

commonDependencies {
    api(libs.koin.core)
}

androidDependencies {
    api(libs.koin.android)
}

task("testClasses")
