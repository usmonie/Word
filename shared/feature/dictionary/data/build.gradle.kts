import extensions.androidDependencies
import extensions.commonDependencies
import extensions.iOSDependencies

plugins {
    id(libs.plugins.usmonie.multiplatform.domain.get().pluginId)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    kotlin("plugin.serialization") version "1.9.0"
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

        implementation(libs.room.runtime)
        implementation(libs.sqlite.bundled)

        implementation(libs.realm.base)
        implementation(libs.realm.sync)

        api(projects.shared.core.domain)
        api(projects.shared.core.tools)

        api(projects.shared.feature.dictionary.domain)
    }

    androidDependencies {
        implementation(libs.ktor.client.okhttp)
        implementation(libs.room.runtime.android)
    }

    iOSDependencies {
        implementation(libs.ktor.client.darwin)
    }
}

dependencies {
    ksp(libs.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

task("testClasses")
