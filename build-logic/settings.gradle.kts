dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }

//    versionCatalogs {
//        create("config") {
//            from(files("../gradle/config.version.toml"))
//        }
//    }
}

rootProject.name = "build-logic"

include(":convention")
