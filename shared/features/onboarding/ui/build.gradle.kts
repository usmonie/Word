
import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.core.get().pluginId)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.multiplatform)
    id(libs.plugins.speech.multiplatform.ui.get().pluginId)
}

android.namespace = "com.usmonie.word.features.onboarding.ui"

commonDependencies {
    implementation(projects.compass.core)
    implementation(projects.shared.core.ui)
    implementation(projects.shared.core.design)
    implementation(projects.shared.features.dashboard.domain)
    implementation(projects.shared.features.onboarding.domain)
    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
    implementation(libs.compose.material3.jetbrains)
}

androidDependencies {
    implementation(libs.compose.ui)
    implementation(libs.google.admob)
    implementation("com.google.firebase:firebase-auth:22.3.1")

    // Also add the dependency for the Google Play services library and specify its version
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    api("androidx.credentials:credentials:1.3.0-alpha01")
    api("androidx.credentials:credentials-play-services-auth:1.3.0-alpha01")
    api("com.google.android.libraries.identity.googleid:googleid:1.1.0")
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

        val commonMain by getting {
            dependencies {
                implementation(compose.components.resources)

//                api(libs.kmpauth.core) //Google One Tap Sign-In
//                api(libs.kmpauth.google) //Google One Tap Sign-In
//                api(libs.kmpauth.firebase) //Integrated Authentications with Firebase
//                implementation(libs.kmpauth.uihelper)
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
    namespace = "com.usmonie.word.features.onboarding.ui"

    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = 24 //config.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

task("testClasses")