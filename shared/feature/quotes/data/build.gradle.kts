import extensions.androidDependencies
import extensions.commonDependencies
import extensions.iOSDependencies

plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    id(libs.plugins.usmonie.multiplatform.domain.get().pluginId)
    kotlin("plugin.serialization") version "2.0.0"
}

android.namespace = "com.usmonie.word.features.quotes.data"

kotlin {
    applyDefaultHierarchyTemplate()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
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

        api(projects.shared.feature.quotes.domain)
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
    kspAndroid(libs.room.compiler)
    kspIosX64(libs.room.compiler)
    kspIosArm64(libs.room.compiler)
    kspIosSimulatorArm64(libs.room.compiler)
    kspCommonMainMetadata(libs.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
    generateKotlin = true
}

task("testClasses")
