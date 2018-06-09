package com.thejuki.kformmaster.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormEmailEditTextElement
import com.thejuki.kformmaster.state.FormEditTextViewState
import com.thejuki.kformmaster.utils.setHintTextColorExt
import com.thejuki.kformmaster.utils.setTextBoldExt
import com.thejuki.kformmaster.utils.setTextColorExt
import com.thejuki.kformmaster.utils.setTextSizeExt

/**
 * Form Email EditText ViewBinder
 *
 * View Binder for [FormEmailEditTextElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormEmailEditTextViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element, FormEmailEditTextElement::class.java, { model, finder, _ ->
        buildLayout(model, finder, context, formBuilder)
        val (textViewTitle, textViewError, itemView) = buildTitle(model, finder, context, formBuilder)
        buildValueWrap(model, finder, formBuilder)

        val editTextValue = finder.find(R.id.formElementValue) as AppCompatEditText
        model.editView = editTextValue
        editTextValue.apply {
            model.valueOnClickListener?.let {
                setOnClickListener(it)
            }
            setText(model.valueAsString)
            hint = model.hint ?: ""
            val hintColor = getParamTypeInt(model.hintColor, formBuilder.commonHintColor)
            if (hintColor > -1) {
                setHintTextColorExt(hintColor)
            }

            setSelectAllOnFocus(model.selectAllOnFocus)
            setEditTextFocusEnabled(editTextValue, itemView)

            val size = getParamTypeInt(model.valueTextSize, formBuilder.commonValueTextSize)
            if (size > -1) {
                setTextSizeExt(size)
            }
            setTextBoldExt(getParamTypeBoolean(model.valueBold, formBuilder.commonValueBold))
            val color = getParamTypeInt(model.valueColor, formBuilder.commonValueColor)
            if (color > -1) {
                setTextColorExt(color)
            }

            if (model.valueMaxLength > 0) {
                filters = arrayOf(InputFilter.LengthFilter(model.valueMaxLength))
            }
            // Email
            setRawInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)

            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}

                override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {

                    // get current form element, existing value and new value
                    val currentValue = model.valueAsString
                    val newValue = charSequence.toString()

                    // trigger event only if the value is changed
                    if (currentValue != newValue) {
                        model.setValue(newValue)
                        model.error = null
                        formBuilder.onValueChanged(model)
                    }
                }

                override fun afterTextChanged(editable: Editable) {}
            })
        }
    }, object : ViewStateProvider<FormEmailEditTextElement, ViewHolder> {
        override fun createViewStateID(model: FormEmailEditTextElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormEditTextViewState(holder)
        }
    })

    private fun setEditTextFocusEnabled(editTextValue: AppCompatEditText, itemView: View) {
        itemView.setOnClickListener {
            editTextValue.requestFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            editTextValue.setSelection(editTextValue.text.length)
            imm.showSoftInput(editTextValue, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
