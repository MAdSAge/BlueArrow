// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
}//

// Root build.gradle.kts file

buildscript {
    val objectboxVersion by extra("4.0.3")  // Define the ObjectBox version

    repositories {
        mavenCentral()  // Add Maven Central repository to fetch dependencies
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.1.0")  // Android Gradle Plugin
        classpath("io.objectbox:objectbox-gradle-plugin:$objectboxVersion")  // ObjectBox Gradle Plugin
    }
}

// Add other configurations here (if needed)
