package com.example.basekotlin.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.basekotlin.R
import com.example.basekotlin.data.listener.TapListener
import com.example.basekotlin.data.listener.TapNoHandleListener

fun View.tap(action: (view: View?) -> Unit) {
    setOnClickListener(object : TapListener() {
        override fun onTap(v: View?) {
            v?.animateTap {
                action(v)
            } ?: action(v)
        }
    })
}

fun View.tapNoHandle(action: (view: View?) -> Unit) {
    setOnClickListener(object : TapNoHandleListener() {
        override fun onTap(v: View?) {
            v?.animateTap {
                action(v)
            } ?: action(v)
        }
    })
}

fun RecyclerView.applyRecyclerTapBehavior() {
    itemAnimator = null
    overScrollMode = View.OVER_SCROLL_NEVER
}

private fun View.animateTap(onEnd: () -> Unit) {
    val tapToken = System.nanoTime()
    setTag(R.id.tap_animation_token, tapToken)

    animate()
        .setListener(null)
        .withEndAction(null)
        .cancel()

    animate()
        .scaleX(TAP_SCALE)
        .scaleY(TAP_SCALE)
        .setDuration(TAP_SCALE_DOWN_DURATION)
        .setInterpolator(TAP_SCALE_DOWN_INTERPOLATOR)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (getTag(R.id.tap_animation_token) != tapToken) return

                animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(TAP_SCALE_UP_DURATION)
                    .setInterpolator(TAP_SCALE_UP_INTERPOLATOR)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            if (getTag(R.id.tap_animation_token) == tapToken) {
                                setTag(R.id.tap_animation_token, null)
                                onEnd()
                            }
                        }
                    })
                    .start()
            }

            override fun onAnimationCancel(animation: Animator) {
                if (getTag(R.id.tap_animation_token) == tapToken) {
                    setTag(R.id.tap_animation_token, null)
                }
            }
        })
        .start()
}

@SuppressLint("ClickableViewAccessibility")
fun View.tapDouble(onDoubleClick: () -> Unit) {
    val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            onDoubleClick()
            return true
        }
    })

    setOnTouchListener { _, event ->
        gestureDetector.onTouchEvent(event)
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.inVisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun setSrcImage(imageView: ImageView, drawable: Int) {
    imageView.setImageResource(drawable)
}

fun setTextBackground(context: Context, textView: TextView, drawable: Int) {
    textView.background =
        ContextCompat.getDrawable(context, drawable)
}

fun setTextColor(textView: TextView, color: String) {
    textView.setTextColor(Color.parseColor(color))
}

fun setTintBackgroundView(view: View, color: String) {
    view.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))
}

private const val TAP_SCALE = 0.94f
private const val TAP_SCALE_DOWN_DURATION = 70L
private const val TAP_SCALE_UP_DURATION = 90L
private val TAP_SCALE_DOWN_INTERPOLATOR = DecelerateInterpolator()
private val TAP_SCALE_UP_INTERPOLATOR = AccelerateDecelerateInterpolator()
