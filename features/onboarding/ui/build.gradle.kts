
import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    alias(libs.plugins.compose)
    id(libs.plugins.speech.multiplatform.ui.get().pluginId)
}

android.namespace = "com.usmonie.word.features.onboarding.ui"

commonDependencies {
    implementation(projects.compass.core)
    implementation(projects.core.ui)
    implementation(projects.features.dashboard.domain)
    implementation(projects.features.onboarding.domain)
    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
//    implementation(compose.components.resources)
    implementation(libs.compose.material3.jetbrains)
//    implementation(libs.compose.material3)
}
androidDependencies {
    implementation(libs.compose.ui)
    implementation(libs.google.admob)
}

kotlin {
    applyDefaultHierarchyTemplate()

    sourceSets {
        getByName("commonTest") {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        getByName("androidUnitTest") {
            dependencies {
                implementation(libs.junit)
            }
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            version = "1.0.0"
        }
    }
}

android {
    compileSdk = 34 // config.versions.android.compileSdk.get().toInt()
    namespace = "com.usmonie.word.features.learning.ui"

//    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = 24 //config.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
