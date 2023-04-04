plugins {
    kotlin("multiplatform")
    id("com.android.library")
    //id("kotlinx-atomicfu")
    id("org.jetbrains.dokka") version "1.7.20"
    kotlin("plugin.serialization") version "1.8.10"
    id("maven-publish")
    kotlin("native.cocoapods")
}

group = "io.github.landrynorris"
version = "0.0.21"

kotlin {
    cocoapods {
        //baseName = "ffmpeg-kit"
        version = "0.0.0"

        ios.deploymentTarget = "12.1"

        pod("ffmpeg-kit-ios-full-gpl", moduleName = "ffmpegkit", version = "~> 5.1")

        framework {
            isStatic = true
            freeCompilerArgs += listOf("-Xoverride-konan-properties=osVersionMin=12.1")
            embedBitcode(org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode.DISABLE)
        }
    }
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }

        publishLibraryVariants("debug", "release")
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ffmpegkit"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0-Beta")
                implementation("org.jetbrains.kotlinx:atomicfu:0.20.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.arthenica:ffmpeg-kit-full:5.1")
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.example.ffmpegkit"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        withType<MavenPublication> {
            artifact(javadocJar.get())
            pom {
                name.set("ffmpeg-kit")
                description.set("FFmpegKit for KMM")
                url.set("https://github.com/LandryNorris/JniUtils")
                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("landrynorris")
                        name.set("Landry Norris")
                        email.set("landry.norris0@gmail.com")
                    }
                }
            }
        }
    }
}
