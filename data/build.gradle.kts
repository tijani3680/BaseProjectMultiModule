import dependencies.AndroidTestDependencies
import dependencies.TestDependencies
import extensions.implementation

plugins {
    id(Commons.ANDROID_DATA_LIBRARY)
}

dependencies {
    implementation("io.github.haiyangwu:mediasoup-client:3.4.0")
    implementation(platform("com.google.firebase:firebase-bom:31.1.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5")
    implementation("androidx.paging:paging-runtime:3.2.0")

    testImplementation(TestDependencies.JUNIT)
    androidTestImplementation(AndroidTestDependencies.EXT_JUNIT)
    androidTestImplementation(AndroidTestDependencies.ESPRESSO)
}
android {
    defaultConfig {
        // ... (applicationId, miSdkVersion, etc)
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }
}