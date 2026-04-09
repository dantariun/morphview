plugins {
    `kotlin-dsl`
}

group = "com.dantariun.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.kotlin.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.dantariun.buildlogic.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "com.dantariun.buildlogic.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "com.dantariun.buildlogic.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
    }
}