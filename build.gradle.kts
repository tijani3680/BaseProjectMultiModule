import extensions.applyDefault
plugins {
    id(Plugins.SPOTLESS)
    // id(Plugins.SCABBARD)
}

allprojects {
    repositories.applyDefault()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
}
