
import extensions.commonDependencies

plugins {
    alias(libs.plugins.compose.jetbrains)
    id(libs.plugins.usmonie.multiplatform.ui.get().pluginId)
    id("dev.shreyaspatil.compose-compiler-report-generator") version "1.1.0"
    alias(libs.plugins.compose.compiler)
}

android.namespace = "com.usmonie.word.features.quotes.ui"

kotlin {
    applyDefaultHierarchyTemplate()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.components.resources)
            }
        }
    }
}

commonDependencies {
    implementation(projects.compass.core)
    implementation(projects.compass.viewmodel)
    implementation(projects.shared.core.analytics)
    implementation(projects.shared.core.kit)
    implementation(projects.shared.core.tools)

    implementation(projects.shared.feature.ads.ui)

    implementation(projects.shared.feature.subscriptions.domain)
    implementation(projects.shared.feature.subscriptions.ui)

    implementation(projects.shared.feature.quotes.domain)
    implementation(projects.shared.feature.quotes.kit)

    implementation(libs.compose.material3.jetbrains)
    implementation(compose.dependencies.materialIconsExtended)
}

kotlin {
    applyDefaultHierarchyTemplate()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )
}

composeCompiler {
    enableIntrinsicRemember = true
    enableStrongSkippingMode = true
}

task("testClasses")
