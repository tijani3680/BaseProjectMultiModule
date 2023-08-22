interface Build {

    companion object Type {
        const val DEBUG = "debug"
        const val RELEASE = "release"
    }

    val isMinifyEnabled: Boolean
    val isShrinkResources: Boolean
}

object DebugBuild : Build {
    override val isMinifyEnabled = false
    override val isShrinkResources = false

    // const val applicationIdSuffix = ".debug"
    const val versionNameSuffix = "-debug"
}

object ReleaseBuild : Build {
    override val isMinifyEnabled = true
    override val isShrinkResources = true
}
