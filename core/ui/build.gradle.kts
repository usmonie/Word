import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.core.get().pluginId)
}

android.namespace = "wtf.speech.core.ui"

commonDependencies {
    api(projects.core.domain)
    api(projects.core.design)
    api(projects.compass.core)
    api("io.github.theapache64:rebugger:1.0.0-rc02")

    api("dev.icerock.moko:resources:0.23.0")
    api("dev.icerock.moko:resources-compose:0.23.0")
}

android {
    buildFeatures.compose = true

    composeOptions.kotlinCompilerExtensionVersion = "1.5.4"
}