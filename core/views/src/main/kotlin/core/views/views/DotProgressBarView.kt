package core.views.views

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import core.views.R
import core.views.util.toPx

class DotProgressBarView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int,
    color: Int = Color.WHITE
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    // region Constructors
    constructor(context: Context) :
        this(context, null, 0, 0)

    constructor(context: Context, attrs: AttributeSet?) :
        this(context, attrs, 0, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        this(context, attrs, defStyleAttr, 0)
    // endregion

    private val displayMetrics = context.resources.displayMetrics
    private var margin: Int = 4.toPx(displayMetrics).toInt()
    private var dotRadius: Int = 8.toPx(displayMetrics).toInt()
    private var numberOfDots = 3
    private val animators = mutableListOf<Animator>()
    private var animationDuration = 1000L
    private var minScale = 0.5f
    private var maxScale = 1f
    private var minAlpha = 0.5f
    private var maxAlpha = 1f
    private var primaryAnimator: ValueAnimator? = null
    private var dotBackground = R.drawable.ic_dot
    private var dotAnimator: ValueAnimator? = null

    init {
        repeat(numberOfDots) {
            val dot = View(context).apply {
                layoutParams = LayoutParams(dotRadius * 2, dotRadius * 2).apply {
                    setMargins(margin, margin, margin, margin)
                }
                scaleX = minScale
                scaleY = minScale
                alpha = maxAlpha
                val drawable = ContextCompat.getDrawable(context, dotBackground)?.apply {
                    setTint(color)
                }
                background = drawable
            }
            addView(dot)
            animators.add(getScaleAnimator(dot))
        }
        primaryAnimator?.cancel()
        primaryAnimator = ValueAnimator.ofInt(0, numberOfDots).apply {
            addUpdateListener {
                if (it.animatedValue != numberOfDots)
                    animators[it.animatedValue as Int].start()
            }
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            duration = animationDuration
            interpolator = LinearInterpolator()
        }
    }

    private fun getScaleAnimator(view: View): Animator {
        if (dotAnimator != null) return dotAnimator as ValueAnimator
        return ValueAnimator.ofFloat(minAlpha, maxAlpha).apply {
            addUpdateListener {
                view.alpha = it.animatedValue as Float
            }
            duration = animationDuration / numberOfDots.toLong()
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
            interpolator = LinearInterpolator()
        }
    }

    fun stopAnimation() {
        primaryAnimator?.cancel()
    }

    fun startAnimation() {
        primaryAnimator?.start()
    }

    fun isAnimationRunning(): Boolean {
        return primaryAnimator!!.isRunning
    }

    override fun setVisibility(visibility: Int) {
        if (visibility == View.VISIBLE) startAnimation()
        else stopAnimation()
        super.setVisibility(visibility)
    }
}
