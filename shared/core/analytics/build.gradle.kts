import extensions.androidDependencies

plugins {
    id(libs.plugins.usmonie.multiplatform.domain.get().pluginId)
}

android.namespace = "com.usmonie.word.core.analytics.ui"

androidDependencies {
    implementation(libs.analytics.amplitude.android)
    implementation(libs.firebase.analytics)
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
    )
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    namespace = "com.usmonie.word.features.analytics.ui"

    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

task("testClasses")
