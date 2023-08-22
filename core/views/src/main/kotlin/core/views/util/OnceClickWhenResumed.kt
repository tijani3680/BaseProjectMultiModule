package core.views.util

import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class OnceClickWhenResumed(
    owner: LifecycleOwner,
    private val onClickListener: ((view: View?) -> Unit)? = null
) : View.OnClickListener, DefaultLifecycleObserver {

    private var isEnabled = false

    init {
        owner.lifecycle.addObserver(this)
    }

    override fun onClick(v: View?) {
        onClickListener?.let {
            if (isEnabled) {
                isEnabled = false
                it(v)
            }
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        isEnabled = true
    }
}
