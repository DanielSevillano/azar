import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    android {
        compileSdk = 36
        namespace = "com.daniel.azar.android"
        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(libs.compose.resources)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.material3.adaptive.navigation.suite)
            implementation(libs.compose.material3.adaptive)
            implementation(libs.compose.material.icons)
            implementation(libs.compose.runtime)
            implementation(libs.compose.ui)
            implementation(libs.lifecycle.viewmodel.compose)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.daniel.azar"
            packageVersion = "3.0.0"
        }
    }
}