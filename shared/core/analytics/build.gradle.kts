import extensions.commonDependencies

plugins {
	id(libs.plugins.usmonie.multiplatform.domain.get().pluginId)
}

android.namespace = "com.usmonie.word.core.analytics.ui"

kotlin {
	applyDefaultHierarchyTemplate()

	sourceSets {
		getByName("commonTest") {
			dependencies {
				implementation(kotlin("test"))
			}
		}
		getByName("androidUnitTest") {
			dependencies {
				implementation(libs.junit)
			}
		}
	}
	listOf(
		iosX64(),
		iosArm64(),
		iosSimulatorArm64()
	)
}

commonDependencies {
	implementation(projects.shared.core.tools)
}

android {
	compileSdk = libs.versions.android.compileSdk.get().toInt()
	namespace = "com.usmonie.word.features.analytics.ui"

	sourceSets["main"].resources.srcDirs("src/commonMain/resources")

	defaultConfig {
		minSdk = libs.versions.android.minSdk.get().toInt()
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_21
		targetCompatibility = JavaVersion.VERSION_21
	}
}

task("testClasses")
