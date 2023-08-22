package core.views.base

interface Action<S : State> {
    fun isRegistered(state: S): Boolean = true
}
