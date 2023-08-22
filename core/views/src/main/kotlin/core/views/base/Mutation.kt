package core.views.base

interface Mutation<S : State> {
    fun reduce(state: S): S = state
}
