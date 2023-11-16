import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.core.get().pluginId)
}

android.namespace = "wtf.speech.shared.core.domain"

kotlin {
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

commonDependencies {
    api(libs.kotlinx.datetime)
    api(libs.coroutines.core)
    api(libs.kmlogging)
}