dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }

    versionCatalogs {
        create("config") {
            from(files("../gradle/config.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"

include(":convention")
