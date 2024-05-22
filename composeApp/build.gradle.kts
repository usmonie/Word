plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
//    alias(libs.plugins.compose.compiler)
}

//composeCompiler {
//    enableStrongSkippingMode = true
//    enableIntrinsicRemember = true
//}

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
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
        }
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

//            implementation(projects.shared.feature.ads.ui)
            implementation(projects.shared.feature.details.ui)

            implementation(projects.shared.feature.dictionary.data)
            implementation(projects.shared.feature.dictionary.domain)
            implementation(projects.shared.feature.dictionary.ui)

            implementation(projects.shared.feature.dashboard.data)
            implementation(projects.shared.feature.dashboard.domain)
            implementation(projects.shared.feature.dashboard.ui)

            implementation(projects.shared.feature.favorites.ui)

            implementation(projects.shared.feature.subscriptions.data)
            implementation(projects.shared.feature.subscriptions.domain)
            implementation(projects.shared.feature.subscriptions.ui)
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.androidx.runtime.tracing)
    debugImplementation(libs.androidx.tracing.perfetto)
    debugImplementation(libs.androidx.tracing.perfetto.binary)
    debugImplementation(libs.compose.ui.tooling)
}
