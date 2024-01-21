import org.gradle.api.internal.FeaturePreviews

include(":benchmark")


include(":baselineprofile")


rootProject.name = "Word"
enableFeaturePreview(FeaturePreviews.Feature.TYPESAFE_PROJECT_ACCESSORS.name)
enableFeaturePreview(FeaturePreviews.Feature.STABLE_CONFIGURATION_CACHE.name)

pluginManagement {
    includeBuild("build-logic")

    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        id("dev.icerock.moko.resources-generator") version "0.23.0" apply false
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":composeApp")

include(":core:domain")
include(":core:ui")
include(":core:design")

include(":compass:core")

include(":features:dashboard:ui")
include(":features:dashboard:domain")
include(":features:dashboard:data")

include(":features:admob:ui")
include(":features:admob:domain")
include(":features:admob:data")

include(":features:subscription:domain")
include(":features:subscription:data")

include(":features:word:ui")