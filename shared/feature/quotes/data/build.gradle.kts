import extensions.androidDependencies
import extensions.commonDependencies
import extensions.iOSDependencies

plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    id(libs.plugins.usmonie.multiplatform.domain.get().pluginId)
    kotlin("plugin.serialization") version "1.7.0"
}

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
        implementation(libs.sqlite)

        implementation(libs.realm.base)
        implementation(libs.realm.sync)

        api(projects.shared.core.domain)
        api(projects.shared.core.tools)

        api(projects.shared.feature.quotes.domain)

        implementation("com.squareup.okio:okio:3.9.0")
    }

    androidDependencies {
        implementation(libs.ktor.client.okhttp)
        implementation(libs.room.runtime.android)
    }

    iOSDependencies {
        implementation(libs.ktor.client.darwin)
    }
}

android {
    namespace = "com.usmonie.word.features.quotes.data"
    sourceSets["main"].apply {
        assets.srcDirs("src/commonMain/resources")
    }
}

dependencies {
    kspCommonMainMetadata(libs.room.compiler)
    kspAndroid(libs.room.compiler)
    kspIosX64(libs.room.compiler)
    kspIosArm64(libs.room.compiler)
    kspIosSimulatorArm64(libs.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
    generateKotlin = true
}

task("testClasses")
