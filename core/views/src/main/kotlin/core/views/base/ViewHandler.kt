package core.views.base

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import core.views.util.instancestate.InstanceStateHandler

open class ViewHandler<T : ViewBinding>(
    private var viewBinding: T?,
    savedInstanceState: Bundle? = null
) : InstanceStateHandler {
    init {
        this.restoreBundle(savedInstanceState)
    }

    protected val binding
        get() = viewBinding ?: throw IllegalAccessException("binding is null")

    fun getRoot(): View = binding.root

    protected open fun onDestroy() = Unit

    fun destroy() {
        onDestroy()
        viewBinding = null
    }
}
