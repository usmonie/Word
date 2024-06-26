@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
	alias(libs.plugins.androidTest)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.androidx.baselineprofile)
}

android {
	namespace = "com.usmonie.baselineprofile"
	compileSdk = 34

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_21
		targetCompatibility = JavaVersion.VERSION_21
	}

	kotlinOptions {
		jvmTarget = "21"
	}

	defaultConfig {
		minSdk = 28
		targetSdk = 34

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	targetProjectPath = ":composeApp"
	buildTypes {
		getByName("debug") {
			signingConfig = signingConfigs.getByName("debug")
		}
	}
}

// This is the configuration block for the Baseline Profile plugin.
// You can specify to run the generators on a managed devices or connected devices.
baselineProfile {
	useConnectedDevices = true
}

dependencies {
	implementation(libs.androidx.test.ext.junit)
	implementation(libs.espresso.core)
	implementation(libs.androidx.uiautomator)
	implementation(libs.androidx.benchmark.macro.junit4)
}