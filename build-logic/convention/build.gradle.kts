plugins {
    `kotlin-dsl`
}

group = "com.dantariun.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}