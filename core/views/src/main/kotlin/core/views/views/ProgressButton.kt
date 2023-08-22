package core.views.views

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import core.views.R

class ProgressButton constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    // region Constructors
    constructor(context: Context) :
        this(context, null, 0, 0)

    constructor(context: Context, attrs: AttributeSet?) :
        this(context, attrs, 0, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        this(context, attrs, defStyleAttr, 0)
    // endregion

    private val progressBar: DotProgressBarView
    private val buttonTextView: TextView

    init {
        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressButton,
            defStyleAttr,
            0
        )
        val buttonText = arr.getString(R.styleable.ProgressButton_text)
        val loading = arr.getBoolean(R.styleable.ProgressButton_loading, false)
        isEnabled = arr.getBoolean(R.styleable.ProgressButton_enabled, true)
        arr.recycle()
        LayoutInflater.from(context).inflate(R.layout.progress_button, this, true).apply {
            buttonTextView = findViewById(R.id.button_text)
            progressBar = findViewById(R.id.progress_indicator)
        }

        buttonTextView.isEnabled = isEnabled
        setText(buttonText)
        setLoading(loading)
        setButtonTextViewColor()
    }

    fun setLoading(loading: Boolean) {
        isClickable = !loading // Disable clickable when loading
        if (loading) {
            buttonTextView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            buttonTextView.isEnabled = false
            progressBar.startAnimation()
        } else {
            buttonTextView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            buttonTextView.isEnabled = true
            progressBar.stopAnimation()
        }
    }

    private fun setText(text: String?) {
        buttonTextView.text = text
    }

    private fun setButtonTextViewColor() {
        when (context.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                buttonTextView.setTextColor(ContextCompat.getColor(context, R.color.cloud_burst))
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                buttonTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        }
    }
}
