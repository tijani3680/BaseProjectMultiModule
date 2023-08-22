package commons

import dependencies.Dependencies

plugins {
    id("java-library")
    id("kotlin")
    id("com.android.lint")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

sourceSets {
    getByName("main") {
        java.srcDir("src/main/kotlin")
    }
    getByName("test") {
        java.srcDir("src/test/kotlin")
    }
}

lint {
    lintConfig = File(projectDir, "lint.xml")
    checkAllWarnings = true
    warningsAsErrors = true
}

dependencies {
    implementation(Dependencies.KOTLINX_COROUTINES_CORE)
}
