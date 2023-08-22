package plugins

import dev.arunkumar.scabbard.gradle.ScabbardGradlePlugin
import dev.arunkumar.scabbard.gradle.ScabbardPluginExtension

apply<ScabbardGradlePlugin>()

configure<ScabbardPluginExtension> {
    enabled = true
    outputFormat = "svg"
    qualifiedNames = true
}
