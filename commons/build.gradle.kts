import dependencies.AndroidTestDependencies
import dependencies.Dependencies
import dependencies.TestDependencies
import extensions.implementation

plugins {
    id(Commons.ANDROID_COMMONS_LIBRARY)
}
android {
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
    }
}

dependencies {
    implementation(Dependencies.MATERIAL)
    implementation(platform("com.google.firebase:firebase-bom:31.1.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.ooimi:toastx:1.1.2")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("com.airbnb.android:lottie:3.7.0")
    implementation("androidx.paging:paging-runtime:3.2.0")
    implementation("com.karumi:dexter:6.2.3")
    testImplementation(TestDependencies.JUNIT)
    androidTestImplementation(AndroidTestDependencies.EXT_JUNIT)
    androidTestImplementation(AndroidTestDependencies.ESPRESSO)
}
