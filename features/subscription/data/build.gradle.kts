import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.domain.get().pluginId)
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

    sourceSets {
        val commonMain by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }

    commonDependencies {
        implementation(libs.kvault)
        implementation(projects.core.domain)
        implementation(projects.features.subscription.domain)
    }

    androidDependencies {
        implementation(libs.android.billing.ktx)
    }
}
