import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    alias(libs.plugins.jetbrainsCompose)
    id(libs.plugins.usmonie.multiplatform.ui.get().pluginId)
}

android.namespace = "com.usmonie.word.features.ads.ui"

commonDependencies {
    implementation(projects.shared.feature.subscriptions.domain)
}

androidDependencies {
    implementation(libs.compose.ui)
}

kotlin {
    applyDefaultHierarchyTemplate()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )
}

android {
    compileSdk = 34 // config.versions.android.compileSdk.get().toInt()
    namespace = "com.usmonie.word.features.ads.ui"

    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = 24 // config.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

task("testClasses")
