package com.example.mydesignapplication.listener

import android.text.Editable
import android.text.TextWatcher
import javax.security.auth.callback.Callback

class MyTextWatcher(
    val afterCallback: ((String) -> Unit) = {},
    val beforeCallback: ((s: String, start: Int, count: Int, after: Int) -> Unit) = { s: String, i: Int, i1: Int, i2: Int -> },
    val onChangedCallback: ((s: String, start: Int, before: Int, count: Int) -> Unit) = { s: String, i: Int, i1: Int, i2: Int -> }
) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        if (s != null) {
            beforeCallback(s.toString(), start, count, after)
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s != null) {
            onChangedCallback(s.toString(), start, before, count)
        }

    }

    override fun afterTextChanged(s: Editable?) {
        if (s != null) {
            afterCallback(s.toString())
        }
    }
}