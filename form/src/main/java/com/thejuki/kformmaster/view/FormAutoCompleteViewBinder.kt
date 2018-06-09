package com.thejuki.kformmaster.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.support.v7.widget.AppCompatTextView
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormAutoCompleteElement
import com.thejuki.kformmaster.state.FormAutoCompleteViewState
import com.thejuki.kformmaster.utils.setHintTextColorExt

/**
 * Form AutoComplete ViewBinder
 *
 * View Binder for [FormAutoCompleteElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormAutoCompleteViewBinder(private val context: Context
                                 , private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element_auto_complete
            , FormAutoCompleteElement::class.java, { model, finder, _ ->
        buildLayout(model, finder, context, formBuilder)
        val (textViewTitle, textViewError, itemView)
                = buildTitle(model, finder, context, formBuilder)
        buildValueWrap(model, finder, formBuilder)

        val autoCompleteTextView = finder.find(R.id.formElementValue) as AppCompatAutoCompleteTextView
        model.valueOnClickListener?.let {
            autoCompleteTextView.setOnClickListener(it)
        }

        autoCompleteTextView.hint = model.hint ?: ""
        val hintColor = getParamTypeInt(model.hintColor, formBuilder.commonHintColor)
        if (hintColor > -1) {
            autoCompleteTextView.setHintTextColorExt(hintColor)
        }

        // Set threshold (the number of characters to type before the drop down is shown)
        autoCompleteTextView.threshold = 1

        model.editView = autoCompleteTextView

        if (model.typedString != null) {
            autoCompleteTextView.setText(model.typedString)
        } else {
            autoCompleteTextView.setText(model.valueAsString)
        }

        // Select all text when focused for easy removal
        if (autoCompleteTextView.text.isNotEmpty()) {
            autoCompleteTextView.setSelectAllOnFocus(true)
        }

        val itemsAdapter = if (model.arrayAdapter != null)
            model.arrayAdapter
        else
            ArrayAdapter(context, android.R.layout.simple_list_item_1, model.options)

        autoCompleteTextView.setAdapter<ArrayAdapter<*>>(itemsAdapter)
        autoCompleteTextView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val typeInt = getParamTypeInt(model.titleFocusColor, formBuilder.commonTitleFocusColor)
                if (typeInt > -1) {
                    textViewTitle.setTextColor(ContextCompat.getColor(context, typeInt))
                }
            } else {
                val typeInt = getParamTypeInt(model.titlesColor, formBuilder.commonTitlesColor)
                if (typeInt > -1) {
                    textViewTitle.setTextColor(ContextCompat.getColor(context, typeInt))
                }
            }
        }

        model.dropdownWidth?.let {
            autoCompleteTextView.dropDownWidth = it
        }

        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, position, _ ->
            model.setValue(adapterView.getItemAtPosition(position))
            model.setError(null)
            setError(textViewError, null)
            formBuilder.onValueChanged(model)
        }

        setEditTextFocusEnabled(autoCompleteTextView, itemView)

        val valueColor = getParamTypeInt(model.valueColor, formBuilder.commonValueColor)
        if (valueColor > -1) {
            autoCompleteTextView.setTextColor(ContextCompat.getColor(context, valueColor))
        }
        autoCompleteTextView.paint.isFakeBoldText = getParamTypeBoolean(model.valueBold, formBuilder.commonValueBold)
        val valueTextSize = getParamTypeInt(model.valueTextSize, formBuilder.commonValueTextSize)
        if (valueTextSize > -1) {
            autoCompleteTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, valueTextSize.toFloat())
        }

        if (model.valueMaxLength > 0) {
            autoCompleteTextView.filters = arrayOf(InputFilter.LengthFilter(model.valueMaxLength))
        }

        autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
                // Keep typed string
                model.typedString = charSequence.toString()

                // If field is blank set form value to null
                if (charSequence.isBlank() && model.value != null) {
                    model.setValue(null)
                    model.error = null
                    formBuilder.onValueChanged(model)
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }, object : ViewStateProvider<FormAutoCompleteElement<*>, ViewHolder> {
        override fun createViewStateID(model: FormAutoCompleteElement<*>): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormAutoCompleteViewState(holder)
        }
    })


    private fun setEditTextFocusEnabled(autoCompleteTextView: AppCompatAutoCompleteTextView, itemView: View) {
        itemView.setOnClickListener {
            autoCompleteTextView.requestFocus()
            if (autoCompleteTextView.text.isNotEmpty()) {
                autoCompleteTextView.selectAll()
            }
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            autoCompleteTextView.setSelection(autoCompleteTextView.text.length)
            imm.showSoftInput(autoCompleteTextView, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
