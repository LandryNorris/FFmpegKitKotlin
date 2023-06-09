plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.library").version("7.4.1").apply(false)
    kotlin("multiplatform").version("1.8.10").apply(false)
}

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlinx:atomicfu-gradle-plugin:0.20.0")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
