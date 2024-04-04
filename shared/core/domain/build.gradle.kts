import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.core.get().pluginId)
}

android.namespace = "wtf.speech.shared.core.domain"

commonDependencies {
    api(libs.kotlinx.datetime)
    api(libs.coroutines.core)
    api(libs.kmlogging)
}

task("testClasses")
