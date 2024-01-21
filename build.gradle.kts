apply(from = libs.plugins.speech.githooks.get().pluginId)

plugins {
    with(libs.plugins) {
        alias(libs.plugins.compose)
        alias(kotlin.multiplatform) apply false
        alias(kotlin.jvm) apply false
        alias(kotlin.android) apply false
        alias(androidTest) apply false
        alias(android.library) apply false
        alias(androidx.baselineprofile) apply false
        alias(android.application) apply false
        alias(detekt) apply false
        alias(realm) apply false
        id("com.google.firebase.crashlytics") version "2.9.9" apply false
//        id("dev.icerock.mobile.multiplatform-resources") apply false
    }
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            if (project.findProperty("composeCompilerReports") == "true") {
                freeCompilerArgs += listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.buildDir.absolutePath}/compose_compiler"
                )
            }
            if (project.findProperty("composeCompilerMetrics") == "true") {
                freeCompilerArgs += listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${project.buildDir.absolutePath}/compose_compiler"
                )
            }
        }
    }
}