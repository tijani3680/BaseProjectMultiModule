package core.views.base

interface Effect<S : State> {
    fun isEnabled(state: S): Boolean = true
}
