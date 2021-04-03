package com.example.mydesignapplication.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebChromeClient
import android.widget.FrameLayout
import com.example.mydesignapplication.R
import com.example.mydesignapplication.myview.PickerView

class SingleDataDialogUtil {
    private lateinit var singleDataDialog: Dialog
    private lateinit var singleDataInflate: View
    private lateinit var singleDataCancelLayout: FrameLayout
    private lateinit var singleDataDetermineLayout: FrameLayout
    private lateinit var singleDataPickerView: PickerView

    fun init(
        context: Context,
        pickerViewData: List<String>,
        determineCallback: (Int) -> Unit,
        index: Int = 0,
        pickerViewCallback: (String) -> Unit = {},
    ) {
        singleDataDialog = Dialog(context, R.style.ChooseHeadImageDialog)
        singleDataInflate =
            LayoutInflater.from(context).inflate(R.layout.dialog_choose_single_data, null)
        singleDataCancelLayout =
            singleDataInflate.findViewById(R.id.dialog_choose_single_data_cancel_layout)
        singleDataDetermineLayout =
            singleDataInflate.findViewById(R.id.dialog_choose_single_data_determine_layout)
        singleDataPickerView =
            singleDataInflate.findViewById(R.id.dialog_choose_single_data_picker_view)
        singleDataDialog.setContentView(singleDataInflate)
        val dialogWindow = singleDataDialog.window
        val windowManager = dialogWindow?.windowManager
        val display = windowManager?.defaultDisplay
        val layoutParams = dialogWindow?.attributes
        val point = Point()
        display?.getSize(point)
        layoutParams?.width = point.x
        dialogWindow?.setGravity(Gravity.BOTTOM)
        dialogWindow?.attributes = layoutParams
        singleDataPickerView.setData(pickerViewData)
        singleDataPickerView.setSelectIndex(index)
        singleDataDialog.show()
        setPickerViewListener { pickerViewCallback(it) }
        setCancelListener()
        setDetermineListener(determineCallback)
    }

    private fun setPickerViewListener(pickerViewCallback: (String) -> Unit) {
        singleDataPickerView.setOnSelectedCallBack { pickerViewCallback(it) }
    }

    private fun setCancelListener() {
        singleDataCancelLayout.setOnClickListener {
            singleDataDialog.dismiss()
        }
    }

    private fun setDetermineListener(determineCallback: (Int) -> Unit) {
        singleDataDetermineLayout.setOnClickListener {
            determineCallback(singleDataPickerView.getCurrentIndex())
            singleDataDialog.dismiss()
        }
    }

}