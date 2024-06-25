import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    alias(libs.plugins.compose.jetbrains)
    alias(libs.plugins.compose.compiler)
    id(libs.plugins.usmonie.multiplatform.ui.get().pluginId)
}

android.namespace = "com.usmonie.word.features.ads.ui"

kotlin {
    applyDefaultHierarchyTemplate()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    namespace = "com.usmonie.word.features.ads.ui"

    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

composeCompiler {
    enableIntrinsicRemember = true
    enableStrongSkippingMode = true
}

commonDependencies {
    implementation(projects.shared.feature.subscriptions.domain)
}

androidDependencies {
    implementation(libs.compose.ui)
}

task("testClasses")
