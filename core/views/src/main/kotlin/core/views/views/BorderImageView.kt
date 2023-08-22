package core.views.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import core.views.R
import kotlin.math.min

class BorderImageView(
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

    private val borderColor: Int
    private val borderWidth: Float?
    private val strokeWidth: Float?
    private val paint = Paint()
    private var selectedStrokeColor: Int? = null
    private var src: Bitmap? = null
    private var sPaint: Paint? = null
    private var srcRect: Rect? = null
    private var dstRect: Rect? = null
    private var radius: Float = 0f
    private var sRadius: Float = 0f
    private var showStroke: Boolean = false

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.BorderImageView)
        val drawable = attributes.getDrawable(R.styleable.BorderImageView_src)
        src = drawable?.let {
            it.toBitmap(it.intrinsicWidth, it.intrinsicHeight).also { b ->
                srcRect = Rect(0, 0, b.width, b.height)
            }
        }

        borderWidth = attributes.getDimension(R.styleable.BorderImageView_border_width, 1f)
        strokeWidth = attributes.getDimension(R.styleable.BorderImageView_stroke_width, 1f)
        borderColor = attributes.getColor(R.styleable.BorderImageView_border_color, Color.WHITE)
        selectedStrokeColor =
            attributes.getColor(R.styleable.BorderImageView_stroke_color, Color.WHITE)
        attributes.recycle()
    }

    fun setSrc(bitmap: Bitmap) {
        src = bitmap
        src?.let { srcRect = Rect(0, 0, it.width, it.height) }
        invalidate()
    }

    fun setStroke(show: Boolean) {
        showStroke = show
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val w = when (widthMode) {
            MeasureSpec.AT_MOST -> {
                src?.width?.let { min(widthSize, it) } ?: widthSize
            }

            MeasureSpec.EXACTLY -> {
                widthSize
            }

            else -> src?.width ?: widthSize
        }
        val h = when (heightMode) {
            MeasureSpec.EXACTLY -> {
                heightSize
            }

            else -> w
        }
        setMeasuredDimension(w, h)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        dstRect = Rect(0, 0, w, h)
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        borderWidth?.let {
            paint.strokeWidth = it
            radius = (min(w, h) - it).plus(1f) / 2f
        }
        sPaint = Paint()
        sPaint?.style = Paint.Style.STROKE
        strokeWidth?.let {
            sPaint?.strokeWidth = strokeWidth
            sRadius = (min(w, h) - strokeWidth) / 2f
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (src != null && dstRect != null && srcRect != null) {
            canvas?.drawBitmap(src!!, srcRect, dstRect!!, paint)
            paint.color = borderColor
            canvas?.drawCircle(width / 2f, height / 2f, radius, paint)
        }

        if (showStroke) {
            selectedStrokeColor?.let { strokeColor ->
                sPaint?.let {
                    it.color = strokeColor
                    canvas?.drawCircle(width / 2f, height / 2f, sRadius, it)
                }
            }
        }
    }
}
