package core.views.base

import android.os.Bundle
import androidx.navigation.NavArgs
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseRootFragment
<VH : ViewHandler<*>, Args : NavArgs, S : State, E : Effect<S>,
    VM : BaseViewModel<S, *, *, E, *>, N : Navigator, R : Root<N>>(
    clazz: Class<VM>,
    navArgsClass: KClass<Args>
) : BaseVMFragment<VH, Args, S, E, VM>(clazz, navArgsClass) {

    @Inject
    lateinit var root: R
    protected lateinit var navigator: N

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator = createNavigator()
        root.baseNavigator = navigator
    }

    override fun onResume() {
        super.onResume()
        root.baseNavigator = navigator
    }

    override fun onStop() {
        root.baseNavigator = null
        super.onStop()
    }

    protected abstract fun createNavigator(): N
}
