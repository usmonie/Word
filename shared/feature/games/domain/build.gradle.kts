import extensions.commonDependencies

plugins {
    id(libs.plugins.usmonie.multiplatform.domain.get().pluginId)
    kotlin("plugin.serialization") version "1.7.0"
}

android.namespace = "com.usmonie.word.features.games.domain"

kotlin {
    applyDefaultHierarchyTemplate()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )

    commonDependencies {
        api(projects.shared.core.tools)
        api(projects.shared.core.domain)

        implementation(projects.shared.feature.quotes.domain)
    }
}

task("testClasses")
