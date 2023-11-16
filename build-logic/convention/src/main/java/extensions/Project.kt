package extensions

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * Retrieves the 'libs' version catalog for the project.
 *
 * @return The 'libs' version catalog.
 */
internal val Project.libs
    get(): VersionCatalog = extensions
        .getByType<VersionCatalogsExtension>()
        .named("libs")

/**
 * Retrieves the 'config' version catalog for the project.
 *
 * @return The 'config' version catalog.
 */
internal val Project.config
    get(): VersionCatalog = extensions
        .getByType<VersionCatalogsExtension>()
        .named("config")
