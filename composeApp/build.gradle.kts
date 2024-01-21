@file:Suppress("OPT_IN_USAGE")

import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink
import org.jetbrains.kotlin.konan.target.CompilerOutputKind

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose)
    alias(libs.plugins.androidx.baselineprofile)
    alias(libs.plugins.google.services)
    id("com.google.firebase.crashlytics")
}

kotlin {
    applyDefaultHierarchyTemplate()
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
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
            version = "1.2.0"

            dependencies {
                implementation(projects.core.design)
                implementation(projects.features.dashboard.domain)
                implementation(projects.features.dashboard.ui)
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.features.dashboard.ui)
                implementation(projects.features.dashboard.domain)
                implementation(projects.features.dashboard.data)

                implementation(projects.features.subscription.domain)
                implementation(projects.features.subscription.data)

                implementation(projects.compass.core)
                implementation(projects.core.design)
                implementation(projects.core.domain)
                implementation(projects.core.ui)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)

                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.compose.ui)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.compose.activity)
                implementation(libs.firebase.analytics)
                implementation(libs.android.billing.ktx)
                implementation("com.amplitude:analytics-android:1.+")
            }
        }
    }

    targets.withType<KotlinNativeTarget> {
        binaries.withType<Framework> {
            isStatic = false
            linkerOpts.add("-lsqlite3")

//            export(projects.core.design)
            transitiveExport = true
        }
    }
}

android {
    namespace = "com.usmonie.word"
    compileSdk = 34 // config.versions.android.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.usmonie.word"
        minSdk = 25 //config.versions.android.minSdk.get().toInt()
        targetSdk = 34

        versionCode = 16
        versionName = "1.2.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"// libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            versionNameSuffix = ".debug"
        }
        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    dependencies {

        implementation("org.slf4j:slf4j-api:1.7.30")
        implementation("org.slf4j:slf4j-simple:1.7.30")
        debugImplementation(libs.compose.ui.tooling)

        implementation(libs.androidx.profileinstaller)
        implementation(libs.google.admob)

        "baselineProfile"(project(":baselineprofile"))
    }
}

dependencies {
    debugImplementation(libs.androidx.runtime.tracing)
    debugImplementation(libs.androidx.tracing.perfetto)
    debugImplementation(libs.androidx.tracing.perfetto.binary)
    debugImplementation(libs.compose.ui.tooling)
}
