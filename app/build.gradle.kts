import dependencies.AndroidTestDependencies
import dependencies.TestDependencies
import extensions.implementation

plugins {
    id(Commons.ANDROID_APPLICATION)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:31.1.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    // implementation("com.facebook.android:facebook-android-sdk:latest.release")
    // implementation("com.appsflyer:af-android-sdk:6.9.4")

    implementation("androidx.preference:preference-ktx:1.2.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")

    testImplementation(TestDependencies.JUNIT)
    androidTestImplementation(AndroidTestDependencies.EXT_JUNIT)
    androidTestImplementation(AndroidTestDependencies.ESPRESSO)
}
android {
    bundle {
        language {
            // Specifies that the app bundle should not support
            // configuration APKs for language resources. These
            // resources are instead packaged with each base and
            // dynamic feature APK.
            enableSplit = false
        }
    }
    defaultConfig {
        // ... (applicationId, miSdkVersion, etc)
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }
}