package core.utils

internal interface Usecase<T, Params> {

    fun execute(params: Params): T
}
