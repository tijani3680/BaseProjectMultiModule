package core.views.util

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.os.postDelayed

class DoubleClickListener(
    private val onClickListener: ((view: View?) -> Unit)? = null,
    private val onDoubleClickListener: ((view: View?) -> Unit),
) : View.OnClickListener {

    companion object {
        private const val DOUBLE_CLICK_QUALIFICATION_TIME_IN_MILLIS = 200L
    }

    private var isDoubleClick = false
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var lastClickTime = 0L

    override fun onClick(v: View?) {
        val currentClickTime = System.currentTimeMillis()
        if (currentClickTime - lastClickTime < DOUBLE_CLICK_QUALIFICATION_TIME_IN_MILLIS) {
            isDoubleClick = true
            handler.removeCallbacks(runnable)
            onDoubleClickListener(v)
            return
        }
        isDoubleClick = false
        runnable = handler.postDelayed(DOUBLE_CLICK_QUALIFICATION_TIME_IN_MILLIS) {
            if (!isDoubleClick) {
                onClickListener?.let { it(v) }
            }
        }
        lastClickTime = currentClickTime
    }
}
