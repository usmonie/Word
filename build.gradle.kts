apply(from = libs.plugins.speech.githooks.get().pluginId)

plugins {
    with(libs.plugins) {
        alias(libs.plugins.compose)
        alias(kotlin.multiplatform) apply false
        alias(kotlin.jvm) apply false
        alias(kotlin.android) apply false
        alias(androidTest) apply false
        alias(android.library) apply false
        alias(androidx.baselineprofile) apply false
        alias(android.application) apply false
        alias(detekt) apply false
        alias(realm) apply false
        id("com.google.firebase.crashlytics") version "2.9.9" apply false
    }
}
