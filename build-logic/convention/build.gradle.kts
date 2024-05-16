import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
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
            id = "com.usmonie.multiplatform.core"
            implementationClass = "MultiPlatformCoreConventionPlugin"
        }
        register("multiplatform ui module") {
            id = "com.usmonie.multiplatform.ui"
            implementationClass = "MultiPlatformUiConventionPlugin"
        }
        register("multiplatform domain module") {
            id = "com.usmonie.multiplatform.domain"
            implementationClass = "MultiPlatformDomainConventionPlugin"
        }
    }
}
