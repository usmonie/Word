import extensions.commonDependencies
import org.jetbrains.kotlin.com.intellij.icons.AllIcons.Nodes.Word
import org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink
import org.jetbrains.kotlin.konan.target.CompilerOutputKind

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
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "design"
            isStatic = true
            version = "1.0.0"
        }
    }

    commonDependencies {
        api(compose.runtime)
        api(compose.foundation)
        api(compose.material3)
        api(compose.ui)

        @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
        api(compose.components.resources)
    }
}

android {
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
}
copyNativeResources("commonMain")
fun copyNativeResources(sourceSet: String) {
    if (sourceSet.isEmpty()) throw IllegalStateException("Valid sourceSet required")

    val prefix = "copy${sourceSet.capitalize()}Resources"
    tasks.withType<KotlinNativeLink> {
        val firstIndex = name.indexOfFirst { it.isUpperCase() }
        val taskName = "$prefix${name.substring(firstIndex)}"

        dependsOn(
            tasks.register<Copy>(taskName) {
                from("src/$sourceSet/resources")
                when (outputKind) {
                    CompilerOutputKind.FRAMEWORK -> into(outputFile.get())
                    CompilerOutputKind.PROGRAM -> into(destinationDirectory.get())
                    else -> throw IllegalStateException("Unhandled binary outputKind: $outputKind")
                }
            }
        )
    }

    tasks.withType<FatFrameworkTask> {
        if (destinationDir.path.contains("Temp")) return@withType

        val firstIndex = name.indexOfFirst { it.isUpperCase() }
        val taskName = "$prefix${name.substring(firstIndex)}"

        dependsOn(
            tasks.register<Copy>(taskName) {
                from("src/$sourceSet/resources")
                into(fatFramework)
            }
        )
    }
}
