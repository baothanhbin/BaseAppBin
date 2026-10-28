package com.example.basekotlin.base

import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun Window.hideNavigation() {
    if (setFullScreenWallpaper()) return

    decorView.viewTreeObserver.addOnGlobalLayoutListener {
        val rect = Rect()
        val activityRoot = decorView
        activityRoot.getWindowVisibleDisplayFrame(rect)
        if (setFullScreenWallpaper()) return@addOnGlobalLayoutListener
    }
}

private fun Window.setFullScreenWallpaper(): Boolean {
    WindowCompat.setDecorFitsSystemWindows(this, false)
    statusBarColor = Color.TRANSPARENT

    val windowInsetsController: WindowInsetsControllerCompat? = if (Build.VERSION.SDK_INT >= 30) {
        ViewCompat.getWindowInsetsController(decorView)
    } else {
        WindowInsetsControllerCompat(this, decorView)
    }

    if (windowInsetsController == null) {
        return true
    }
    setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
    )
    windowInsetsController.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

    windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
    windowInsetsController.hide(WindowInsetsCompat.Type.systemGestures())
    return false
}

@Suppress("DEPRECATION")
fun PopupWindow.hideNavigationBar() {
    contentView.post {
        ViewCompat.getWindowInsetsController(contentView)?.apply {
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.navigationBars())
            hide(WindowInsetsCompat.Type.systemGestures())
        }
        contentView.systemUiVisibility =
            contentView.systemUiVisibility or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }
}

@Suppress("DEPRECATION")
fun Window.hideNavBar() {
    WindowCompat.setDecorFitsSystemWindows(this, false)
    navigationBarColor = Color.TRANSPARENT
    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

    val controller = WindowInsetsControllerCompat(this, decorView)
    controller.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    controller.hide(WindowInsetsCompat.Type.navigationBars())
}
