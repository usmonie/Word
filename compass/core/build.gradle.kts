import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.core.get().pluginId)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
}

android.namespace = "wtf.speech.compass.core"

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Compass"
            isStatic = true
            version = "1.0.0"
        }
    }
}

commonDependencies {
    api(projects.core.domain)
    api(projects.core.design)
    implementation("io.github.theapache64:rebugger:1.0.0-rc02")

}

androidDependencies {
    implementation("androidx.compose.ui:ui-test-junit4:1.5.4")
}

android {
    buildFeatures.compose = true

    composeOptions.kotlinCompilerExtensionVersion = "1.5.8"
}
task("testClasses")