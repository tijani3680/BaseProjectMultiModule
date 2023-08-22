package plugins

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import com.diffplug.gradle.spotless.SpotlessPlugin

apply<SpotlessPlugin>()

configure<SpotlessExtension> {
    format("misc") {
        target(
            fileTree(
                mapOf(
                    "dir" to ".",
                    "include" to listOf(
                        "**/*.gradle",
                        "**/.md",
                        "**/.gitignore",
                        "**/*.yaml",
                        "**/*.yml"
                    ),
                    "exclude" to listOf(
                        ".gradle/**",
                        ".gradle-cache/**",
                        "**/tools/**",
                        "**/build/**"
                    )
                )
            )
        )
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
        toggleOffOn("spotless:off", "spotless:on")
    }
    format("xml") {
        target("**/res/**/*.xml")
        indentWithSpaces(4)
        trimTrailingWhitespace()
        endWithNewline()
        toggleOffOn("spotless:off", "spotless:on")
    }
    kotlin {
        target(
            fileTree(
                mapOf(
                    "dir" to ".",
                    "include" to listOf("**/*.kt"),
                    "exclude" to listOf(
                        ".gradle/**",
                        "**/build/**"
                    )
                )
            )
        )
        ktlint("0.45.2")
            .setUseExperimental(false)
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
        toggleOffOn("spotless:off", "spotless:on")
    }
    kotlinGradle {
        target(
            fileTree(
                mapOf(
                    "dir" to ".",
                    "include" to listOf("**/*.gradle.kts", "*.gradle.kts"),
                    "exclude" to listOf(
                        ".gradle/**",
                        "**/build/**"
                    )
                )
            )
        )
        ktlint("0.45.2")
            .setUseExperimental(false)
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
        toggleOffOn("spotless:off", "spotless:on")
    }
    predeclareDeps()
}

configure<SpotlessExtensionPredeclare> {
    kotlin { ktlint(Versions.KTLINT) }
}
