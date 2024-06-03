import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    id(libs.plugins.usmonie.multiplatform.domain.get().pluginId)
    kotlin("plugin.serialization") version "2.0.0"
}

android.namespace = "com.usmonie.word.features.subscription.data"

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )

    commonDependencies {
        implementation(libs.datastore)
        implementation(libs.datastore.core)
        implementation(libs.datastore.preferences)

        implementation(projects.shared.core.domain)
        implementation(projects.shared.feature.subscriptions.domain)
    }

    androidDependencies {
        implementation(libs.android.billing.ktx)
    }
}

task("testClasses")
