import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.core.get().pluginId)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
    id(libs.plugins.speech.multiplatform.ui.get().pluginId)
}

android.namespace = "com.usmonie.core.design"

kotlin {
    applyDefaultHierarchyTemplate()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.components.resources)
            }
        }
    }
    3
    commonDependencies {
        api(compose.runtime)
        api(compose.foundation)
        api(compose.material3)
        api(compose.ui)

        @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
        api(compose.components.resources)
        api(compose.components.uiToolingPreview)
    }

    androidDependencies {
        api(compose.uiTooling)
        api(compose.preview)
    }
}

android {
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
}
task("testClasses")