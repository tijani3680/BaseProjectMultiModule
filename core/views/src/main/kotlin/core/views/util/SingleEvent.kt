package core.views.util

import java.util.concurrent.atomic.AtomicBoolean

class SingleEvent<out T>(private val value: T) {

    var isUntouched = AtomicBoolean(true)
        private set

    fun getUntouchedValue(): T? = if (isUntouched.getAndSet(false)) value else null
}
