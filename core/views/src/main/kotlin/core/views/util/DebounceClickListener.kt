package core.views.util

import android.view.View

class DebounceClickListener(
    private val minimumIntervalInMillis: Long = 500,
    private val onClickListener: ((view: View?) -> Unit)
) : View.OnClickListener {

    private var lastClickTime = 0L

    override fun onClick(v: View?) {
        val currentClickTime = System.currentTimeMillis()
        if (currentClickTime - lastClickTime > minimumIntervalInMillis || lastClickTime == 0L) {
            lastClickTime = currentClickTime
            onClickListener(v)
        }
    }
}
