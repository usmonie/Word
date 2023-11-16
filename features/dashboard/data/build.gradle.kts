import extensions.androidDependencies
import extensions.commonDependencies
import extensions.iOSDependencies

plugins {
    id(libs.plugins.speech.multiplatform.domain.get().pluginId)
    id("app.cash.sqldelight") version "2.0.0"
}

android.namespace = "com.usmonie.word.features.dashboard.data"

kotlin {
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
        implementation(libs.kotlinx.datetime)
        implementation(libs.sqldelight.runtime)
        api(libs.kvault)
        api(projects.core.domain)
        api(projects.features.dashboard.domain)
    }

    androidDependencies {
        implementation(libs.sqldelight.driver.android)
    }

    iOSDependencies {
        implementation("co.touchlab:sqliter-driver:1.3.1")
        implementation(libs.sqldelight.driver.native)
    }
}


sqldelight {
    databases {
        create("Database") {
            packageName.set("com.usmonie.word.features.dashboard.data.db")
        }

        linkSqlite = true
    }
}
