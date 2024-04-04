import org.gradle.api.internal.FeaturePreviews


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

include(":benchmark")
include(":baselineprofile")

include(":shared:core:domain")
include(":shared:core:ui")
include(":shared:core:design")

include(":compass:core")

include(":shared:features:dashboard:ui")
include(":shared:features:dashboard:domain")
include(":shared:features:dashboard:data")

include(":shared:features:admob:ui")
include(":shared:features:admob:domain")
include(":shared:features:admob:data")

include(":shared:features:onboarding:ui")
include(":shared:features:onboarding:domain")
include(":shared:features:onboarding:data")

include(":shared:features:subscription:domain")
include(":shared:features:subscription:data")

include(":shared:features:word:ui")