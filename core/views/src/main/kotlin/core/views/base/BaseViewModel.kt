package core.views.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class BaseViewModel
<S : State, A : Action<S>, M : Mutation<S>, E : Effect<S>, C : Coordinator>(
    protected open val coordinator: C? = null,
    private val logger: ((event: String, message: String?) -> Unit)? = null
) : ViewModel() {

    private val mutableAction = MutableSharedFlow<A>(
        replay = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 64
    )

    private val mutableEffect = MutableSharedFlow<E>(
        replay = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 64
    )
    val effect = mutableEffect.asSharedFlow()

    private val mutableState = mutableState()
    val state: StateFlow<S> = mutableState.asStateFlow()
    private val mutex = Mutex()

    init {
        mutableAction.asSharedFlow().onEach { a ->
            handle(a).onEach { mutation ->
                mutex.withLock {
                    mutableState.value = mutation.reduce(state.value)
                    logger?.invoke(BaseViewModel::class.simpleName!!, "New State: ${state.value}")
                }
            }.launchIn(viewModelScope)
        }.launchIn(viewModelScope)
    }

    fun emitAction(action: A) {
        if (action.isRegistered(state.value)) {
            logger?.invoke(BaseViewModel::class.simpleName!!, "Action: $action")
            mutableAction.tryEmit(action)
        }
    }

    protected fun emitEffect(effect: E) {
        if (effect.isEnabled(state.value)) {
            logger?.invoke(BaseViewModel::class.simpleName!!, "Effect: $effect")
            mutableEffect.tryEmit(effect)
        }
    }

    protected fun changeState(mutation: M) {
        mutableState.value = mutation.reduce(state.value)
        logger?.invoke(BaseViewModel::class.simpleName!!, "New State: ${state.value}")
    }

    private fun mutableState() = MutableStateFlow(initialState())
    protected abstract fun initialState(): S
    protected abstract fun handle(action: A): Flow<M>
}
