import dependencies.TestDependencies

plugins {
    id(Commons.JAVA_KOTLIN_LIBRARY)
}

dependencies {

    testImplementation(TestDependencies.JUNIT)
}
