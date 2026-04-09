package com.dantariun.buildlogic

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

val Project.libs: VersionCatalog
    get() = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

object AndroidSdkVersions {
    const val COMPILE_SDK = 36
    const val MIN_SDK = 24
    const val TARGET_SDK = 36
}
