
import extensions.commonDependencies

plugins {
    alias(libs.plugins.compose.jetbrains)
    id(libs.plugins.usmonie.multiplatform.ui.get().pluginId)
    alias(libs.plugins.compose.compiler)
}

android.namespace = "com.usmonie.word.features.quotes.kit"

kotlin {
    applyDefaultHierarchyTemplate()
}

commonDependencies {
    implementation(projects.shared.core.kit)
    implementation(projects.shared.feature.quotes.domain)
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
