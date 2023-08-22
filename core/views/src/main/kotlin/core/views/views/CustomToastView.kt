package core.views.views

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewPropertyAnimator
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import core.views.R
import core.views.databinding.CustomViewBinding

class CustomToastView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        private const val DEFAULT_DURATION = 2000L
    }

    private var animator: ViewPropertyAnimator? = null
    private var binding: CustomViewBinding? = null
    private var countDownTimer: CountDownTimer? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context, attrs, defStyleAttr, 0
    )

    init {
        binding = CustomViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun showToast(title: String, icon: IconChooser) {
        initData(title, icon)
        animator = animate().translationY(0f).setDuration(300)
        animator?.start()
        countDownTimer = object : CountDownTimer(DEFAULT_DURATION, DEFAULT_DURATION) {
            override fun onTick(millisUntilFinished: Long) = Unit

            override fun onFinish() {
                animator?.cancel()
                animator = animate().translationY(-350f).setDuration(300)
                animator?.start()
            }
        }
        countDownTimer?.start()
    }

    override fun onDetachedFromWindow() {
        animator?.cancel()
        countDownTimer?.cancel()
        super.onDetachedFromWindow()
    }

    private fun initData(title: String, icon: IconChooser) {
        when (icon) {
            IconChooser.ERROR -> {
                binding?.iconGroup?.setBackgroundResource(R.drawable.ic_error)
                binding?.tvCustomToast?.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.falu_red
                    )
                )
                binding?.customView?.background?.setColor(R.color.we_peep)
            }
            IconChooser.WARNING -> {
                binding?.iconGroup?.setBackgroundResource(R.drawable.ic_exclemationmark)
                binding?.tvCustomToast?.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.saddle_brown
                    )
                )
                binding?.customView?.background?.setColor(R.color.papaya_whip)
            }
            IconChooser.INFORMATIONAL -> {
                binding?.iconGroup?.setBackgroundResource(R.drawable.ic_informational)
                binding?.customView?.background?.setColor(R.color.alice_blue)
                binding?.tvCustomToast?.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.denim
                    )
                )
            }
            IconChooser.SUCCESS -> {
                binding?.iconGroup?.setBackgroundResource(R.drawable.ic_success)
                binding?.customView?.background?.setColor(R.color.magic_mint)
            }
            IconChooser.NoInternet -> {
                binding?.iconGroup?.setBackgroundResource(R.drawable.ic_dc)
                binding?.customView?.background?.setColor(R.color.back_noInternet)
            }
        }
        binding?.tvCustomToast?.text = title
    }

    private fun Drawable.setColor(colorInt: Int) {
        when (val mutate = mutate()) {
            is GradientDrawable -> mutate.setColor(ContextCompat.getColor(context, colorInt))
            is ShapeDrawable -> mutate.paint.color = ContextCompat.getColor(context, colorInt)
            is ColorDrawable -> mutate.color = ContextCompat.getColor(context, colorInt)
        }
    }
}
