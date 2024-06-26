import extensions.androidDependencies
import extensions.commonDependencies

plugins {
	id(libs.plugins.usmonie.multiplatform.core.get().pluginId)
	alias(libs.plugins.kotlinMultiplatform)
	alias(libs.plugins.compose.jetbrains)
	alias(libs.plugins.compose.compiler)
	id(libs.plugins.usmonie.multiplatform.ui.get().pluginId)
}

android.namespace = "com.usmonie.core.kit"

kotlin {
	applyDefaultHierarchyTemplate()

	sourceSets {
		val commonMain by getting {
			dependencies {
				implementation(compose.components.resources)
			}
		}
	}

	commonDependencies {
		api(compose.animation)
		api(compose.runtime)
		api(compose.foundation)
		api(compose.material3)
		api(compose.ui)

		@OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
		api(compose.components.resources)
		api(compose.components.uiToolingPreview)

		api("io.github.theapache64:rebugger:1.0.0-rc03")

		implementation(libs.kotlinx.datetime)
	}

	androidDependencies {
		api(compose.uiTooling)
		api(compose.preview)
	}
}

android {
	sourceSets["main"].resources.srcDirs("src/commonMain/resources")
}

dependencies {
	detektPlugins(libs.twitter.compose.detekt)
}

compose.resources {
	publicResClass = true
}

composeCompiler {
	enableIntrinsicRemember = true
	enableStrongSkippingMode = true
}

task("testClasses")
