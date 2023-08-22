package core.views.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmapOrNull
import core.views.R
import kotlin.math.cos
import kotlin.math.sin

class ClockWidget(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : View(context, attrs, defStyleAttr, defStyleRes) {

    // region Constructors
    constructor(context: Context) :
        this(context, null, 0, 0)

    constructor(context: Context, attrs: AttributeSet?) :
        this(context, attrs, 0, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        this(context, attrs, defStyleAttr, 0)
    // endregion
    private var clockBackground: Drawable? = AppCompatResources.getDrawable(context, R.drawable.ic_app)

    private val mat = Matrix()
    private var srcRect: Rect? = null
    private var dstRect: Rect? = null
    private var hourRect = Rect(0, 0, 0, 0)
    private var minRect = Rect(0, 0, 0, 0)
    private var secRect = Rect(0, 0, 0, 0)
    private var paint: Paint = Paint()

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ClockWidget)
        val resourceId = attributes.getResourceId(R.styleable.ClockWidget_background, 0)
        // clockBackground = ResourcesCompat.getDrawable(context.resources, resourceId, null)
        attributes.recycle()

        paint.isAntiAlias = true
        paint.color = Color.WHITE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        srcRect = Rect(0, 0, w, h)
        dstRect = Rect(0, 0, w, h)
    }

    /**
     * Show clock
     *
     * @param hour based on 24 format
     * @param min
     * @param sec
     */
    fun showClock(hour: Int, min: Int, sec: Int) {
        val hourDegree = (90 - ((hour + (min / 60f)) * 360f / 12f)) * (Math.PI / 180f)
        val minDegree = (90 - ((min + (sec / 60f)) * 360f / 60f)) * (Math.PI / 180f)
        val secDegree = (90 - (sec * 360f / 60f)) * (Math.PI / 180f)
        val hourLength = width / 10f
        val minLength = width * 32f / 200f
        val secLength = width / 5f
        val w1 = width / 2
        val h1 = height / 2

        var w2 = (w1 + cos(hourDegree) * hourLength).toInt()
        var h2 = (h1 - sin(hourDegree) * hourLength).toInt()
        hourRect = Rect(w1, h1, w2, h2)

        w2 = (cos(minDegree) * minLength + w1).toInt()
        h2 = (h1 - sin(minDegree) * minLength).toInt()
        minRect = Rect(w1, h1, w2, h2)

        w2 = (cos(secDegree) * secLength + w1).toInt()
        h2 = (h1 - sin(secDegree) * secLength).toInt()
        secRect = Rect(w1, h1, w2, h2)
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (srcRect != null && dstRect != null && clockBackground?.toBitmapOrNull() != null)
            canvas?.drawBitmap(clockBackground?.toBitmapOrNull(width, height)!!, srcRect, dstRect!!, paint)

        paint.strokeWidth = 2f
        with(hourRect) {
            canvas?.drawLine(
                left.toFloat(),
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
                paint
            )
        }
        with(minRect) {
            canvas?.drawLine(
                left.toFloat(),
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
                paint
            )
        }
        paint.strokeWidth = 1f
        with(secRect) {
            canvas?.drawLine(
                left.toFloat(),
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
                paint
            )
        }
    }

    fun startAnimation() {
        invalidate()
    }
}
