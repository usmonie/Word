import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.core.get().pluginId)
}

android.namespace = "wtf.speech.core.ui"

commonDependencies {
    api(projects.core.domain)
    api(projects.core.design)
    api(projects.compass.core)
}

android {
    buildFeatures.compose = true

    composeOptions.kotlinCompilerExtensionVersion = "1.5.4"
}