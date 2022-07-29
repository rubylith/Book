package com.fyl.book.utils

import android.util.Log
import com.fyl.book.BuildConfig

public object LogUtils {

    private const val TAG = "EdgeBar"
    private var DEBUG = BuildConfig.DEBUG

    @JvmStatic
    fun v(tag: String, msg: String) {
        if (DEBUG) {
            Log.v(TAG, "$tag # $msg")
        }
    }

    @JvmStatic
    fun v(debug: Boolean, tag: String, msg: String) {
        if (debug) {
            v(tag, msg)
        }
    }

    @JvmStatic
    fun d(msg: String) {
        if (DEBUG) {
            Log.d(TAG, msg)
        }
    }

    @JvmStatic
    fun d(tag: String, msg: String) {
        if (DEBUG) {
            Log.d(TAG, "$tag # $msg")
        }
    }

    @JvmStatic
    fun d(debug: Boolean, tag: String, msg: String) {
        if (debug) {
            d(tag, msg)
        }
    }

    @JvmStatic
    fun i(tag: String, msg: String) {
        if (DEBUG) {
            Log.i(TAG, "$tag # $msg")
        }
    }

    @JvmStatic
    fun i(debug: Boolean, tag: String, msg: String) {
        if (debug) {
            i(tag, msg)
        }
    }

    @JvmStatic
    fun w(tag: String, msg: String) {
        if (DEBUG) {
            Log.w(TAG, "$tag # $msg")
        }
    }

    @JvmStatic
    fun w(debug: Boolean, tag: String, msg: String) {
        if (debug) {
            w(tag, msg)
        }
    }

    @JvmStatic
    fun e(tag: String, msg: String) {
        if (DEBUG) {
            Log.e(TAG, "$tag # $msg")
        }
    }

    @JvmStatic
    fun e(debug: Boolean, tag: String, msg: String) {
        if (debug) {
            e(tag, msg)
        }
    }

    @JvmStatic
    fun e(tag: String, msg: String, tr: Throwable) {
        if (DEBUG) {
            Log.e(TAG, "$tag # $msg", tr)
        }
    }

    @JvmStatic
    fun e(debug: Boolean, tag: String, msg: String, tr: Throwable) {
        if (debug) {
            e(tag, msg, tr)
        }
    }

    @JvmStatic
    fun showTrace(tag: String, msg: String) {
        if (DEBUG) {
            d(tag, "$msg \n ${Log.getStackTraceString(Throwable())}")
        }
    }

    @JvmStatic
    fun showTrace(debug: Boolean, tag: String, msg: String) {
        if (debug) {
            showTrace(tag, msg)
        }
    }
}