package utils

import java.util.Locale

fun isLinuxOrMacOs(): Boolean {
    val osName = System.getProperty("os.name").lowercase(Locale.ROOT)
    return listOf("linux", "mac os", "macos").contains(osName)
}
