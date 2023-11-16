import com.android.build.api.dsl.LibraryExtension
import extensions.configureAndroid
import extensions.configureDetekt
import extensions.configureJvmToolchain
import extensions.configureKotlin
import extensions.configureKotlinJvm
import extensions.configureTarget
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Convention plugin for setting up base settings for any kmp module
 */
class MultiPlatformCoreConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("io.gitlab.arturbosch.detekt")
            }

            extensions.configure<LibraryExtension> {
                configureAndroid(extension = this)
            }

            extensions.configure<DetektExtension> {
                configureDetekt(extension = this)
            }

            configureKotlinJvm()
            configureKotlin()

            extensions.configure<KotlinMultiplatformExtension> {
                configureTarget()
                configureJvmToolchain()
            }
        }
    }
}