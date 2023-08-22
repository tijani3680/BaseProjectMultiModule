package core.views.base

import android.os.Bundle
import javax.inject.Inject

abstract class BaseRootActivity
<VH : ViewHandler<*>, S : State, E : Effect<S>,
    VM : BaseViewModel<S, *, *, E, *>, N : Navigator, R : Root<N>>(
    clazz: Class<VM>
) : BaseVMActivity<VH, S, E, VM>(clazz) {

    @Inject
    lateinit var root: R
    protected lateinit var navigator: N

    override fun onConfigureWindow(savedInstanceState: Bundle?) {
        super.onConfigureWindow(savedInstanceState)
        navigator = createNavigator()
        root.baseNavigator = navigator
    }

    override fun onStart() {
        super.onStart()
        root.baseNavigator = navigator
    }

    override fun onResume() {
        super.onResume()
        root.baseNavigator = navigator
    }

    override fun onStop() {
        super.onStop()
        root.baseNavigator = null
    }
    protected abstract fun createNavigator(): N
}
