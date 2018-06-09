package com.thejuki.kformmaster.view

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.text.InputFilter
import android.text.InputType
import android.view.View
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormPickerDateElement
import com.thejuki.kformmaster.state.FormEditTextViewState
import com.thejuki.kformmaster.utils.setHintTextColorExt
import com.thejuki.kformmaster.utils.setTextBoldExt
import com.thejuki.kformmaster.utils.setTextColorExt
import com.thejuki.kformmaster.utils.setTextSizeExt

/**
 * Form Picker Date ViewBinder
 *
 * View Binder for [FormPickerDateElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerDateViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element, FormPickerDateElement::class.java, { model, finder, _ ->
        buildLayout(model, finder, context, formBuilder)
        val (textViewTitle, textViewError, itemView) = buildTitle(model, finder, context, formBuilder)
        buildValueWrap(model, finder, formBuilder)

        val editTextValue = finder.find(R.id.formElementValue) as AppCompatEditText

        editTextValue.setText(model.valueAsString)
        editTextValue.hint = model.hint ?: ""
        val hintColor = getParamTypeInt(model.hintColor, formBuilder.commonHintColor)
        if (hintColor > -1) {
            editTextValue.setHintTextColorExt(hintColor)
        }
        editTextValue.setSelectAllOnFocus(model.selectAllOnFocus)

        editTextValue.setRawInputType(InputType.TYPE_NULL)

        model.editView = editTextValue

        // If no value is set by the user, create a new instance of DateHolder
        with(model.value)
        {
            if (this == null) {
                model.setValue(FormPickerDateElement.DateHolder())
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
        val datePickerDialog = DatePickerDialog(context,
                dateDialogListener(model, editTextValue, textViewError),
                model.value?.year ?: 0,
                if ((model.value?.month ?: 0) == 0) 0 else (model.value?.month ?: 0) - 1,
                model.value?.dayOfMonth ?: 0)

        setOnClickListener(editTextValue, itemView, datePickerDialog)
    }, object : ViewStateProvider<FormPickerDateElement, ViewHolder> {
        override fun createViewStateID(model: FormPickerDateElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<*> {
            return FormEditTextViewState(holder)
        }
    })

    private fun dateDialogListener(model: FormPickerDateElement,
                                   editTextValue: AppCompatEditText,
                                   textViewError: AppCompatTextView): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            // get current form element, existing value and new value
            var dateChanged = false
            with(model.value)
            {
                dateChanged = !(this?.year == year && this.month == monthOfYear && this.dayOfMonth == dayOfMonth)
                this?.year = year
                this?.month = monthOfYear + 1
                this?.dayOfMonth = dayOfMonth
                this?.isEmptyDate = false
            }

            if (dateChanged) {
                model.error = null
                formBuilder.onValueChanged(model)
                model.valueObservers.forEach { it(model.value, model) }
                editTextValue.setText(model.valueAsString)
            }
        }
    }
}
