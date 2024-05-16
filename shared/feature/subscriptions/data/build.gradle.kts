import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    id(libs.plugins.usmonie.multiplatform.domain.get().pluginId)
    kotlin("plugin.serialization") version "1.9.0"
    id(libs.plugins.realm.get().pluginId)
}

android.namespace = "com.usmonie.word.features.subscription.data"

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )

    commonDependencies {
        implementation(libs.kvault)
        implementation(projects.shared.core.domain)
        implementation(projects.shared.feature.subscriptions.domain)
    }

    androidDependencies {
        implementation(libs.android.billing.ktx)
    }
}

task("testClasses")
