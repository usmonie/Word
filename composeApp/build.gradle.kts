plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.compose.jetbrains)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.androidx.baselineprofile)
    alias(libs.plugins.google.services)
//    id("com.google.firebase.crashlytics")
}

kotlin {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.compose)
            implementation(libs.koin.core)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(projects.compass.core)

            implementation(projects.shared.core.analytics)
            implementation(projects.shared.core.domain)
            implementation(projects.shared.core.kit)
            implementation(projects.shared.core.tools)

            implementation(projects.shared.feature.ads.ui)
            implementation(projects.shared.feature.details.ui)
            implementation(projects.shared.feature.dictionary.data)
            implementation(projects.shared.feature.dictionary.domain)
            implementation(projects.shared.feature.dictionary.ui)
            implementation(projects.shared.feature.dashboard.ui)
            implementation(projects.shared.feature.favorites.ui)
            implementation(projects.shared.feature.games.ui)
            implementation(projects.shared.feature.settings.data)
            implementation(projects.shared.feature.settings.domain)
            implementation(projects.shared.feature.settings.ui)
            implementation(projects.shared.feature.subscriptions.data)
            implementation(projects.shared.feature.subscriptions.domain)
            implementation(projects.shared.feature.subscriptions.ui)
            implementation(projects.shared.feature.quotes.data)
            implementation(projects.shared.feature.quotes.ui)
        }
    }
}

android {
    namespace = "com.usmonie.word"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")

    defaultConfig {
        applicationId = "com.usmonie.word"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 30
        versionName = "2.0-beta01"
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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

    buildFeatures {
        buildConfig = true
    }
}

composeCompiler {
    enableIntrinsicRemember = true
    enableStrongSkippingMode = true
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.compose.ui.tooling.preview)

    implementation(libs.analytics.amplitude.android)
    implementation(libs.firebase.analytics)
    implementation(libs.google.admob)
//    "baselineProfile"(project(":baselineprofile"))

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.androidx.runtime.tracing)
    debugImplementation(libs.androidx.tracing.perfetto)
    debugImplementation(libs.androidx.tracing.perfetto.binary)
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile> {
    dependsOn(tasks.withType<com.google.devtools.ksp.gradle.KspTask>())
}