import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.domain.get().pluginId)
    kotlin("plugin.serialization") version "1.9.0"
}

android.namespace = "com.usmonie.word.dashboard.domain"

kotlin {
    applyDefaultHierarchyTemplate()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            version = "1.0.0"
        }
    }
    commonDependencies {
        implementation(libs.kotlinx.datetime)
        implementation(libs.kotlinx.serialization)
        api(projects.core.domain)
    }

    sourceSets {
        getByName("commonTest") {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        getByName("androidUnitTest") {
            dependencies {
                implementation(libs.junit)
            }
        }
    }
}

task("testClasses")