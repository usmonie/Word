
import extensions.commonDependencies

plugins {
    id(libs.plugins.usmonie.multiplatform.domain.get().pluginId)
    kotlin("plugin.serialization") version "2.0.0"
}

android.namespace = "com.usmonie.word.features.settings.data"

kotlin {
    applyDefaultHierarchyTemplate()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )

    commonDependencies {
        implementation(libs.kotlinx.datetime)

        api(libs.datastore)
        api(libs.datastore.core)
        api(libs.datastore.preferences)

        api(projects.shared.core.domain)
        api(projects.shared.core.tools)

        implementation(projects.shared.feature.settings.domain)
    }
}
task("testClasses")
