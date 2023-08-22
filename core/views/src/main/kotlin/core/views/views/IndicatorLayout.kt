package core.views.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.forEachIndexed
import core.views.R

class IndicatorLayout(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    // region Constructors
    constructor(context: Context) :
        this(context, null, 0, 0)

    constructor(context: Context, attrs: AttributeSet?) :
        this(context, attrs, 0, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        this(context, attrs, defStyleAttr, 0)
    // endregion) {

    private val num: Int

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.IndicatorLayout)
        num = attributes.getInt(R.styleable.IndicatorLayout_num_indicator, 0)
        attributes.recycle()
        val params: LayoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(8, 0, 8, 0) }
        repeat(num) {
            addView(
                ImageView(context).apply {
                    layoutParams = params
                    setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.indicator_inactive)
                    )
                }
            )
        }
    }

    fun setSelectedIndicatorPosition(position: Int) {
        forEachIndexed { i, view ->
            (view as? ImageView)?.apply {
                val drawableId =
                    if (i == position) R.drawable.indicator_active
                    else R.drawable.indicator_inactive
                setImageDrawable(ContextCompat.getDrawable(context, drawableId))
            }
        }
    }
}
