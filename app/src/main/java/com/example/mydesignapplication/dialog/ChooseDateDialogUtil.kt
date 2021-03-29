package com.example.mydesignapplication.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.FrameLayout
import android.widget.TextView
import com.example.mydesignapplication.R
import com.example.mydesignapplication.utils.Utils

class ChooseDateDialogUtil {
    private val TAG = "ChooseDateDialogUtil"

    private lateinit var chooseDateDialog: Dialog
    private lateinit var chooseDateInflater: View
    private lateinit var chooseDateCancelLayout: FrameLayout
    private lateinit var chooseDateDetermineLayout: FrameLayout
    private lateinit var chooseDateTextView: TextView
    private lateinit var chooseDateDatePickerView: DatePicker


    fun init(context: Context, determinedCallback: (String) -> Unit) {
        chooseDateDialog = Dialog(context, R.style.ChooseHeadImageDialog)
        chooseDateInflater =
            LayoutInflater.from(context).inflate(R.layout.dialog_choose_date, null)
        chooseDateCancelLayout =
            chooseDateInflater.findViewById(R.id.dialog_choose_date_cancel_layout)
        chooseDateDetermineLayout =
            chooseDateInflater.findViewById(R.id.dialog_choose_date_determine_layout)
        chooseDateTextView = chooseDateInflater.findViewById(R.id.dialog_choose_date_date_text_view)
        chooseDateDatePickerView =
            chooseDateInflater.findViewById(R.id.dialog_choose_date_picker_view)
        Utils.setDatePickerDividerColor(chooseDateDatePickerView)
        chooseDateDialog.setContentView(chooseDateInflater)
        val dialogWindow = chooseDateDialog.window
        val windowManager = dialogWindow?.windowManager
        val display = windowManager?.defaultDisplay
        val layoutParams = dialogWindow?.attributes
        val point = Point()
        display?.getSize(point)
        layoutParams?.width = point.x
        dialogWindow?.setGravity(Gravity.BOTTOM)
        dialogWindow?.attributes = layoutParams
        setCancelListener()
        setDeterminedListener(determinedCallback)
        setPickerViewListener()
        chooseDateTextView.text = getDate()
        chooseDateDialog.show()

    }

    private fun setCancelListener() {
        chooseDateCancelLayout.setOnClickListener {
            chooseDateDialog.dismiss()
        }
    }

    private fun setDeterminedListener(determinedCallback: (String) -> Unit) {
        chooseDateDetermineLayout.setOnClickListener {
            determinedCallback(getDate())
            chooseDateDialog.dismiss()
        }
    }

    private fun getDate(): String {
        var year = "${chooseDateDatePickerView.year}"
        var month = "${chooseDateDatePickerView.month + 1}"
        var day = "${chooseDateDatePickerView.dayOfMonth}"
        if (chooseDateDatePickerView.month < 9) {
            month = "0$month"
        }
        if (chooseDateDatePickerView.dayOfMonth < 10) {
            day = "0$day"
        }
        val date = "$year-$month-$day"
        Log.d(TAG, "date $date")
        return date
    }

    private fun setPickerViewListener() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            chooseDateDatePickerView.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                chooseDateTextView.text = getDate()
            }
        }
    }
}