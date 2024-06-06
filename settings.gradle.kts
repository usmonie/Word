import org.gradle.api.internal.FeaturePreviews

include(":benchmark")


rootProject.name = "Word"

enableFeaturePreview(FeaturePreviews.Feature.TYPESAFE_PROJECT_ACCESSORS.name)
enableFeaturePreview(FeaturePreviews.Feature.STABLE_CONFIGURATION_CACHE.name)

pluginManagement {
    includeBuild("build-logic")

    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")

        mavenCentral()
        gradlePluginPortal()

        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
    }
}

dependencyResolutionManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")

        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()

        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
    }
}

include(":composeApp")

include(":compass:core")
include(":compass:viewmodel")

include(":shared:core:analytics")
include(":shared:core:domain")
include(":shared:core:kit")
include(":shared:core:tools")

include(":shared:feature:ads:ui")

include(":shared:feature:dashboard:ui")

include(":shared:feature:details:ui")

include(":shared:feature:dictionary:data")
include(":shared:feature:dictionary:domain")
include(":shared:feature:dictionary:ui")

include(":shared:feature:games:domain")
include(":shared:feature:games:ui")

include(":shared:feature:favorites:ui")

include(":shared:feature:settings:data")
include(":shared:feature:settings:domain")
include(":shared:feature:settings:ui")

include(":shared:feature:subscriptions:data")
include(":shared:feature:subscriptions:domain")
include(":shared:feature:subscriptions:ui")

include(":shared:feature:quotes:data")
include(":shared:feature:quotes:domain")
include(":shared:feature:quotes:kit")
include(":shared:feature:quotes:ui")
