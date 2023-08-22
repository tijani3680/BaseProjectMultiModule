package core.views.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import core.views.R
import core.views.databinding.LayoutPalphoneButtonBinding

class PalphoneButton constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
) : CardView(context, attrs, defStyleAttr) {
    // region Constructors
    constructor(context: Context) :
        this(context, null)

    constructor(context: Context, attrs: AttributeSet?) :
        this(context, attrs, 0)
    // endregion

    private val binding: LayoutPalphoneButtonBinding
    private var enableTextColor: Int = 0
    private var disableTextColor: Int = 0
    private var enableBackgroundColor: Int = 0
    private var disableBackgroundColor: Int = 0
    private var enableText: String? = null
    private var disableText: String? = null
    private var enableIcon: Drawable? = null
    private var disableIcon: Drawable? = null
    private var progressColor: Int? = null
    private var progressView: DotProgressBarView? = null

    init {
        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.PalphoneButton,
            defStyleAttr,
            0
        )
        enableIcon = arr.getDrawable(R.styleable.PalphoneButton_button_enable_icon)
        disableIcon = arr.getDrawable(R.styleable.PalphoneButton_button_disable_icon) ?: enableIcon
        enableText = arr.getString(R.styleable.PalphoneButton_button_enable_text)
        disableText = arr.getString(R.styleable.PalphoneButton_button_disable_text) ?: enableText
        enableTextColor =
            arr.getColor(R.styleable.PalphoneButton_button_enable_text_color, Color.WHITE)
        disableTextColor =
            arr.getColor(R.styleable.PalphoneButton_button_disable_text_color, Color.WHITE)
        enableBackgroundColor =
            arr.getColor(R.styleable.PalphoneButton_button_enable_background_color, Color.WHITE)
        disableBackgroundColor =
            arr.getColor(R.styleable.PalphoneButton_button_disable_background_color, Color.WHITE)
        progressColor =
            arr.getColor(R.styleable.PalphoneButton_progress_color, Color.WHITE)
        val buttonRadius = arr.getDimension(R.styleable.PalphoneButton_button_radius, 32f)
        val buttonElevation = arr.getDimension(R.styleable.PalphoneButton_button_elevation, 0f)
        arr.recycle()

        binding = LayoutPalphoneButtonBinding.inflate(LayoutInflater.from(context), this)
        progressColor?.let {
            progressView =
                DotProgressBarView(context, attrs, defStyleAttr, defStyleAttr, it).apply {
                    gravity = Gravity.CENTER
                    layoutParams =
                        LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                            gravity = Gravity.CENTER
                        }
                    visibility = GONE
                }
            addView(progressView)
        }
        binding.content.text = enableText
        binding.content.setCompoundDrawablesRelativeWithIntrinsicBounds(
            enableIcon,
            null,
            null,
            null
        )
        binding.content.setTextColor(enableTextColor)
        radius = buttonRadius
        setCardBackgroundColor(enableBackgroundColor)
        cardElevation = buttonElevation
    }

    fun changeUiButton(enable: Boolean) {
        isClickable = enable
        if (enable) {
            setCardBackgroundColor(enableBackgroundColor)
            binding.content.setTextColor(enableTextColor)
            binding.content.text = enableText
            binding.content.setCompoundDrawablesRelativeWithIntrinsicBounds(
                enableIcon,
                null,
                null,
                null
            )
        } else {
            setCardBackgroundColor(disableBackgroundColor)
            binding.content.setTextColor(disableTextColor)
            binding.content.text = disableText
            binding.content.setCompoundDrawablesRelativeWithIntrinsicBounds(
                disableIcon,
                null,
                null,
                null
            )
        }
    }

    fun setLoading(loading: Boolean) {
        isClickable = !loading // Disable clickable when loading
        if (loading) {
            binding.content.visibility = View.GONE
            progressView?.visibility = View.VISIBLE
            binding.content.isEnabled = false
            progressView?.startAnimation()
        } else {
            binding.content.visibility = View.VISIBLE
            progressView?.visibility = View.GONE
            binding.content.isEnabled = true
            progressView?.stopAnimation()
        }
    }

    fun setText(text: String) {
        binding.content.text = text
    }
}
