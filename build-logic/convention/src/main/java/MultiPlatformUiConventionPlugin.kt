import com.android.build.api.dsl.LibraryExtension
import extensions.commonDependencies
import extensions.configureAndroid
import extensions.configureCompose
import extensions.configureDetekt
import extensions.configureJvmToolchain
import extensions.configureKotlin
import extensions.configureKotlinJvm
import extensions.configureTarget
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Convention plugin for setting up base settings for ui feature module
 */
class MultiPlatformUiConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("io.gitlab.arturbosch.detekt")
            }

            val libraryExtension = extensions.getByType<LibraryExtension>()
            val kmpExtension = extensions.getByType<KotlinMultiplatformExtension>()
            val composeExtension = extensions.getByType<ComposeExtension>()

            configureAndroid(extension = libraryExtension)
            configureCompose(extension = libraryExtension)

            configureKotlinJvm()
            configureKotlin()

            extensions.configure<DetektExtension> {
                configureDetekt(extension = this)
            }

            with(kmpExtension) {
                configureTarget()
                configureJvmToolchain()
            }

            commonDependencies {
                implementation(composeExtension.dependencies.ui)
                implementation(composeExtension.dependencies.foundation)
                implementation(composeExtension.dependencies.runtime)
            }
        }
    }
}