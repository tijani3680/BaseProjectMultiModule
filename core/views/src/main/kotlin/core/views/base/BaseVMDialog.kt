package core.views.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlin.reflect.KClass

abstract class BaseVMDialog
<VH : ViewHandler<*>, Args : NavArgs, S : State, E : Effect<S>, VM : BaseViewModel<S, *, *, E, *>>(
    private val clazz: Class<VM>,
    navArgsClass: KClass<Args>
) : BaseVHDialog<VH, Args>(navArgsClass) {

    protected val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[clazz]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.state.onEach { renderState(it) }.collect()
        }
        lifecycleScope.launchWhenStarted {
            viewModel.effect.onEach { renderEffect(it) }.collect()
        }
    }

    abstract fun renderEffect(effect: E)
    abstract fun renderState(state: S)
}
