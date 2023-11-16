import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    alias(libs.plugins.compose)
    id(libs.plugins.speech.multiplatform.ui.get().pluginId)
}

android.namespace = "com.usmonie.word.features.dashboard.ui"

commonDependencies {
    implementation(projects.compass.core)
    implementation(projects.core.ui)
    implementation(projects.features.dashboard.domain)
    implementation(projects.features.dashboard.data)

    implementation(libs.kvault)

}
androidDependencies {
    implementation(libs.compose.ui)
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
    namespace = "wtf.speech.features.dashboard.ui"

//    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
//    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = 24 //config.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
