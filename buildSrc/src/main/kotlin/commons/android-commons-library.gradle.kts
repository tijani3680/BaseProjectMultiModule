package commons

import AndroidConfigs
import DebugBuild
import Modules
import ReleaseBuild
import dependencies.Dependencies
import extensions.implementation

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
}

android {
    compileSdk = AndroidConfigs.COMPILE_SDK

    defaultConfig {
        minSdk = AndroidConfigs.MIN_SDK
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = AndroidConfigs.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles("proguard_rules.pro") // "consumer-rules.pro"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = ReleaseBuild.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = DebugBuild.isMinifyEnabled
        }
    }

    // flavorDimensions += listOf("environment")
    // productFlavors {
    //     create("dev") {
    //         dimension = "environment"
    //     }
    //     create("qa") {
    //         dimension = "environment"
    //     }
    //     create("prod") {
    //         dimension = "environment"
    //     }
    // }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    sourceSets {
        getByName("main") {
            java.srcDir("src/main/kotlin")
        }
        getByName("test") {
            java.srcDir("src/test/kotlin")
        }
        getByName("androidTest") {
            java.srcDir("src/androidTest/kotlin")
        }
    }

    lint {
        lintConfig = File(projectDir, "lint.xml")
        checkAllWarnings = true
        warningsAsErrors = true
    }
}

dependencies {
    implementation("io.coil-kt:coil:2.1.0")
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.DATA))
    implementation(project(Modules.Core.UTILS))
    implementation(project(Modules.Core.VIEWS))

    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.APPCOMPAT)

    implementation(Dependencies.DAGGER_ANDROID)
    implementation(Dependencies.DAGGER_ANDROID_SUPPORT)

    implementation(Dependencies.KOTLINX_COROUTINES_ANDROID)
    implementation(Dependencies.EVENT_BUS)

    implementation(Dependencies.NAVIGATION_FRAGMENT_KTX)
    implementation(Dependencies.NAVIGATION_UI_KTX)
}
