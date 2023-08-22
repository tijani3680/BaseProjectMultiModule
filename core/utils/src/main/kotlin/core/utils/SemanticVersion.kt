package core.utils

import kotlin.math.min

data class SemanticVersion(
    val major: Int = 0,
    val minor: Int = 1,
    val patch: Int = 0,
    val preRelease: String? = null,
    val buildMetadata: String? = null
) : Comparable<SemanticVersion> {
    companion object {
        fun parse(str: String?): SemanticVersion? {
            str ?: return null
            val match =
                """^(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)(?:-((?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\.(?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\+([0-9a-zA-Z-]+(?:\.[0-9a-zA-Z-]+)*))?$"""
                    .toRegex().find(str)
            match ?: return null
            val (major, minor, patch, preRelease, buildMetadata) = match.destructured

            return SemanticVersion(
                major = major.toInt(),
                minor = minor.toInt(),
                patch = patch.toInt(),
                preRelease = preRelease,
                buildMetadata = buildMetadata
            )
        }
    }

    init {
        require(major >= 0) { "Major version must be a positive number" }
        require(minor >= 0) { "Minor version must be a positive number" }
        require(patch >= 0) { "Patch version must be a positive number" }
        if (!preRelease.isNullOrBlank()) require(
            preRelease.matches(
                Regex(
                    """(?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\.(?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*))*"""
                )
            )
        ) { "Pre-release version is not valid" }
        if (!buildMetadata.isNullOrBlank()) require(
            buildMetadata.matches(
                Regex("""[0-9a-zA-Z-]+(?:\.[0-9a-zA-Z-]+)*""")
            )
        ) { "Build metadata is not valid" }
    }

    override fun toString(): String = buildString {
        append("$major.$minor.$patch")
        if (!preRelease.isNullOrBlank()) {
            append('-')
            append(preRelease)
        }
        if (!buildMetadata.isNullOrBlank()) {
            append('+')
            append(buildMetadata)
        }
    }

    fun isInitialDevelopmentPhase(): Boolean = major == 0

    override fun compareTo(other: SemanticVersion): Int {
        if (major > other.major) return 1
        if (major < other.major) return -1
        if (minor > other.minor) return 1
        if (minor < other.minor) return -1
        if (patch > other.patch) return 1
        if (patch < other.patch) return -1

        if (preRelease == null && other.preRelease == null) return 0
        if (preRelease != null && other.preRelease == null) return -1
        if (preRelease == null && other.preRelease != null) return 1

        val parts = preRelease.orEmpty().split(".")
        val otherParts = other.preRelease.orEmpty().split(".")

        val endIndex = min(parts.size, otherParts.size) - 1
        for (i in 0..endIndex) {
            val part = parts[i]
            val otherPart = otherParts[i]
            if (part == otherPart) continue

            val partIsNumeric = part.matches(Regex("""\d+"""))
            val otherPartIsNumeric = otherPart.matches(Regex("""\d+"""))

            when {
                partIsNumeric && !otherPartIsNumeric -> return -1
                !partIsNumeric && otherPartIsNumeric -> return 1
                !partIsNumeric && !otherPartIsNumeric -> {
                    if (part > otherPart) return 1
                    if (part < otherPart) return -1
                }
                else -> {
                    val partInt = part.toInt()
                    val otherPartInt = otherPart.toInt()
                    if (partInt > otherPartInt) return 1
                    if (partInt < otherPartInt) return -1
                }
            }
        }

        return if (parts.size < otherParts.size) -1 else if (parts.size > otherParts.size) 1 else 0
    }

    fun getRawVersion(): String = buildString { append("$major.$minor.$patch") }
}
