import extensions.commonDependencies

plugins {
    id(libs.plugins.usmonie.multiplatform.domain.get().pluginId)
    kotlin("plugin.serialization") version "1.9.0"
}

android.namespace = "com.usmonie.word.feature.subscription.domain"

kotlin {
    applyDefaultHierarchyTemplate()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )

    commonDependencies {
        api(projects.shared.core.domain)
    }
}
task("testClasses")
