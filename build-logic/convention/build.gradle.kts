import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    compileOnly(dependencyNotation = libs.detekt.gradlePlugin)
    compileOnly(dependencyNotation = libs.android.gradlePlugin)
    compileOnly(dependencyNotation = libs.kotlin.gradlePlugin)
    compileOnly(dependencyNotation = libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("multiplatform core module") {
            id = "wtf.speech.multiplatform.core"
            implementationClass = "MultiPlatformCoreConventionPlugin"
        }
        register("multiplatform ui module") {
            id = "wtf.speech.multiplatform.ui"
            implementationClass = "MultiPlatformUiConventionPlugin"
        }
        register("multiplatform domain module") {
            id = "wtf.speech.multiplatform.domain"
            implementationClass = "MultiPlatformDomainConventionPlugin"
        }
    }
}
