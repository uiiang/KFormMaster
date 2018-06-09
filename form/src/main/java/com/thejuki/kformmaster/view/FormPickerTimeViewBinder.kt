package com.thejuki.kformmaster.view

import android.app.TimePickerDialog
import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.text.InputFilter
import android.text.InputType
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormPickerTimeElement
import com.thejuki.kformmaster.state.FormEditTextViewState
import com.thejuki.kformmaster.utils.setHintTextColorExt
import com.thejuki.kformmaster.utils.setTextBoldExt
import com.thejuki.kformmaster.utils.setTextColorExt
import com.thejuki.kformmaster.utils.setTextSizeExt

/**
 * Form Picker Time ViewBinder
 *
 * View Binder for [FormPickerTimeElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerTimeViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element, FormPickerTimeElement::class.java, { model, finder, _ ->
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

        model.editView = editTextValue

        editTextValue.setRawInputType(InputType.TYPE_NULL)

        // If no value is set by the user, create a new instance of TimeHolder
        with(model.value)
        {
            if (this == null) {
                model.setValue(FormPickerTimeElement.TimeHolder())
            }
            this?.validOrCurrentTime()
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
        val timePickerDialog = TimePickerDialog(context,
                timeDialogListener(model, editTextValue, textViewError),
                model.value?.hourOfDay ?: 0,
                model.value?.minute ?: 0,
                false)

        setOnClickListener(editTextValue, itemView, timePickerDialog)
    }, object : ViewStateProvider<FormPickerTimeElement, ViewHolder> {
        override fun createViewStateID(model: FormPickerTimeElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormEditTextViewState(holder)
        }
    })

    private fun timeDialogListener(model: FormPickerTimeElement,
                                   editTextValue: AppCompatEditText,
                                   textViewError: AppCompatTextView): TimePickerDialog.OnTimeSetListener {
        return TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            var timeChanged = false
            with(model.value)
            {
                timeChanged = !(this?.hourOfDay == hourOfDay && this.minute == minute)
                this?.hourOfDay = hourOfDay
                this?.minute = minute
                this?.isEmptyTime = false
            }

            if (timeChanged) {
                model.error = null
                formBuilder.onValueChanged(model)
                model.valueObservers.forEach { it(model.value, model) }
                editTextValue.setText(model.valueAsString)
            }
        }
    }
}