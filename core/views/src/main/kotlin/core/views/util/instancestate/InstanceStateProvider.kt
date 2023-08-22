package core.views.util.instancestate

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class InstanceStateProvider<T>(
    private val clazz: Class<T>,
    private val defaultValue: T
) : ReadWriteProperty<InstanceStateHandler, T> {

    private var cache: T? = null

    override operator fun getValue(thisRef: InstanceStateHandler, property: KProperty<*>): T {
        return cache ?: clazz.cast(thisRef.getBundle().getString(property.name))?.apply {
            cache = this
        } ?: defaultValue
    }

    override fun setValue(thisRef: InstanceStateHandler, property: KProperty<*>, value: T) {
        cache = value
        if (value == null) {
            thisRef.getBundle().remove(property.name)
            return
        }
        when (value) {
            is Int -> thisRef.getBundle().putInt(property.name, value)
            is Long -> thisRef.getBundle().putLong(property.name, value)
            is Float -> thisRef.getBundle().putFloat(property.name, value)
            is String -> thisRef.getBundle().putString(property.name, value)
            is Bundle -> thisRef.getBundle().putBundle(property.name, value)
            is Serializable -> thisRef.getBundle().putSerializable(property.name, value)
            is Parcelable -> thisRef.getBundle().putParcelable(property.name, value)
        }
    }
}
