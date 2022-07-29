package com.fyl.book.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.StringRes
import java.io.File
import java.io.FileOutputStream
import java.lang.RuntimeException

public object Utils {

    private val isBuildForQ = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q  // Android-10
    private val isBuildForR = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R  // Android-11
    private val isBuildForS = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S  // Android-12

    @JvmStatic
    fun getActivityByView(v: View): Activity? {
        return v.context.let {
            if (it is Activity) it else null
        }
    }

    @JvmStatic
    fun <T> getOverrideObject(context: Context, clazz: Class<T>, @StringRes resId: Int): T {
        context.getString(resId).takeIf { it.isNotEmpty() }?.let {
            try {
                Class.forName(it)?.getDeclaredConstructor(Context::class.java)?.newInstance(context)?.let { obj ->
                    return clazz.cast(obj) as T
                }
            } catch (e: Exception) {
                LogUtils.d("getOverrideObject() Bad override obj: $e")
            }
        }

        return try {
            clazz.newInstance()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @JvmStatic
    fun getDisplaySize(context: Context): Point {
        return Point().also {
            context.resources.displayMetrics.run {
                it.x = widthPixels
                it.y = heightPixels
            }
        }
    }

    @JvmStatic
    @SuppressLint("NewApi")
    fun getWindowsSize(context: Context): Point {
        return Point().also {
            (context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.run {
                if (isBuildForR) {
                    currentWindowMetrics.bounds.let { bounds ->
                        it.x = bounds.width()
                        it.y = bounds.height()
                    }
                } else {
                    defaultDisplay.getRealSize(it)
                }
            }
        }
    }

    @JvmStatic
    fun getWindowsBounds(context: Context): Rect {
        return (context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.currentWindowMetrics?.bounds ?: Rect()
    }

    @JvmStatic
    fun getWindowInsets(context: Context): Insets {
        return if (isBuildForR) {
            (context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.currentWindowMetrics?.windowInsets
                ?.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout())
                ?: Insets.NONE
        } else {
            getStatusBarHeight(context).let {
                Insets.of(0, it, 0, getWindowsSize(context).y - getDisplaySize(context).y - it)
            }
        }
    }

    @JvmStatic
    fun getStatusBarHeight(context: Context): Int {
        return context.resources.run {
            getIdentifier("status_bar_height", "dimen", "android").takeIf { it > 0 }?.let {
                getDimensionPixelSize(it)
            } ?: 0
        }
    }

    @JvmStatic
    fun getNavigationBarHeight(context: Context): Int {
        return context.resources.run {
            getIdentifier("navigation_bar_height", "dimen", "android").takeIf { it > 0 }?.let {
                getDimensionPixelSize(it)
            } ?: 0
        }
    }

    @JvmStatic
    fun saveIconToCacheDir(context: Context, icon: Bitmap): File? {
        try {
            val iconFile = File(context.cacheDir, "icon.png")
            val fileOS = FileOutputStream(iconFile)
            icon.compress(Bitmap.CompressFormat.PNG, 100, fileOS)
            fileOS.flush()
            fileOS.close()
            return iconFile
        } catch (e: java.lang.Exception) {
            LogUtils.d("saveIconToCacheDir() Failed: ${e.stackTrace}")
        }
        return null
    }

    @JvmStatic
    fun drawableToBitmap(drawable: Drawable): Bitmap {
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else {
            Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888).also {
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                drawable.draw(Canvas(it))
            }
        }
    }
}