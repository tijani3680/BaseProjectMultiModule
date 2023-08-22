package core.views.util.instancestate

import android.os.Bundle

interface InstanceStateHandler {

    val key: String
        get() = this::class.simpleName ?: "_state"

    private val savable: Bundle
        get() = Bundle()

    fun getBundle() = savable
    fun saveBundle(outState: Bundle) = outState.putBundle(key, savable)
    fun restoreBundle(savedInstanceState: Bundle?) = savedInstanceState?.let {
        it.getBundle(key)?.let { stateBundle -> savable.putAll(stateBundle) }
    }
}
