
import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    alias(libs.plugins.jetbrainsCompose)
    id(libs.plugins.usmonie.multiplatform.ui.get().pluginId)
    id("dev.shreyaspatil.compose-compiler-report-generator") version "1.1.0"
}

android.namespace = "com.usmonie.word.features.dashboard.ui"

kotlin {
    applyDefaultHierarchyTemplate()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.components.resources)
            }
        }
    }
}

commonDependencies {
    implementation(projects.compass.core)
    implementation(projects.compass.viewmodel)
    implementation(projects.shared.core.kit)
    implementation(projects.shared.core.tools)

    implementation(projects.shared.feature.dashboard.domain)

    implementation(projects.shared.feature.dictionary.domain)
    implementation(projects.shared.feature.dictionary.ui)

    implementation(projects.shared.feature.subscriptions.domain)

    implementation(libs.compose.material3.jetbrains)
    implementation(compose.dependencies.materialIconsExtended)
    implementation(libs.kvault)
}

androidDependencies {
    implementation(libs.compose.ui)
    implementation("com.google.firebase:firebase-auth:22.3.1")

    // Also add the dependency for the Google Play services library and specify its version
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    api("androidx.credentials:credentials:1.3.0-alpha01")
    api("androidx.credentials:credentials-play-services-auth:1.3.0-alpha01")
    api("com.google.android.libraries.identity.googleid:googleid:1.1.0")
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
    namespace = "com.usmonie.word.features.dashboard.ui"

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
