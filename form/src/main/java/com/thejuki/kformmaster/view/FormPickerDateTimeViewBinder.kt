package com.thejuki.kformmaster.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.text.InputFilter
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormPickerDateTimeElement
import com.thejuki.kformmaster.state.FormEditTextViewState
import com.thejuki.kformmaster.utils.setHintTextColorExt
import com.thejuki.kformmaster.utils.setTextBoldExt
import com.thejuki.kformmaster.utils.setTextColorExt
import com.thejuki.kformmaster.utils.setTextSizeExt
import java.util.*

/**
 * Form Picker DateTime ViewBinder
 *
 * View Binder for [FormPickerDateTimeElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerDateTimeViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element, FormPickerDateTimeElement::class.java, { model, finder, _ ->
        buildLayout(model, finder, context, formBuilder)
        val (textViewTitle, textViewError, itemView) = buildTitle(model, finder, context, formBuilder)
        buildValueWrap(model, finder, formBuilder)

        val editTextValue = finder.find(R.id.formElementValue) as AppCompatEditText
        model.valueOnClickListener?.let {
            editTextValue.setOnClickListener(it)
        }

        editTextValue.setText(model.valueAsString)
        editTextValue.hint = model.hint ?: ""
        val hintColor = getParamTypeInt(model.hintColor, formBuilder.commonHintColor)
        if (hintColor > -1) {
            editTextValue.setHintTextColorExt(hintColor)
        }
        editTextValue.setSelectAllOnFocus(model.selectAllOnFocus)

        model.editView = editTextValue

        editTextValue.setRawInputType(InputType.TYPE_NULL)

        // If no value is set by the user, create a new instance of DateTimeHolder
        with(model.value) {
            if (this == null) {
                model.setValue(FormPickerDateTimeElement.DateTimeHolder())
            }
            this?.validOrCurrentDate()
        }
        editTextValue.apply {
            val size = getParamTypeInt(model.valueTextSize, formBuilder.commonValueTextSize)
            if (size > -1) {
                setTextSizeExt(size)
            }
            setTextBoldExt(getParamTypeBoolean(model.valueBold, formBuilder.commonValueBold))
            val color = getParamTypeInt(model.valueColor, formBuilder.commonValueColor)
            if (color > -1) {
                setTextColorExt(color)
            }
        }
        if (model.valueMaxLength > 0) {
            editTextValue.filters = arrayOf(InputFilter.LengthFilter(model.valueMaxLength))
        }
        val pickerView = TimePickerBuilder(this.context, object : OnTimeSelectListener {
            override fun onTimeSelect(date: Date?, v: View?) {
                val calendar = Calendar.getInstance()
                calendar.time = date
                val timeHolder = FormPickerDateTimeElement.DateTimeHolder(calendar, model.dateFormat)
                with(model.value) {
                    this?.year = timeHolder.year
                    this?.month = timeHolder.month
                    this?.dayOfMonth = timeHolder.dayOfMonth
                    this?.hourOfDay = timeHolder.hourOfDay
                    this?.minute = timeHolder.minute
                    this?.isEmptyDateTime = false
                }
                model.dateValue = calendar
                model.error = null
                formBuilder.onValueChanged(model)
                model.valueObservers.forEach { it(model.value, model) }
                editTextValue.setText(timeHolder.toString())
            }
        }).setType(booleanArrayOf(model.hasYear, model.hasMonth, model.hasDay, model.hasHours, model.hasMins, model.hasSeconds))//年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(21)
                .setRangDate(model.startDate, model.endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build()
        model.dateValue?.let {
            //            val calendar = Calendar.getInstance()
//            calendar.time = it
            pickerView.setDate(it)
            val timeHolder = FormPickerDateTimeElement.DateTimeHolder(it, model.dateFormat)
            editTextValue.setText(timeHolder.toString())
        }
//        val datePickerDialog = DatePickerDialog(context,
//                dateDialogListener(model, editTextValue, textViewError),
//                model.value?.year ?: 0,
//                if ((model.value?.month ?: 0) == 0) 0 else (model.value?.month ?: 0) - 1,
//                model.value?.dayOfMonth ?: 0)
        setOnClickListener(editTextValue, itemView, pickerView)
    }, object : ViewStateProvider<FormPickerDateTimeElement, ViewHolder> {
        override fun createViewStateID(model: FormPickerDateTimeElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormEditTextViewState(holder)
        }
    })

    /**
     * Shows the [dialog] when the form element is clicked
     */
    fun setOnClickListener(editTextValue: AppCompatEditText, itemView: View, pickerView: TimePickerView) {
        editTextValue.isFocusable = false

        // display the dialog on click
        val listener = View.OnClickListener {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(itemView.windowToken, 0)
            pickerView.show()
        }

        itemView.setOnClickListener(listener)
        editTextValue.setOnClickListener(listener)
    }
}
