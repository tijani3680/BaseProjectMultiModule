package core.views.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.imageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import core.views.views.BorderImageView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow

fun View.clicks(): Flow<Unit> = callbackFlow {
    setOnClickListener {
        trySend(Unit)
    }
    awaitClose { setOnClickListener(null) }
}

inline fun BorderImageView.load(
    data: Any?,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {}
): Disposable {
    val request = ImageRequest.Builder(context)
        .data(data)
        .target { result -> (result as? BitmapDrawable)?.let { setSrc(it.bitmap) } }
        .apply(builder)
        .build()
    return imageLoader.enqueue(request)
}

fun Activity.isAllPermissionsGranted(
    permissions: Array<out String>,
    grantResults: IntArray
): Boolean = (grantResults.size >= permissions.size) &&
    grantResults.all { it == PackageManager.PERMISSION_GRANTED }

fun Activity.isAllPermissionsGranted(permissions: Array<out String>) = permissions.all {
    ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}

fun View.hideKeyboard() {
    clearFocus()
    val input = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    input?.hideSoftInputFromWindow(windowToken, 0)
}


fun ImageView.changeImageDrawable(value: Int) {
    ResourcesCompat.getDrawable(resources, value, null)
}

private const val IDLE_TIME_IN_MILLIS = 3000L

fun EditText.edtTextChangeListener() = channelFlow {
    val textWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // StopShip
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // StopShip
        }

        override fun afterTextChanged(s: Editable?) {
            if (s?.toString().isNullOrBlank()) {
                trySend(false)
            } else {
                trySend(true)
            }
        }
    }

    addTextChangedListener(textWatcher)
    awaitClose { removeTextChangedListener(textWatcher) }
}

fun EditText.edtSearchChangeListener() = channelFlow {
    val textWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // StopShip
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // StopShip
        }

        override fun afterTextChanged(s: Editable?) {
            if (s?.toString().isNullOrBlank()) {
                trySend("")
            } else {
                trySend(s.toString())
            }
        }
    }

    addTextChangedListener(textWatcher)
    awaitClose { removeTextChangedListener(textWatcher) }
}



fun EditText.isTypingFlow() = channelFlow {
    val textWatcher = object : TextWatcher {
        private var isTyping = false
        private val handler = Handler(Looper.getMainLooper())
        private val runnable = Runnable {
            if (!isTyping) {
                trySend(false)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // StopShip
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            isTyping = true
            handler.removeCallbacks(runnable)
            if (before < count) {
                trySend(true)
            }
        }

        override fun afterTextChanged(s: Editable?) {
            isTyping = false
            handler.postDelayed(runnable, IDLE_TIME_IN_MILLIS)
        }
    }

    addTextChangedListener(textWatcher)
    awaitClose { removeTextChangedListener(textWatcher) }
}

fun Drawable.changeBackgroundColor(context: Context, @ColorRes colorInt: Int) {
    when (val muted = this.mutate()) {
        is GradientDrawable -> muted.setColor(ContextCompat.getColor(context, colorInt))
        is ShapeDrawable -> muted.paint.color = ContextCompat.getColor(context, colorInt)
        is ColorDrawable -> muted.color = ContextCompat.getColor(context, colorInt)
    }
}

fun Float.toPx(displayMetrics: DisplayMetrics): Float {
    return (this * (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
}

fun Int.toPx(displayMetrics: DisplayMetrics): Float {
    return (this * (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
}

@Suppress("DEPRECATION")
fun Fragment.getPackageInfo(): PackageInfo {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        requireContext().packageManager.getPackageInfo(
            requireContext().packageName,
            PackageManager.PackageInfoFlags.of(0)
        )
    } else {
        requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
    }
}
