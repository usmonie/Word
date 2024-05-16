import extensions.commonDependencies

plugins {
    id(libs.plugins.usmonie.multiplatform.core.get().pluginId)
}

android.namespace = "com.usmonie.shared.core.domain"

commonDependencies {
    api(libs.kotlinx.datetime)
    api(libs.coroutines.core)
    api(libs.kmlogging)
}

task("testClasses")
