package com.example.mydesignapplication.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import com.example.mydesignapplication.R
import com.example.mydesignapplication.listener.MyTextWatcher
import com.example.mydesignapplication.utils.showSoftInputFromWindow

class WelfareDialogUtil {
    private lateinit var welfareDialog: Dialog
    private lateinit var welfareInflate: View
    private lateinit var welfareCancelLayout: FrameLayout
    private lateinit var welfareDetermineLayout: FrameLayout
    private lateinit var welfareEditLayout: FrameLayout
    private lateinit var welfareEditText: EditText
    private lateinit var welfareEditTextView: TextView

    fun init(context: Context, determineCallback: (String) -> Unit) {
        welfareDialog = Dialog(context, R.style.ChooseHeadImageDialog)
        welfareInflate = LayoutInflater.from(context).inflate(R.layout.dialog_welfare, null)
        welfareCancelLayout = welfareInflate.findViewById(R.id.dialog_welfare_cancel_layout)
        welfareDetermineLayout = welfareInflate.findViewById(R.id.dialog_welfare_determine_layout)
        welfareEditLayout = welfareInflate.findViewById(R.id.dialog_welfare_edit_layout)
        welfareEditText = welfareInflate.findViewById(R.id.dialog_welfare_edit_text)
        welfareEditTextView = welfareInflate.findViewById(R.id.dialog_welfare_text_view)
        welfareDialog.setContentView(welfareInflate)
        val dialogWindow = welfareDialog.window
        val windowManager = dialogWindow?.windowManager
        val display = windowManager?.defaultDisplay
        val layoutParams = dialogWindow?.attributes
        val point = Point()
        display?.getSize(point)
        layoutParams?.width = point.x
        dialogWindow?.setGravity(Gravity.BOTTOM)
        dialogWindow?.attributes = layoutParams
        welfareDialog.show()
        setCancelListener()
        setDetermineListener(determineCallback)
        setEditTextListener()
        setListener(context)
    }


    private fun setCancelListener() {
        welfareCancelLayout.setOnClickListener {
            welfareDialog.dismiss()
        }
    }

    private fun setDetermineListener(callback: (String) -> Unit) {
        welfareDetermineLayout.setOnClickListener {
            callback(welfareEditText.text.toString())
            welfareDialog.dismiss()
        }
    }

    private fun setListener(context: Context) {
        welfareEditLayout.setOnClickListener {
            showSoftInputFromWindow(context, welfareEditText)
        }
    }

    private fun setEditTextListener() {
        welfareEditText.addTextChangedListener(MyTextWatcher({
            welfareEditTextView.text = it.length.toString()
        }))
    }


}