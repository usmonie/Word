import com.android.build.api.dsl.LibraryExtension
import extensions.commonDependencies
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
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Convention plugin for setting up base settings for domain feature module
 */
class MultiPlatformDomainConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("io.gitlab.arturbosch.detekt")
            }

            val libraryExtension = extensions.getByType<LibraryExtension>()
            val kmpExtension = extensions.getByType<KotlinMultiplatformExtension>()

            configureAndroid(extension = libraryExtension)

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
                implementation(project(":core:domain"))
            }
        }
    }
}