package com.currenzy.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * Extension property to access the version catalog used in the project.
 *
 * Retrieves the `VersionCatalog` instance named "libs" from the `VersionCatalogsExtension`,
 * which is commonly used to manage and access library versions and dependencies in a centralized manner.
 *
 * @return The `VersionCatalog` instance for managing library versions and dependencies.
 */
val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")