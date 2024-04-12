@file:Suppress("OPT_IN_USAGE")

import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

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
                implementation(projects.shared.core.design)
                implementation(projects.shared.features.dashboard.domain)
                implementation(projects.shared.features.dashboard.ui)
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.shared.features.dashboard.ui)
                implementation(projects.shared.features.dashboard.domain)
                implementation(projects.shared.features.dashboard.data)

                implementation(projects.shared.features.onboarding.ui)
                implementation(projects.shared.features.onboarding.domain)
                implementation(projects.shared.features.onboarding.data)

                implementation(projects.shared.features.subscription.domain)
                implementation(projects.shared.features.subscription.data)

                implementation(projects.compass.core)
                implementation(projects.shared.core.design)
                implementation(projects.shared.core.domain)
                implementation(projects.shared.core.ui)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)

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
                implementation(libs.yandex.admob)
                implementation(libs.analytics.android)
            }
        }
    }

    targets.withType<KotlinNativeTarget> {
        binaries.withType<Framework> {
            isStatic = false
            linkerOpts.add("-lsqlite3")

//            export(projects.shared.core.design)
            transitiveExport = true
        }
    }
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("/Users/usmanakhmedov/Documents/word_new_new")
            storePassword = "usmanakhmedov"
            keyAlias = "usmonie"
            keyPassword = "usmonie"
        }
        create("debug_new") {
            storeFile = file("/Users/usmanakhmedov/Documents/word_new_new")
            storePassword = "usmanakhmedov"
            keyAlias = "usmonie"
            keyPassword = "usmonie"
        }
    }
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
        kotlinCompilerExtensionVersion = "1.5.8"// libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/*"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("debug_new")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            versionNameSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
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
        implementation(libs.play.services.auth)
        implementation(libs.firebase.auth)
        implementation(libs.slf4j.api)
        implementation(libs.slf4j.simple)
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
task("testClasses")
