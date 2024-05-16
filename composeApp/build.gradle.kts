plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
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
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
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

//            implementation(projects.shared.feature.subscriptions.data)
            implementation(projects.shared.feature.subscriptions.domain)
        }
    }
}

android {
    namespace = "com.usmonie.word"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.usmonie.word"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

// tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//     compilerOptions.freeCompilerArgs.addAll(
//         "-P",
//         "plugin:androidx.compose.compiler.plugins.kotlin:featureFlag=StrongSkipping",
//     )
// }

dependencies {
    implementation(project(":shared:feature:subscriptions:domain"))
}
