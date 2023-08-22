package core.views.views

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import core.views.R
import core.views.util.toPx
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PalPhoneClock(
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

    //region Variables

    private var clockWidget: ClockWidget? = null
    private var imgClockInfo: ImageView? = null
    private var chronometer: Chronometer? = null

    private var timeStr: String = "12:00:00"
    private var isReverse: Boolean = false
    private var textColor: Int
    private var text: String? = null
    private var isClockShowing: Boolean = false
    private var animator: Animator? = null
    var onClickListener = {}

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility != VISIBLE) isClockShowing = false
    }

    // endregion

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PalPhoneClock)
        isReverse = attributes.getBoolean(R.styleable.PalPhoneClock_reverse, false)
        textColor = attributes.getColor(R.styleable.PalPhoneClock_text_color, Color.WHITE)
        text = attributes.getString(R.styleable.PalPhoneClock_app_name)
        attributes.recycle()
        initialView()
    }

    private fun initialView() {
        gravity = Gravity.CENTER_VERTICAL
        removeAllViews()
        imgClockInfo = ImageView(context).apply {
            val displayMetrics = context.resources.displayMetrics
            layoutParams = LayoutParams(
                24.toPx(displayMetrics).toInt(),
                24.toPx(displayMetrics).toInt()
            ).apply {
                setPadding(5, 5, 5, 5)
                setMargins(8, 0, 8, 0)
            }
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_info_clock))
            visibility = if (isReverse && !isClockShowing) GONE else VISIBLE
        }

        chronometer = Chronometer(context).apply {
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                setMargins(4, 0, 4, 0)
                text = ""
            }
            textSize = 18.toPx(context.resources.displayMetrics)
            typeface = ResourcesCompat.getFont(context, R.font.roboto_bold)
            setTextColor(textColor)
            setOnChronometerTickListener {
                timeStr = it.text.toString()
                extractDateFromChronometerTimeStr(it.text.toString())?.let { d ->
                    updateClock(d)
                    remakeView(d)
                }
            }
            setOnClickListener { clockWidget?.performClick() }
        }
        clockWidget = ClockWidget(context = context).apply {
            val displayMetrics = context.resources.displayMetrics
            layoutParams = LayoutParams(
                35.toPx(displayMetrics).toInt(),
                35.toPx(displayMetrics).toInt()
            ).apply {
                setMargins(4, 0, 4, 0)
                setOnClickListener {
                    onClickListener()
                    isClockShowing = !isClockShowing
                    extractDateFromChronometerTimeStr(timeStr)?.let { d -> remakeView(d) }
                    animator?.cancel()
                    animator?.start()
                }
            }
        }

        if (isReverse) {
            addView(imgClockInfo)
            addView(chronometer)
            addView(clockWidget)
        } else {
            addView(clockWidget)
            addView(chronometer)
            addView(imgClockInfo)
        }
    }
    fun setInfoClickListener(onClick: () -> Unit) {
        imgClockInfo?.setOnClickListener {
            onClick.invoke()
        }
    }

    fun initClock(baseTime: Long) {
        initAnimator()
        chronometer?.base = baseTime
        chronometer?.start()
    }

    fun getTimeStr() = chronometer?.text

    private fun remakeView(d: Date) {

        if (isClockShowing) {
            val sdf2 = SimpleDateFormat("HH:mm", Locale.ROOT)
            chronometer?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
            chronometer?.text = sdf2.format(d)
            imgClockInfo?.visibility = VISIBLE
        } else {
            chronometer?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24f)
            imgClockInfo?.visibility = GONE
            if (isReverse) chronometer?.text = ""
            else chronometer?.text = text
        }
    }

    private fun updateClock(d: Date) {
        Calendar.getInstance().apply {
            time = d
            clockWidget?.showClock(
                get(Calendar.HOUR_OF_DAY),
                get(Calendar.MINUTE),
                get(Calendar.SECOND)
            )
        }
    }

    private fun extractDateFromChronometerTimeStr(timeStr: String): Date? {
        val sdf1 = SimpleDateFormat("HH:mm:ss", Locale.ROOT)
        return try {
            sdf1.parse(timeStr)
        } catch (e: Exception) {
            null
        }
    }

    private fun initAnimator() {
        animator?.cancel()
        animator =
            ObjectAnimator.ofFloat(chronometer, View.SCALE_X, 0.08f, 1f)
                .apply {
                    duration = 100
                    repeatCount = 0
                }
    }
}
