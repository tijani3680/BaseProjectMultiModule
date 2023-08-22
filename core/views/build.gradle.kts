import dependencies.AndroidTestDependencies
import dependencies.TestDependencies
import extensions.implementation

plugins {
    id(Commons.ANDROID_LIBRARY)
}

dependencies {

    implementation("io.coil-kt:coil:2.1.0")

    testImplementation(TestDependencies.JUNIT)
    androidTestImplementation(AndroidTestDependencies.EXT_JUNIT)
    androidTestImplementation(AndroidTestDependencies.ESPRESSO)
}
