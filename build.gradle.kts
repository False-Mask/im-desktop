import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
                implementation("com.squareup.okhttp3:okhttp:4.10.0")

                // https://mvnrepository.com/artifact/com.squareup.retrofit2/retrofit
                implementation("com.squareup.retrofit2:retrofit:2.7.2")

                // https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-gson
                implementation("com.squareup.retrofit2:converter-gson:2.9.0")


            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "im"
            packageVersion = "1.0.0"
        }
    }
}
