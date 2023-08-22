package core.views.util.instancestate

inline fun <reified T> instanceState(defaultValue: T) =
    InstanceStateProvider(T::class.java, defaultValue)

inline fun <reified T> nullableInstanceState(defaultValue: T? = null) =
    NullableInstanceStateProvider(T::class.java, defaultValue)
