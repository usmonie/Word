import org.gradle.api.internal.FeaturePreviews

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

include(":features:detail:ui")
include(":features:detail:domain")
include(":features:detail:data")

include(":features:favourites:ui")
include(":features:favourites:domain")
include(":features:favourites:data")

include(":features:word:ui")