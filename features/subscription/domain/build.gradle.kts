import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.domain.get().pluginId)
    kotlin("plugin.serialization") version "1.9.0"
}

android.namespace = "com.usmonie.word.subscription.domain"

kotlin {
    applyDefaultHierarchyTemplate()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )

    commonDependencies {
        api(projects.core.domain)
    }
}
task("testClasses")
