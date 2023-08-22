import dependencies.TestDependencies

plugins {
    id(Commons.JAVA_KOTLIN_DOMAIN_LIBRARY)
}

dependencies {

    implementation("androidx.paging:paging-common-ktx:3.1.1")
    testImplementation(TestDependencies.JUNIT)
}
