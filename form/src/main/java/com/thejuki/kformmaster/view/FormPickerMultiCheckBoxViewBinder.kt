package com.thejuki.kformmaster.view

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.text.InputType
import android.view.View
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormPickerMultiCheckBoxElement
import com.thejuki.kformmaster.state.FormEditTextViewState
import com.thejuki.kformmaster.utils.setHintTextColorExt
import com.thejuki.kformmaster.utils.setTextBoldExt
import com.thejuki.kformmaster.utils.setTextColorExt
import com.thejuki.kformmaster.utils.setTextSizeExt

/**
 * Form Picker MultiCheckBox ViewBinder
 *
 * View Binder for [FormPickerMultiCheckBoxElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerMultiCheckBoxViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element, FormPickerMultiCheckBoxElement::class.java, { model, finder, _ ->
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

        model.editView = editTextValue

        editTextValue.setRawInputType(InputType.TYPE_NULL)
        editTextValue.isFocusable = false
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

        model.reInitDialog(context, formBuilder)

    }, object : ViewStateProvider<FormPickerMultiCheckBoxElement<*>, ViewHolder> {
        override fun createViewStateID(model: FormPickerMultiCheckBoxElement<*>): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormEditTextViewState(holder)
        }
    })
}
