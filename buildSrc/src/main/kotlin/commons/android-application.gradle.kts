package commons

import AndroidConfigs
import DebugBuild
import Modules
import ReleaseBuild
import dependencies.AnnotationProcessorsDependencies
import dependencies.DebugDependencies
import dependencies.Dependencies
import dependencies.ReleaseDependencies
import extensions.implementation

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
}

android {
    compileSdk = AndroidConfigs.COMPILE_SDK

    defaultConfig {
        applicationId = AndroidConfigs.APPLICATION_ID
        minSdk = AndroidConfigs.MIN_SDK
        targetSdk = AndroidConfigs.TARGET_SDK
        versionCode = AndroidConfigs.VERSION_CODE
        versionName = AndroidConfigs.VERSION_NAME
        signingConfig = signingConfigs.getByName("debug")
        testInstrumentationRunner = AndroidConfigs.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables.useSupportLibrary = true
        ndk {
            abiFilters += listOf("x86", "armeabi-v7a", "arm64-v8a", "x86_64")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            ndk.debugSymbolLevel = "FULL"
            isMinifyEnabled = ReleaseBuild.isMinifyEnabled
            isShrinkResources = ReleaseBuild.isShrinkResources
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = DebugBuild.isMinifyEnabled
            isShrinkResources = DebugBuild.isShrinkResources
            // applicationIdSuffix = DebugBuild.applicationIdSuffix
            versionNameSuffix = DebugBuild.versionNameSuffix
        }
    }

    // flavorDimensions += listOf("environment")
    //
    // productFlavors {
    //     create("dev") {
    //         dimension = "environment"
    //         applicationIdSuffix = ".dev"
    //         versionNameSuffix = "-dev"
    //     }
    //     create("qa") {
    //         dimension = "environment"
    //         applicationIdSuffix = ".qa"
    //         versionNameSuffix = "-qa"
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

    buildFeatures {
        dataBinding = true
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
    bundle {
        language { enableSplit = false }
    }
}

// Allow references to generated code for dagger
kapt {
    correctErrorTypes = true
}

dependencies {
    // implementation(project(Modules.Features.HOME))
    // implementation(project(Modules.Features.SEARCH))
    // implementation(project(Modules.Features.CALL))
    // implementation(project(Modules.Features.FEEDBACK))
    // implementation(project(Modules.Features.MESSAGE))
    // implementation(project(Modules.Features.CHAT))
    // implementation(project(Modules.Features.FRIENDS))
    // implementation(project(Modules.Features.SETTINGS))
    // implementation(project(Modules.Features.SPLASH))
    // implementation(project(Modules.Features.CONTAINER))
    // implementation(project(Modules.Features.RINGING))
    implementation(project(Modules.COMMONS))
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.DATA))
    implementation(project(Modules.Core.UTILS))
    implementation(project(Modules.Core.VIEWS))

    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.CONSTRAIN_LAYOUT)
    implementation(Dependencies.MATERIAL)

    implementation(Dependencies.DAGGER)
    implementation(Dependencies.DAGGER_ANDROID)
    kapt(AnnotationProcessorsDependencies.DAGGER_COMPILER)
    kapt(AnnotationProcessorsDependencies.DAGGER_ANDROID_PROCESSOR)

    implementation(Dependencies.KOTLINX_COROUTINES_ANDROID)
    implementation(Dependencies.EVENT_BUS)

    implementation(Dependencies.NAVIGATION_FRAGMENT_KTX)
    implementation(Dependencies.NAVIGATION_UI_KTX)

    debugImplementation(DebugDependencies.LEAKCANARY_ANDROID)
    debugImplementation(DebugDependencies.PLUTO)
    debugImplementation(DebugDependencies.PLUTO_PLUGIN)

    releaseImplementation(ReleaseDependencies.PLUTO)
    releaseImplementation(ReleaseDependencies.PLUTO_PLUGIN)

    implementation("com.google.android.play:app-update:2.0.1")
    implementation("com.google.android.play:app-update-ktx:2.0.1")
}
