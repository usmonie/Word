import extensions.androidDependencies
import extensions.commonDependencies
import extensions.iOSDependencies

plugins {
    id(libs.plugins.usmonie.multiplatform.domain.get().pluginId)
    kotlin("plugin.serialization") version "1.9.0"
    id(libs.plugins.realm.get().pluginId)
}

android.namespace = "com.usmonie.word.features.dictionary.data"

kotlin {
    applyDefaultHierarchyTemplate()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )

    commonDependencies {
        implementation(libs.kotlinx.datetime)
        implementation(libs.kotlinx.serialization)
        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.serialization.kotlinx.json)
        implementation(libs.realm.base)
        implementation(libs.realm.sync)

        api(projects.shared.core.domain)
        api(projects.shared.core.tools)

        api(projects.shared.feature.dictionary.domain)
    }

    androidDependencies {
        implementation(libs.ktor.client.okhttp)
    }

    iOSDependencies {
        implementation(libs.ktor.client.darwin)
    }
}

task("testClasses")
