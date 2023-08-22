package core.views.base

interface CoordinatorFactory {
    fun <T : Coordinator> create(clazz: Class<T>): T?
}
