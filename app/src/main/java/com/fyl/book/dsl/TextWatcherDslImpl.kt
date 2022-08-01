package com.fyl.book.dsl

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

class TextWatcherDslImpl : TextWatcher {

    private var beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
    private var onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
    private var afterTextChanged: ((Editable?) -> Unit)? = null

    fun beforeTextChanged(method: (CharSequence?, Int, Int, Int) -> Unit) {
        beforeTextChanged = method
    }

    fun onTextChanged(method: (CharSequence?, Int, Int, Int) -> Unit) {
        onTextChanged = method
    }

    fun afterTextChanged(method: (Editable?) -> Unit) {
        afterTextChanged = method
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeTextChanged?.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged?.invoke(s, start, before, count)
    }

    override fun afterTextChanged(s: Editable?) {
        afterTextChanged?.invoke(s)
    }
}

fun TextView.addTextChangedListenerDsl(init: TextWatcherDslImpl.() -> Unit) {
    this.addTextChangedListener(TextWatcherDslImpl().also {
        it.init()
    })
}