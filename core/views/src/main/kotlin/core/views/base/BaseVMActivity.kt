package core.views.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

abstract class BaseVMActivity
<VH : ViewHandler<*>, S : State, E : Effect<S>, VM : BaseViewModel<S, *, *, E, *>>(
    private val clazz: Class<VM>
) : BaseVHActivity<VH>() {

    protected val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[clazz]
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.state.onEach { renderState(it) }.collect()
        }
        lifecycleScope.launchWhenStarted {
            viewModel.effect.onEach { renderEffect(it) }.collect()
        }
    }

    protected abstract fun renderEffect(effect: E)
    protected abstract fun renderState(state: S)
}
