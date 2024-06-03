import extensions.androidDependencies
import extensions.commonDependencies
import extensions.iOSDependencies

plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    id(libs.plugins.usmonie.multiplatform.domain.get().pluginId)
    kotlin("plugin.serialization") version "2.0.0"
}

android.namespace = "com.usmonie.word.features.dictionary.data"

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
    kspAndroid(libs.room.compiler)
}

//dependencies {
//    add("kspCommonMainMetadata", libs.room.compiler) // Run KSP on [commonMain] code
//    add("kspAndroid", libs.room.compiler)
//    add("kspIosSimulatorArm64", libs.room.compiler)
//    add("kspIosX64", libs.room.compiler)
//    add("kspIosArm64", libs.room.compiler)
//}

room {
    schemaDirectory("$projectDir/schemas")
    generateKotlin = true
}

task("testClasses")
