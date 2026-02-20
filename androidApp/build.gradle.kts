import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    compilerOptions {
        languageVersion = KotlinVersion.KOTLIN_2_3
    }
}

android {
    namespace = "com.daniel.azar"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.daniel.azar"
        minSdk = 24
        targetSdk = 36
        versionCode = 7
        versionName = "3.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.composeApp)
    implementation(libs.activity.compose)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.ui)
    implementation(libs.core.splashscreen)
}