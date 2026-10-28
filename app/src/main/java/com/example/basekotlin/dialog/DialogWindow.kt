package com.example.basekotlin.dialog

import android.graphics.Color
import android.view.Window
import android.view.WindowManager
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.WindowCompat

fun Window.configureDialogSurface(horizontalMarginDp: Int, dimAmount: Float) {
    WindowCompat.setDecorFitsSystemWindows(this, true)
    clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
    setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
    addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    attributes = attributes.apply { this.dimAmount = dimAmount }

    val horizontalMargin = (horizontalMarginDp * context.resources.displayMetrics.density).toInt()
    setLayout(
        context.resources.displayMetrics.widthPixels - horizontalMargin * 2,
        WindowManager.LayoutParams.WRAP_CONTENT,
    )
}
