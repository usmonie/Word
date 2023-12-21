
import extensions.androidDependencies
import extensions.commonDependencies

plugins {
    id(libs.plugins.speech.multiplatform.core.get().pluginId)
    alias(libs.plugins.compose)
}

android.namespace = "com.usmonie.core.design"

kotlin {
    applyDefaultHierarchyTemplate()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )

    commonDependencies {
        api(compose.runtime)
        api(compose.foundation)
        api(compose.material3)
        api(compose.ui)

        @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
        api(compose.components.resources)
    }

    androidDependencies {
        api(compose.uiTooling)
        api(compose.preview)
    }
}

android {
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
}
