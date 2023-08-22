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
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = AndroidConfigs.COMPILE_SDK

    defaultConfig {
        minSdk = AndroidConfigs.MIN_SDK
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = AndroidConfigs.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles("proguard_rules.pro") // "consumer-rules.pro"
    }

    buildTypes {
        release {
            isMinifyEnabled = ReleaseBuild.isMinifyEnabled
            consumerProguardFiles("proguard-project.txt")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                rootProject.file("buildSrc/retrofit-proguard-rules.pro"),
                rootProject.file("buildSrc/gson-proguard-rules.pro"),
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
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.Core.UTILS))
    implementation(project(Modules.Core.VIEWS))

    implementation(Dependencies.DAGGER)
    implementation(Dependencies.DAGGER_ANDROID)
    kapt(AnnotationProcessorsDependencies.DAGGER_COMPILER)
    kapt(AnnotationProcessorsDependencies.DAGGER_ANDROID_PROCESSOR)

    implementation(Dependencies.KOTLINX_COROUTINES_CORE)

    implementation(Dependencies.DATASTORE_PREFERENCES)

    implementation(Dependencies.OKHTTP)
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.CONVERTER_GSON)

    implementation(Dependencies.GSON)

    implementation(Dependencies.ROOM_RUNTIME)
    implementation(Dependencies.ROOM_KTX)
    kapt(AnnotationProcessorsDependencies.ROOM_ANNOTATION_PROCESSOR)

    debugImplementation(DebugDependencies.CHUCKER)
    // debugImplementation(DebugDependencies.PLUTO)
    debugImplementation(DebugDependencies.PLUTO_PLUGIN)

    releaseImplementation(ReleaseDependencies.CHUCKER)
    // releaseImplementation(ReleaseDependencies.PLUTO)
    releaseImplementation(ReleaseDependencies.PLUTO_PLUGIN)
}
