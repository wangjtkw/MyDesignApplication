package com.example.mydesignapplication.utils

import android.content.res.Resources.NotFoundException
import android.graphics.drawable.ColorDrawable
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.NumberPicker
import java.lang.reflect.Field


object Utils {

    fun setDatePickerDividerColor(datePicker: DatePicker) {
        // 获取 mSpinners
        val llFirst = datePicker.getChildAt(0) as LinearLayout

        // 获取 NumberPicker
        val mSpinners = llFirst.getChildAt(0) as LinearLayout
        for (i in 0 until mSpinners.childCount) {
            val picker = mSpinners.getChildAt(i) as NumberPicker
            val pickerFields: Array<Field> = NumberPicker::class.java.declaredFields
            for (pf in pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true)
                    try {
                        pf.set(picker, ColorDrawable(0x000000))
                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace()
                    } catch (e: NotFoundException) {
                        e.printStackTrace()
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    }
                    break
                }
            }
        }
    }
}