import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.core.get().pluginId)
    alias(libs.plugins.compose)
}

android.namespace = "wtf.speech.core.ui"

commonDependencies {
    api(projects.core.domain)
    api(projects.core.design)
    api(projects.compass.core)
    api("io.github.theapache64:rebugger:1.0.0-rc02")
}

android {
    buildFeatures.compose = true

    composeOptions.kotlinCompilerExtensionVersion = "1.5.8"
}
task("testClasses")