package com.ondevop.core_ui

import android.app.Activity
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


enum class ButtonState { Pressed, Idle }




enum class Keyboard {
    Opened, Closed
}



fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun String.toFormattedDate(): String {
    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault())
    val date = inputFormat.parse(this)
    return outputFormat.format(date)
}

fun Long.toFormattedDateFromMilli(): String {
    val dateFormat = SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault())
    return dateFormat.format(Date(this))
}


fun Modifier.bounceClick(onClick: () -> Unit = {}) = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(if (buttonState == ButtonState.Pressed) 0.90f else 1f)
    
    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .click {
            onClick()
        }
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}

fun Modifier.click(onClick: () -> Unit = {}) = composed {
    this
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {
                onClick()
            }
        )
    
}

fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(this))
    } else {
        this
    }
}


fun <T> debounce(timeMillis: Long = 300L, action: (T) -> Unit): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = CoroutineScope(Dispatchers.Default).launch {
            delay(timeMillis)
            withContext(Dispatchers.Main) {
                action(param)
            }
        }
    }
}


fun cropBitmap(bitmap: Bitmap, ratio: Double): Bitmap {
    val bitmapRatio = bitmap.width.toFloat() / bitmap.height.toFloat()
    return if (bitmapRatio.toDouble() != ratio) {
        val finalHeight = bitmap.width / ratio
        val firstYPixel = ((bitmap.height / 2) - (finalHeight / 2)).toInt()
        try {
            Bitmap.createBitmap(
                bitmap, 0, firstYPixel, bitmap.width,
                finalHeight.toInt()
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            bitmap
        }
    } else {
        bitmap
    }
}



